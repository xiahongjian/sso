package tech.hongjian.sso.subsystema.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import tech.hongjian.sso.client.interceptor.AuthInteceptor;
import tech.hongjian.sso.common.constants.WebConstants;

/**
 * @author xiahongjian
 * @time 2018-04-19 15:00:37
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthInteceptor()).addPathPatterns("/**").excludePathPatterns("/", "", "/index",
				WebConstants.HANDLE_LOGOUT_NOTIFY_PATH);
	}

}
