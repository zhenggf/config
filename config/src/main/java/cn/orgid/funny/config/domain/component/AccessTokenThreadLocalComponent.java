package cn.orgid.funny.config.domain.component;

import org.springframework.stereotype.Component;

import cn.orgid.funny.config.domain.model.app.AccessToken;

@Component
public class AccessTokenThreadLocalComponent {
	
	//test 测试
	private ThreadLocal<AccessToken> local=new ThreadLocal<AccessToken>();
	
	public void set(AccessToken t){
		
		local.set(t);
	
	}
	
	public AccessToken get(){
		
		return local.get();
	
	}

}
