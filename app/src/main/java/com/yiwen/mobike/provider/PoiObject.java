package com.yiwen.mobike.provider;

import java.io.Serializable;

public class PoiObject implements Serializable {

	public String address;
	public String lattitude;
	public String longitude;
	public String district;

	public PoiObject(String address, String lattitude, String longitude,String district) {
		this.address = address;
		this.lattitude = lattitude;
		this.longitude = longitude;
		this.district=district;
	}

}
