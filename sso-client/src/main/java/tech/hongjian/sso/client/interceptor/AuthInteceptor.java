package tech.hongjian.sso.client.interceptor;

import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import tech.hongjian.sso.client.session.ClientSessionManager;
import tech.hongjian.sso.common.constants.WebConstants;
import tech.hongjian.sso.common.model.RestResponse;
import tech.hongjian.sso.common.util.HttpUtil;
import tech.hongjian.sso.common.util.SSOConfigUtil;

/**
 * @author xiahongjian
 * @time 2018-04-19 17:28:29
 *
 */
public class AuthInteceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession();
		String uri = request.getRequestURI();
		// 访问登出直接放行
		if (WebConstants.LOGOUT_URL.equals(uri)) {
			return true;
		}
		
		// 已登录
		if (session.getAttribute(WebConstants.TOKEN_KEY_IN_SESSION) != null) {
			return true;
		}

		String requestToken = request.getParameter("token");
		// 带有token，为从认证系统调整过来的
		if (StringUtils.isNotBlank(requestToken)) {
			RestResponse<?> res = HttpUtil.post(SSOConfigUtil.getValidateUrl(), new HashMap<String, String>() {
				{
					put("token", requestToken);
				}
			}, RestResponse.class);
			// token 验证成功
			if (res != null && RestResponse.STATUS_SUCCESS.equals(res.getStatus())) {
				ClientSessionManager.INSTANCE.add(requestToken, session);
				session.setAttribute(WebConstants.TOKEN_KEY_IN_SESSION, requestToken);
				return true;
			}
		}
		// 未登录，跳转到认证中心登录
		String urlParams = WebConstants.SYS_URL_PARAM_NAME + "=" + URLEncoder.encode(SSOConfigUtil.getClientUrl(), "UTF-8") + "&"
				+ WebConstants.FROM_URL_PARAM_NAME + "="
				+ URLEncoder.encode(request.getRequestURL().toString(), "UTF-8");
		response.sendRedirect(
				SSOConfigUtil.getLoginUrl() + (SSOConfigUtil.getLoginUrl().contains("?") ? "&" : "?") + urlParams);
//		response.sendRedirect("https://www.baidu.com");
		return false;
	}

}
