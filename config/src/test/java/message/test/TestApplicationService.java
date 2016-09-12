package message.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.orgid.funny.config.domain.component.AccessTokenThreadLocalComponent;
import cn.orgid.funny.config.domain.model.app.AccessToken;
import cn.orgid.funny.config.domain.model.app.Application;
import cn.orgid.funny.config.domain.model.config.ConfigField;
import cn.orgid.funny.config.domain.model.config.ConfigGroup;
import cn.orgid.funny.config.domain.service.ApplicationService;
import cn.orgid.funny.config.domain.service.ConfigService;
import cn.orgid.funny.config.domain.util.EncrypUtil;

public class TestApplicationService extends TestBase {
	
	@Autowired
	ApplicationService appService;
	
	@Autowired
	ConfigService configService;
	
	@Autowired
	AccessTokenThreadLocalComponent component;
	
	@Test
	public void test(){
		
		Application application =appService.createApplication("测试应用"+System.currentTimeMillis());
		AccessToken token = appService.getApplicationAccessToken(application.getAppKey(), application.getSecret());
		component.set(token);
		ConfigGroup c=new ConfigGroup();
		c.setAppId(token.getAppId());
		c.setGroupCode(String.valueOf(System.currentTimeMillis()));
		c.setName("sssada");
		configService.createConfigGroup(c);
		List<ConfigField> configFields = new ArrayList<ConfigField>();
		ConfigField configField = new ConfigField();
		configField.setKeyName("123");
		configField.setValue("111");
		configField.setGroupId(c.getId());
		configField.setAppId(token.getAppId());
		configFields.add(configField);
		configService.addConfigFields(configFields);
		List<ConfigField> list = configService.findGroupFieldsByGroupId(c.getId());
		if(list!=null&&list.size()>0){
			ConfigField cf = list.get(0);
			if(cf.isEncrypted()){
				String s=EncrypUtil.decrypt(cf.getValue(), token.getK());
				System.out.println(s);
			}else{
				System.out.println(cf.getValue());
			}
		}
		System.out.println(list);
		
	}

}
