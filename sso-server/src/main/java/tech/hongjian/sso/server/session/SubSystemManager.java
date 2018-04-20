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
		List<String> urls = subSystemMap.containsKey(token) ? subSystemMap.get(token) : new ArrayList<String>();
		if (StringUtils.isNotBlank(url))
			urls.add(url);
		subSystemMap.put(token, urls);
	}
	
	public void remove(String token) {
		if (token == null)
			return;
		subSystemMap.remove(token);
	}
}
