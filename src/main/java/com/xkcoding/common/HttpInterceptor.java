package com.xkcoding.common;

import com.xkcoding.util.JsonMapper;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {
	private static final String START_TIME = "requestStartTime";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURL().toString();
		String ip = request.getRemoteAddr();
		String header = request.getHeader("User-Agent");
		UserAgent userAgent = UserAgent.parseUserAgentString(header);
		log.info("【访问来源】，IP：{}，浏览器类型：{}，操作系统：{}，原始 User-Agent：{}", ip, userAgent.getBrowser().toString(), userAgent.getOperatingSystem().toString(), header);
		Map parameterMap = request.getParameterMap();
		log.info("【访问请求路径】：{}，【请求参数】：{}，", url, JsonMapper.obj2Str(parameterMap));
		Long start = System.currentTimeMillis();
		request.setAttribute(START_TIME, start);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		String url = request.getRequestURL().toString();
		Long start = (Long) request.getAttribute(START_TIME);
		Long end = System.currentTimeMillis();
		log.info("【访问请求路径】：{}，【耗时】：{}毫秒", url, end - start);
	}
}
