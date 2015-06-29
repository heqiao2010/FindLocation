package com.github.heqiao2010;

public class ParameterException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2963608732262356404L;

	public ParameterException(){
		super();
	}
	
	public ParameterException(String msg){
		super(msg);
	}
	
	public ParameterException(String msg, Exception cause){
		super(msg, cause);
	}
}
