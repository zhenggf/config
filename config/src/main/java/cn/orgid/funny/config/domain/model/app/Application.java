package cn.orgid.funny.config.domain.model.app;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.orgid.funny.config.domain.model.base.ModelBase;
import cn.orgid.funny.config.domain.util.EncrypUtil;

@Entity
@Table(name = "t_app")
public class Application extends ModelBase {

	private static final long serialVersionUID = 1L;
	

	
	@Column(unique=true)
	private String name;

	@Column(unique=true)
	private String appKey;

	private String secret;

	private String k;
	
	
	
	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getSecret() {
		return secret;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public AccessToken createAccessToken() {

		AccessToken accessToken = AccessToken.createApplicationAccessToken();
		accessToken.setAppId(getId());
		accessToken.setK(k);
		return accessToken;

	}

	public void init() {

		this.appKey = UUID.randomUUID().toString().replaceAll("-", "");
		this.secret = UUID.randomUUID().toString().replace("-", "");
		k=EncrypUtil.getEncryptionKey();

	}

	public static void main(String[] args) {
		
		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
		
	}

}
