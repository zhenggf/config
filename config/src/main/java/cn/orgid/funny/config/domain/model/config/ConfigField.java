package cn.orgid.funny.config.domain.model.config;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.orgid.funny.config.domain.model.base.BooleanConverter;
import cn.orgid.funny.config.domain.model.base.ModelBase;

@Entity
@Table(name="t_config_field")
public class ConfigField extends ModelBase {
	
	private static final long serialVersionUID = 1L;
	
	private Long appId;
	
	private Long groupId;
	
	private String keyName;
	
	private String value;
	
	private String description;
	
	
	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	@Convert(converter=BooleanConverter.class)
	private boolean encrypted;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEncrypted() {
		return encrypted;
	}

	public void setEncrypted(boolean encrypted) {
		this.encrypted = encrypted;
	}
	
	
}
