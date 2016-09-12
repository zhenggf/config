package config.test;

import java.util.Properties;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.orgid.funny.config.client.ConfigClient;

public class TestConfigClient extends TestBase {
	
	@Autowired
	ConfigClient client;
	
	@Test
	public void test(){
		
		System.out.println(client);
		Properties ps =client.getConfigProperties();
		System.out.println(ps);
		
	}

}
