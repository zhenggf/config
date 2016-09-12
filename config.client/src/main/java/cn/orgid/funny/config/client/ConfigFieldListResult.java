package cn.orgid.funny.config.client;

import java.util.List;

import cn.orgid.funny.config.client.ConfigClient.ConfigField;
import cn.orgid.funny.config.client.http.JsonResult;

public class ConfigFieldListResult extends JsonResult{
	
	List<ConfigField> value;

	public List<ConfigField> getValue() {
		return value;
	}

	public void setValue(List<ConfigField> value) {
		this.value = value;
	}
	

}
