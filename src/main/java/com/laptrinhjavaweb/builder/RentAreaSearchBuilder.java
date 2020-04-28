package com.laptrinhjavaweb.builder;

public class RentAreaSearchBuilder {

	private String value;

	public String getValue() {
		return value;
	}

	private RentAreaSearchBuilder(Builder builder) {
		this.value = builder.value;
	}

	public static class Builder {
		private String value;

		public Builder setValue(String value) {
			this.value = value;
			return this;

		}

	}
}
