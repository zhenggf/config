package cn.orgid.funny.config.client;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.http.client.ResponseHandler;

import cn.orgid.funny.config.client.ecrypt.EncrypUtil;
import cn.orgid.funny.config.client.http.HttpClientComponent;
import cn.orgid.funny.config.client.http.JsonResponseHandler;
import cn.orgid.funny.config.client.http.HttpClientComponent.RequestParameters;



public class ConfigClient {

	private String appKey;

	private String secret;

	private String serviceUrl;

	private AccessToken accessToken;

	public void setK1(String k1){
		
		appKey=k1;
		
	}
	
	public void setK2(String k2){
		
		secret=k2;
		
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public void init() {

		getToken();

	}

	private void getToken() {

		String url = serviceUrl + "/get_access_token.htm";
		RequestParameters parameters = new RequestParameters();
		parameters.add("appKey", appKey);
		parameters.add("secret", secret);
		org.apache.http.client.ResponseHandler<AccessTokenResult> responseHandler = JsonResponseHandler
				.createResponseHandler(AccessTokenResult.class);
		AccessTokenResult r = HttpClientComponent.executePost(parameters, url,
				responseHandler);
		if (r == null || !r.isSuccess()) {
			throw new RuntimeException("远程访问错误，无法获取配置信息");
		}
		accessToken = r.getValue();

	}

	public Properties getConfigProperties() {

		if (accessToken == null || accessToken.isExpire()) {
			getToken();
		}
		String url = serviceUrl + "/get_config_fields.htm";
		RequestParameters parameters = new RequestParameters();
		parameters.add("token", accessToken.getToken());
		parameters.add("id", String.valueOf(accessToken.getId()));
		parameters.add("appId", String.valueOf(accessToken.getAppId()));
		ResponseHandler<ConfigFieldListResult> responseHandler = JsonResponseHandler
				.createResponseHandler(ConfigFieldListResult.class);
		ConfigFieldListResult r = HttpClientComponent.executePost(parameters,
				url, responseHandler);
		Properties p = new Properties();
		List<ConfigField> list = null;
		if (r == null || r.getValue() == null)
			return p;
		list = r.getValue();
		for (ConfigField configField : list) {
			if (configField.isEncrypted()) {
				p.put(configField.getKeyName(),
						EncrypUtil.decrypt(configField.getValue(),
								accessToken.getK()));
			} else {
				p.put(configField.getKeyName(), configField.getValue());
			}
		}
		return p;

	}

	public static class AccessToken {

		private Long id;

		private Long appId;

		private String token;

		private Date tokenExpireTime;

		private String k;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getAppId() {
			return appId;
		}

		public void setAppId(Long appId) {
			this.appId = appId;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
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

		public boolean isExpire() {

			return (tokenExpireTime != null && new Date().getTime() > tokenExpireTime
					.getTime());

		}

	}

	public static class ConfigField {

		private Long groupId;

		private String keyName;

		private String value;

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

		public boolean isEncrypted() {
			return encrypted;
		}

		public void setEncrypted(boolean encrypted) {
			this.encrypted = encrypted;
		}

	}

}
