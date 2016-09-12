package cn.orgid.funny.config.domain.dao.config;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.orgid.funny.config.domain.model.config.ConfigField;

public interface ConfigFieldDAO extends JpaRepository<ConfigField, Long> {

	List<ConfigField> findByGroupId(Long id);

	List<ConfigField> findByEncrypted(boolean b);

	List<ConfigField> findByAppId(Long appId);

}
