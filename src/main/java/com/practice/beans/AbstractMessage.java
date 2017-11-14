/**
 * 
 */
package com.practice.beans;

import com.google.gson.GsonBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Anand Singh <email: avsmips@gmail.com>
 *
 */
@Setter
@Getter
public abstract class AbstractMessage<T> {
	private String operation;
	private T body;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new GsonBuilder().create().toJson(this);
	}
}
