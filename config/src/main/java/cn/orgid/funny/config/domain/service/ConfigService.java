package cn.orgid.funny.config.domain.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.orgid.funny.config.domain.component.AccessTokenThreadLocalComponent;
import cn.orgid.funny.config.domain.dao.app.AccessTokenDAO;
import cn.orgid.funny.config.domain.dao.app.ApplicationDAO;
import cn.orgid.funny.config.domain.dao.config.ConfigFieldDAO;
import cn.orgid.funny.config.domain.dao.config.ConfigGroupDAO;
import cn.orgid.funny.config.domain.exception.ApplicationException;
import cn.orgid.funny.config.domain.model.app.AccessToken;
import cn.orgid.funny.config.domain.model.app.Application;
import cn.orgid.funny.config.domain.model.config.ConfigField;
import cn.orgid.funny.config.domain.model.config.ConfigGroup;
import cn.orgid.funny.config.domain.util.EncrypUtil;

@Service
public class ConfigService {

	private boolean encrypting = false;

	private Object EncryptingLock = new Object();

	@Autowired
	ConfigGroupDAO configGroupDAO;

	@Autowired
	ConfigFieldDAO configRecordDAO;

	@Autowired
	ApplicationDAO applicationDAO;

	@Autowired
	AccessTokenDAO accessTokenDAO;

	@Autowired
	AccessTokenThreadLocalComponent tokenThreadLocalComponent;

	public void createConfigGroup(ConfigGroup configGroup) {

		checkAccessToken();
		if (configGroupDAO.findByGroupCode(configGroup.getGroupCode()) != null)
			throw new ApplicationException("配置组编码已存在");
		AccessToken t = tokenThreadLocalComponent.get();
		configGroup.setAppId(t.getAppId());
		configGroupDAO.save(configGroup);

	}

	private AccessToken checkAccessToken() {

		AccessToken t = tokenThreadLocalComponent.get();
		AccessToken accessToken = accessTokenDAO.findOne(t.getId());
		if (!accessToken.isValid(t)) {
			throw new ApplicationException("access token error");
		}
		return accessToken;

	}

	public void addConfigFields(List<ConfigField> configFields) {

		if (configFields == null) {
			return;
		}
		checkAccessToken();
		Set<String> set = new HashSet<String>();
		for (ConfigField configField : configFields) {
			if (set.contains(configField.getKeyName())) {
				continue;
			} else {
				set.add(configField.getKeyName());
			}
			configRecordDAO.save(configField);
		}

	}

	public List<ConfigField> findGroupFieldsByGroupId(Long groupId) {

		checkAccessToken();
		AccessToken t = tokenThreadLocalComponent.get();
		List<ConfigField> configFields = configRecordDAO.findByGroupId(groupId);

		return configRecordDAO.findByGroupId(groupId);

	}

	public List<ConfigField> findGroupFields() {
		
		
		checkAccessToken();
		AccessToken t = tokenThreadLocalComponent.get();
		List<ConfigField> ls = configRecordDAO.findByAppId(t.getAppId());
		return ls;

	}

	@Scheduled(cron = "1 * * * * ?")
	public void encryptFields() {

		synchronized (EncryptingLock) {
			if (encrypting) {
				return;
			} else {
				encrypting = true;
			}
		}
		Map<Long, Application> map = new HashMap<Long, Application>();
		List<ConfigField> configFields = configRecordDAO.findByEncrypted(false);
		for (ConfigField configField : configFields) {
			encryptConfigField(map, configField);
		}
		encrypting = false;

	}

	private void encryptConfigField(Map<Long, Application> map,
			ConfigField configField) {
		
		try {
			Application app = map.get(configField.getGroupId());
			if (app == null) {
				ConfigGroup configGroup = configGroupDAO.findOne(configField
						.getGroupId());
				app = applicationDAO.findOne(configGroup.getAppId());
				map.put(configField.getGroupId(), app);
			}
			configField.setValue(EncrypUtil.encryt(configField.getValue(),
					app.getK()));
			configField.setEncrypted(true);
			configRecordDAO.save(configField);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}

}
