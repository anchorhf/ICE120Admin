package com.anke.ice.model;

public class ResponseModel<T> {

	public static <T> ResponseModel<T> create(int code, String message) {
		ResponseModel<T> model = new ResponseModel<T>();
		model.setCode(code);
		model.setMessage(message);
		return model;
	}

	public static <T> ResponseModel<T> create(int code, String message, T obj) {
		ResponseModel<T> model = new ResponseModel<T>();
		model.setCode(code);
		model.setMessage(message);
		model.setObj(obj);
		return model;
	}

	public static final int CODE_SUCCESS = 200; // 执行成功
	public static final int CODE_ERROR = 500; // 服务器异常

	private int code;
	private String message;
	private T obj;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}
}
