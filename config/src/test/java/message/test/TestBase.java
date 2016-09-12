package message.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.orgid.funny.config.domain.dao.app.ApplicationDAO;
import cn.orgid.funny.config.domain.dao.app.ApplicationQuery;
import cn.orgid.funny.config.domain.model.app.Application;
import cn.orgid.funny.config.domain.service.ApplicationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={ "classpath*:/config.context.xml"})
public class TestBase {
	
	@Autowired
	ApplicationService appService;
	
	@Test
	public void createApp(){
		
		Application app = new Application();
		
		
	}
	
}