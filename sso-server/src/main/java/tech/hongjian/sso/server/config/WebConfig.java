package tech.hongjian.sso.server.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import tech.hongjian.sso.server.LoginInterceptor;

/**
 * @author xiahongjian 
 * @time   2018-04-19 15:00:37
 *
 */
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/sso/**");
	}

}
