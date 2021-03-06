package tech.hongjian.sso.subsystema.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tech.hongjian.sso.client.session.ClientSessionManager;
import tech.hongjian.sso.common.constants.WebConstants;
import tech.hongjian.sso.common.model.RestResponse;
import tech.hongjian.sso.common.util.SSOConfigUtil;

/**
 * @author xiahongjian 
 * @time   2018-04-20 10:56:11
 *
 */
@Controller	
public class IndexController {

	@GetMapping(value = {"", "/"})
	public String index() {
		return "index";
	}
	
	@GetMapping("res")
	public String resources() {
		return "res";
	}
	
	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {
		String token = (String) session.getAttribute(WebConstants.TOKEN_KEY_IN_SESSION);
		if (StringUtils.isBlank(token))
			return "redirect:/";
		// 重定向到认证系统的登出Url
		return "redirect:" + SSOConfigUtil.getLogoutUrl();
	}
	
	@RequestMapping(value = WebConstants.HANDLE_LOGOUT_NOTIFY_PATH)
	public RestResponse<?> handleLogoutNotify(String token) {
		if (StringUtils.isBlank(token))
			return RestResponse.fail();
		// 销毁session
		HttpSession session = ClientSessionManager.INSTANCE.remove(token);
		session.invalidate();
		return RestResponse.ok();
	}
}
