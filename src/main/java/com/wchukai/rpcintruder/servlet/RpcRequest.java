package com.wchukai.rpcintruder.servlet;

import java.io.Serializable;
/**
 * @author chukai
 */
public class RpcRequest implements Serializable {

	private static final long serialVersionUID = -2226632814980198240L;

	private String className;

	private String methodName;

	private Object[] args;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

}
