package cn.orgid.funny.config.client;

import cn.orgid.funny.config.client.ConfigClient.AccessToken;
import cn.orgid.funny.config.client.http.JsonResult;

public class AccessTokenResult extends JsonResult{

	private AccessToken value;

	public AccessToken getValue() {
		return value;
	}

	public void setValue(AccessToken value) {
		this.value = value;
	}
	
	
	
}
