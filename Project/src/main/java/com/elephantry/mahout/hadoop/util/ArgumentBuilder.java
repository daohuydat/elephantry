/**
 * @author datdh
 * */
package com.elephantry.mahout.hadoop.util;

import java.util.ArrayList;
import java.util.List;

public class ArgumentBuilder {
	private List<String> args;

	public ArgumentBuilder() {
		args = new ArrayList<>();
	}

	public ArgumentBuilder(String setting) {
		args = new ArrayList<>();
		set(setting);
	}

	public ArgumentBuilder(String key, String value) {
		args = new ArrayList<>();
		set(key, value);
	}

	public ArgumentBuilder set(String setting) {
		args.add(setting);
		return this;
	}

	public ArgumentBuilder set(String key, String value) {
		args.add(key);
		args.add(value);
		return this;
	}
	
	public ArgumentBuilder set(String key, int value) {
		args.add(key);
		args.add(String.valueOf(value));
		return this;
	}
	
	public ArgumentBuilder set(String key, double value) {
		args.add(key);
		args.add(String.valueOf(value));
		return this;
	}
	public String[] getArguments(){
		return args.toArray(new String[]{});
	}
}
