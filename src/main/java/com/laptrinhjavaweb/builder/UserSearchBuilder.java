package com.laptrinhjavaweb.builder;

public class UserSearchBuilder {
	private String userName;
	private String fullName;
	private String status;
	private String checked;

	public String getUserName() {
		return userName;
	}

	public String getFullName() {
		return fullName;
	}

	public String getStatus() {
		return status;
	}

	public String getChecked() {
		return checked;
	}
	private UserSearchBuilder(Builder builder) {
		this.fullName = builder.fullName;
		this.userName = builder.userName;
		this.status = builder.status;
		this.checked = builder.checked;
	}

	public static class Builder {
		private String userName;
		private String fullName;
		private String status;
		private String checked;

		public Builder setUserName(String userName) {
			this.userName = userName;
			return this;
		}

		public Builder setFullName(String fullName) {
			this.fullName = fullName;
			return this;
		}

		public Builder setStatus(String status) {
			this.status = status;
			return this;
		}

		public Builder setChecked(String checked) {
			this.checked = checked;
			return this;
		}

		public UserSearchBuilder build() {
			return new UserSearchBuilder(this);
		}
	}

}
