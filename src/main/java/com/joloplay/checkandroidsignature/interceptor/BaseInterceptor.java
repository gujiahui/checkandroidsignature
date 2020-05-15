package com.joloplay.checkandroidsignature.interceptor;

import com.joloplay.checkandroidsignature.util.IPKit;
import com.joloplay.checkandroidsignature.util.MapCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 */
@Component
@Slf4j
public class BaseInterceptor implements HandlerInterceptor {
	private static final String USER_AGENT = "user-agent";

	private MapCache cache = MapCache.single();


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		String uri = request.getRequestURI();

		log.info("UserAgent: {}", request.getHeader(USER_AGENT));
		log.info("用户访问地址: {}, 来路地址: {}", uri, IPKit.getIpAddrByRequest(request));
		//请求拦截处理
		//设置get请求的token
/*		if (request.getMethod().equals("GET")) {
			String csrf_token = UUID.UU64();
			// 默认存储30分钟
			cache.hset(Types.CSRF_TOKEN.getType(), csrf_token, uri, 30 * 60);
			request.setAttribute("_csrf_token", csrf_token);
		}*/
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
