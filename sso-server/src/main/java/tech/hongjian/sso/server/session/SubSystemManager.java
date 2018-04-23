package tech.hongjian.sso.server.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

/**
 * @author xiahongjian
 * @time 2018-04-19 14:52:12
 *
 */
public enum SubSystemManager {
	INSTANCE;

	private Map<String, List<String>> subSystemMap = new ConcurrentHashMap<>();
	
	public boolean contains(String token) {
		return subSystemMap.containsKey(token);
	}

	public List<String> getSubSytems(String token) {
		if (subSystemMap.containsKey(token))
			return subSystemMap.get(token);
		return Collections.emptyList();
	}

	public void addSystemUrl(String token, String url) {
		if (StringUtils.isBlank(token))
			return;
		List<String> urls;
		if (subSystemMap.containsKey(token)) {
			urls = subSystemMap.get(token);
		} else {
			urls = new ArrayList<String>(1);
			subSystemMap.put(token, urls);
		}
		urls.add(url);
	}
	
	public List<String> remove(String token) {
		if (token == null)
			return Collections.emptyList();
		return subSystemMap.remove(token);
	}
}
