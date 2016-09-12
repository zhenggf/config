package cn.orgid.funny.config.domain.model.config;

import javax.persistence.Entity;
import javax.persistence.Table;
import cn.orgid.funny.config.domain.model.base.ModelBase;

@Entity
@Table(name="t_config_group")
public class ConfigGroup  extends ModelBase{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Long appId;
	
	private String groupCode;
	
	private String name;
	

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	
}
