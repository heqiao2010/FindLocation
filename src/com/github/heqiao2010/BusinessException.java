package com.github.heqiao2010;

public class BusinessException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2963608732262356404L;

	public BusinessException(){
		super();
	}
	
	public BusinessException(String msg){
		super(msg);
	}
	
	public BusinessException(String msg, Exception cause){
		super(msg, cause);
	}
}
