package tech.hongjian.sso.common.util;

import java.io.IOException;
import java.util.Properties;

/**
 * @author xiahongjian 
 * @time   2018-04-20 10:17:16
 *
 */
public class SSOConfigUtil {
	private static final Properties CONFIG = new Properties();
	static {
		try {
			CONFIG.load(SSOConfigUtil.class.getClassLoader().getResourceAsStream("sso.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getLoginUrl() {
		return CONFIG.getProperty("auth.service.loginUrl", "http://localhost:8000/login");
	}
	
	public static String getValidateUrl() {
		return CONFIG.getProperty("auth.service.validateUrl", "http://localhost:8000/sso/validate");
	}
	
	public static String getLogoutUrl() {
		return CONFIG.getProperty("auth.service.logoutUrl", "http://localhost:8000/logout");
	}
	
	public static String getClientUrl() {
		return CONFIG.getProperty("client.url", "http://localhost:8080");
	}
}
