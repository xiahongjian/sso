package tech.hongjian.sso.common.model;

/**
 * @author xiahongjian 
 * @time   2018-04-19 15:49:20
 *
 */
public class RestResponse<T> {
	public static final String STATUS_SUCCESS = "success";
	public static final String STATUS_FAIL = "fail";
	
	private String status;
	private String msessage;
	private T data;
	
	public RestResponse(String status, String message, T data) {
		this.status = status;
		this.msessage = message;
		this.data = data;
	}
	
	public RestResponse(String status, String message) {
		this(status, message, null);
	}
	
	public RestResponse(String status) {
		this(status, null, null);
	}
	
	public static RestResponse<?> ok() {
		return new RestResponse<>(STATUS_SUCCESS);
	}
	
	public static RestResponse<?> ok(Object data) {
		return new RestResponse<Object>(STATUS_SUCCESS, null, data);
	}
	
	public static RestResponse<?> fail() {
		return new RestResponse<>(STATUS_FAIL);
	}
	
	public static RestResponse<?> fail(String message) {
		return new RestResponse<>(STATUS_FAIL, message);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsessage() {
		return msessage;
	}

	public void setMsessage(String msessage) {
		this.msessage = msessage;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
