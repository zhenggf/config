package cn.orgid.funny.config.domain.dao.config;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.orgid.funny.config.domain.model.config.ConfigGroup;

public interface ConfigGroupDAO extends JpaRepository<ConfigGroup, Long>{

	ConfigGroup findByGroupCode(String groupCode);

}
