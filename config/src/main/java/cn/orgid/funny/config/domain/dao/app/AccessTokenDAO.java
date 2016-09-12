package cn.orgid.funny.config.domain.dao.app;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.orgid.funny.config.domain.model.app.AccessToken;

public interface AccessTokenDAO extends JpaRepository<AccessToken, Long>{

	AccessToken findByToken(String token);

	AccessToken findByAppIdAndToken(Long appId, String token);

}
