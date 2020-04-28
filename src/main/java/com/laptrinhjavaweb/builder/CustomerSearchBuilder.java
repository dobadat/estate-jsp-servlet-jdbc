package com.laptrinhjavaweb.builder;

public class CustomerSearchBuilder {

	private String customerName;
	private String email;
	private String phoneNumber;
	private String customerId;

	public String getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	private CustomerSearchBuilder(Builder builder) {
		this.customerName = builder.customerName;
		this.email = builder.email;
		this.phoneNumber = builder.phoneNumber;
		this.customerId = builder.customerId;
	}

	public static class Builder {
		private String customerName;
		private String email;
		private String phoneNumber;
		private String customerId;

		public Builder setCustomerName(String customerName) {
			this.customerName = customerName;
			return this;
		}

		public Builder setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}

		public Builder setCustomerId(String customerId) {
			this.customerId = customerId;
			return this;
		}

		public CustomerSearchBuilder build() {
			return new CustomerSearchBuilder(this);
		}
	}

}
