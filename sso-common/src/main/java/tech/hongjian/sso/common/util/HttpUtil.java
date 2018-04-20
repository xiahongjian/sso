package tech.hongjian.sso.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;

/**
 * @author xiahongjian 
 * @time   2018-04-19 16:42:04
 *
 */
public class HttpUtil {
	public static <T> T post(String url, Map<String, String> params, Class<T> clazz) {
		return JSONObject.parseObject(post(url, params), clazz);
	}
	
	public static String post(String url, Map<String, String> params) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		if (params != null && !params.isEmpty()) {
			List<NameValuePair> pairs = new ArrayList<>();
			for (Entry<String, String> entry : params.entrySet()) {
				pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
			} catch (UnsupportedEncodingException e) {}
		}
		try {
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			return IOUtils.toString(httpResponse.getEntity().getContent());
		} catch (IOException e) {
		}
		return null;
	}
}
