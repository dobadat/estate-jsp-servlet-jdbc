package com.laptrinhjavaweb.DTO;

public class UserDTO extends AbstractDTO{
private String userName;
private String fullName;
private String status;
private String checked;

public String getChecked() {
	return checked;
}
public void setChecked(String checked) {
	this.checked = checked;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getFullName() {
	return fullName;
}
public void setFullName(String fullName) {
	this.fullName = fullName;
}

public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}



}
