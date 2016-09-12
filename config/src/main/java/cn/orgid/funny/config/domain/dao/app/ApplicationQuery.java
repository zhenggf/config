package cn.orgid.funny.config.domain.dao.app;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import cn.orgid.funny.config.domain.model.app.Application;

@Repository
public class ApplicationQuery {
	
	@Autowired
	SqlSessionTemplate sessionTemplate;
	
	public Application get(Long id) {
		
		return (Application) sessionTemplate.selectOne(
				 "Application.get", id);
		
	}

}
