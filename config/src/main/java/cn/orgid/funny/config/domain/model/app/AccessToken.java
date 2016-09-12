package cn.orgid.funny.config.domain.model.app;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;




import cn.orgid.funny.config.domain.exception.ApplicationException;
import cn.orgid.funny.config.domain.model.base.ModelBase;

@Entity
@Table(name="t_access_token")
public class AccessToken extends ModelBase{
	
	private static final long serialVersionUID = 1L;

	private Long appId;
	
	private String token;
	
	private Date tokenExpireTime;
	
	@Transient
	private String k;
	
	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Date getTokenExpireTime() {
		return tokenExpireTime;
	}

	public void setTokenExpireTime(Date tokenExpireTime) {
		this.tokenExpireTime = tokenExpireTime;
	}

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public void init(){
		
		token =UUID.randomUUID().toString().replaceAll("-", "");
		tokenExpireTime=new Date(new Date().getTime()+1000*60*60*5);
		
		
	}
	
	private AccessToken(){
		
	}
	
	public static AccessToken createApplicationAccessToken(){
		
		AccessToken accessToken = new AccessToken();
		accessToken.init();
		return accessToken;
	
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isValid(AccessToken accessToken) {
		
		if(isExpire()){
			throw new ApplicationException("access token  expired");
		}
		if(accessToken!=null&&accessToken.getToken().equals(token)
				&&accessToken.getId()==getId()){
			return true;
		}
		return false;
		
	}

	private boolean isExpire() {
		
		return (tokenExpireTime!=null&&new Date().getTime()>tokenExpireTime.getTime());
		
	}

	public void refresh() {
		
		token =UUID.randomUUID().toString().replaceAll("-", "");
		tokenExpireTime=new Date(new Date().getTime()+1000*60*60*5);
		
	}

}
