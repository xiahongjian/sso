package tech.hongjian.sso.server.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import tech.hongjian.sso.common.util.HttpUtil;
import tech.hongjian.sso.server.session.SubSystemManager;
import tech.hongjian.sso.server.session.WebConstants;

/**
 * @author xiahongjian
 * @time 2018-04-19 16:06:51
 *
 */
@Controller
public class IndexController {
	
	@GetMapping(value = {"", "/", "/index"})
	public String index() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	public void doLogin(HttpServletResponse response, HttpSession session, String username, String password, String from,
			String systemUrl) throws IOException {
		String token = UUID.randomUUID().toString();
		session.setAttribute(WebConstants.TOKEN_KEY_IN_SESSION, token);
		SubSystemManager.INSTANCE.addSystemUrl(token, systemUrl);
		
		if (StringUtils.isBlank(from)) {
			from = systemUrl;
		}
		if (StringUtils.isBlank(from)) {
			response.sendRedirect("/index");
			return;
		}
		// 重定向到子系统
		response.sendRedirect(from + (from.contains("?") ? "&" : ":") + "token=" + token);
	}
	
	@GetMapping("/logout")
	public void logout(HttpSession session) {
		String token = (String) session.getAttribute(WebConstants.TOKEN_KEY_IN_SESSION);
		// token不为null时为已登录，向各个注册的子系统发送登出通知
		if (token != null) {
			List<String> urls = SubSystemManager.INSTANCE.getSubSytems(token);
			Map<String, String> params = new HashMap<>(1);
			params.put("token", token);
			for (String url : urls)
				HttpUtil.post(url, params);
		}
		session.invalidate();
	}
	
}
