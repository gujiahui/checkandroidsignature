package com.joloplay.checkandroidsignature.handler;

import com.joloplay.checkandroidsignature.common.base.BaseResult;
import com.joloplay.checkandroidsignature.common.enums.BaseResultEnum;
import com.joloplay.checkandroidsignature.common.base.BaseResultGenerator;
import com.joloplay.checkandroidsignature.exception.JsonException;
import lombok.extern.slf4j.Slf4j;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gjh
 */
@Slf4j
@ControllerAdvice
public class GlobleExceptionHandler extends RuntimeException{


	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public BaseResult jsonHandler(HttpServletRequest request, Exception e) throws Exception {
		if ("org.apache.catalina.connector.ClientAbortException".equals(e.getClass().getName())) {
			log.error("发生clientAbortException");
			return null;
		}
		log.error("-----------jsonHandler---错误信息----------：", e);
		return BaseResultGenerator.error(BaseResultEnum.SERVER_ERROR.getCode(),e.getMessage());
	}

	/**
	 * @param request
	 * @param e
	 * @Description:i自定义异常
	 * @Author:SimonHu
	 * @Date: 2019/8/23 14:26
	 */
	@ExceptionHandler(value = JsonException.class)
	@ResponseBody
	public BaseResult customException(HttpServletRequest request, JsonException e) {
		log.error("---------customException-----错误信息----------："+e.getMessage());
		log.error(getErrrorInfo(e,e.getCode(),e.getMessage(),2));
		return BaseResultGenerator.error(e.getCode(), e.getMessage());
	}

	/**
	 * @param ex
	 * @param row 控制打印行数
	 * @return java.lang.String
	 * @Description:获取错误信息并记录日志
	 * @Author:SimonHu
	 * @Date: 2019/8/26 16:04
	 */
	private String getErrrorInfo(Exception ex, Integer code, String msg,int row) {
		StringBuilder sb = new StringBuilder();
		StackTraceElement[] error = ex.getStackTrace();
		int i = 0;
		for (StackTraceElement stackTraceElement : error) {
			sb.append(stackTraceElement);
			sb.append("\r\n");
			i++;
			if (i >= row) {
				break;
			}
		}
		return sb.toString();
	}
}
