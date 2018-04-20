package tech.hongjian.sso.client.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

/**
 * @author xiahongjian 
 * @time   2018-04-20 10:01:07
 *
 */
public enum ClientSessionManager {
	INSTANCE;
	
	private Map<String, HttpSession> sessionMap = new ConcurrentHashMap<>();
	
	public void add(String token, HttpSession session) {
		sessionMap.put(token, session);
	}
	
	public boolean contains(String token) {
		return sessionMap.containsKey(token);
	}
	
	public HttpSession remove(String token) {
		return sessionMap.remove(token);
	}
}
