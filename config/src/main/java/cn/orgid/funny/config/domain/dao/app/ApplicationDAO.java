package cn.orgid.funny.config.domain.dao.app;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.orgid.funny.config.domain.model.app.Application;

public interface ApplicationDAO extends JpaRepository<Application, Long> {
	
	Application findByAppKeyAndSecret(String appId, String secret);
	
	

}
