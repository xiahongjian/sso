package tech.hongjian.sso.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import tech.hongjian.sso.server.session.SubSystemManager;
import tech.hongjian.sso.server.session.WebConstants;

/**
 * @author xiahongjian 
 * @time   2018-04-19 14:49:47
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		if (WebConstants.LOGOUT_URL.equals(uri))
			return true;
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute(WebConstants.TOKEN_KEY_IN_SESSION);
		// 已经登录
		if (token != null) {
			String systemUrl = request.getParameter(WebConstants.SYS_URL_PARAM_NAME);
			// 如果systemUrl参数不为空，则是从子系统跳转过来的，重定向会原系统
			if (StringUtils.isNoneBlank(systemUrl)) {
				// 记录子系统Url，用户登出时向各个子系统发送登录通知
				SubSystemManager.INSTANCE.addSystemUrl(token, systemUrl);
				String url = request.getParameter(WebConstants.FROM_URL_PARAM_NAME);
				if (StringUtils.isBlank(url))
					url = systemUrl;
				response.sendRedirect(url + (url.contains("?") ? "&" : "?") + "token=" + token);
				return false;
			}
			return true;
		}
		if (WebConstants.LOGIN_URL.equals(uri))
			return true;
		
		// 未登录，且访问的不是登录和登出页面，重定向到登录页面
		response.sendRedirect(WebConstants.LOGIN_URL);
		return false;
	}

}
