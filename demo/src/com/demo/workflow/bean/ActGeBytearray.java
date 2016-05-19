package com.demo.workflow.bean;

import java.io.Serializable;
import java.util.Arrays;

public class ActGeBytearray implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5006708530243487954L;
	private byte[] bytes;

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	@Override
	public String toString() {
		return "ActGeBytearray [bytes=" + Arrays.toString(bytes) + "]";
	}
}
