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
@Getter
@Setter
public class Cordinates {

	private int x ;
	private int y ;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new GsonBuilder().create().toJson(this);
	}
	
	
}
