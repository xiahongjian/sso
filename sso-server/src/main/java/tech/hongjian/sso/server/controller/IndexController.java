package tech.hongjian.sso.server.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import tech.hongjian.sso.common.constants.WebConstants;
import tech.hongjian.sso.server.session.SubSystemManager;

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
	
	@GetMapping("/res")
	public String res() {
		return "res";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	public void doLogin(HttpServletResponse response, HttpSession session, String username, String password, String from,
			String sysUrl) throws IOException {
		String token = UUID.randomUUID().toString();
		session.setAttribute(WebConstants.TOKEN_KEY_IN_SESSION, token);
		SubSystemManager.INSTANCE.addSystemUrl(token, sysUrl);
		
		if (StringUtils.isBlank(from)) {
			from = sysUrl;
		}
		if (StringUtils.isBlank(from)) {
			response.sendRedirect("/index");
			return;
		}
		// 重定向到子系统
		response.sendRedirect(from + (from.contains("?") ? "&" : ":") + "token=" + token);
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		String token = (String) session.getAttribute(WebConstants.TOKEN_KEY_IN_SESSION);
		SubSystemManager.INSTANCE.remove(token);
		session.invalidate();
		return "index";
	}
	
}
