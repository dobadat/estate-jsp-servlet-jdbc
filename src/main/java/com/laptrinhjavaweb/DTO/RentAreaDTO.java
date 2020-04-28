package com.laptrinhjavaweb.DTO;

public class RentAreaDTO extends AbstractDTO{
	private String value;
	private String buildingid;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getBuildingid() {
		return buildingid;
	}
	public void setBuildingid(String buildingid) {
		this.buildingid = buildingid;
	}
	
	

}
