package com.example.demo.model;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
	private static final String SUCCESS = "SUCCESS";
	
	/** 狀態代碼 */
	int statusCode;
	
	/** 狀態描述 */
	String statusDesc;
	
	/** 回覆物件 */
	Object respData;
	
	public ApiResponse() {
	}
	
	public ApiResponse(int statusCode, String statusDesc, Object respData) {
		this.statusCode = statusCode;
		this.statusDesc = statusDesc;
		this.respData = respData;
	}
	
	public ApiResponse(Object respData) {
		this.statusCode = HttpStatus.OK.value();
		this.statusDesc = SUCCESS;
		this.respData = respData;
	}
}
