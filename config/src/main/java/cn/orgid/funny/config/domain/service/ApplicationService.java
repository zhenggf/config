package cn.orgid.funny.config.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.orgid.funny.config.domain.component.AccessTokenThreadLocalComponent;
import cn.orgid.funny.config.domain.dao.app.AccessTokenDAO;
import cn.orgid.funny.config.domain.dao.app.ApplicationDAO;
import cn.orgid.funny.config.domain.exception.ApplicationException;
import cn.orgid.funny.config.domain.model.app.AccessToken;
import cn.orgid.funny.config.domain.model.app.Application;

@Service
public class ApplicationService {
	
	
	@Autowired
	ApplicationDAO applicationDAO;
	
	@Autowired
	AccessTokenDAO accessTokenDAO;
	
	@Autowired
	AccessTokenThreadLocalComponent accessTokenThreadLocalComponent;
	
	
	
	public Application createApplication(String name){
		
		Application application=new  Application();
		
		application.setName(name);
		application.init();
		applicationDAO.save(application);
		return application;
		
	}
	
	public AccessToken getApplicationAccessToken(String appKey,String secret){
		
		Application application = applicationDAO.findByAppKeyAndSecret(appKey, secret);
		if(application==null){
			throw new ApplicationException("appid or secret error!");
		}
		AccessToken token = application.createAccessToken();
		accessTokenDAO.save(token);
		return token;
		
	}
	
	
	
	
	
	
	
}
