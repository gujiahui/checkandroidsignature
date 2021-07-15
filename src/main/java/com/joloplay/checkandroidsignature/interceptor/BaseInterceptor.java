package com.joloplay.checkandroidsignature.interceptor;

import com.joloplay.checkandroidsignature.util.IPKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 * @author  gjh
 */
@Component
@Slf4j
public class BaseInterceptor implements HandlerInterceptor {
	private static final String USER_AGENT = "user-agent";



	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		String uri = request.getRequestURI();

		log.info("UserAgent: {}", request.getHeader(USER_AGENT));
		log.info("用户访问地址: {}, 来路地址: {}", uri, IPKit.getIp(request));
		if(uri!=null&&!"".equals(uri)&&uri.endsWith("/")){
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
		initSiteConfig(httpServletRequest);
	}

	private void initSiteConfig(HttpServletRequest request) {
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}
}
