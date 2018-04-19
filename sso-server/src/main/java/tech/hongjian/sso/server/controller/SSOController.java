package tech.hongjian.sso.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.hongjian.sso.common.model.RestResponse;
import tech.hongjian.sso.server.session.SubSystemManager;

/**
 * @author xiahongjian 
 * @time   2018-04-19 15:47:51
 *
 */
@RestController
@RequestMapping("/sso/")
public class SSOController {
	
	@RequestMapping("validate")
	public RestResponse<?> validateToken(String token) {
		if (SubSystemManager.INSTANCE.contains(token))
			return RestResponse.ok();
		return RestResponse.fail();
	}

}
