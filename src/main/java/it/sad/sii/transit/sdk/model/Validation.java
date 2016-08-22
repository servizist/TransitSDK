package it.sad.sii.transit.sdk.model;

import java.io.Serializable;

public class Validation implements Serializable {
	private String day;
	private String validator;

	private int count;

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("day: ").append(day);
		buffer.append(", validator: ").append(validator);
		buffer.append(", count: ").append(count);

		return buffer.toString();
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getValidator() {
		return validator;
	}

	public void setValidator(String validator) {
		this.validator = validator;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}