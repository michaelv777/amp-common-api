/**
 * 
 */
package com.amp.common.api.test.api;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.amp.common.api.config.spring.PersistenceBeansConfig;
import com.amp.common.api.impl.ToolkitDataProvider;
import com.amp.common.api.test.config.ToolkitBeansConfig;

/**
 * @author MVEKSLER
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ComponentScan(basePackages = {"com.amp.common.api"})
@ContextConfiguration(classes = {PersistenceBeansConfig.class, ToolkitBeansConfig.class})
@TestPropertySource(locations = { "classpath:amp-common-api.properties"})
public class ToolkitDataProviderTest 
{	
	@SuppressWarnings("unused")
	@Autowired
	private Environment env;
	
	@Autowired
	private ToolkitDataProvider cToolkitDataProvider = null;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception 
	{
		try
		{
			if ( this.cToolkitDataProvider != null )
			{
				this.cToolkitDataProvider.closeConnection();
			}
		}
		catch( Exception e)
		{
			fail("testToolkitDataProvider failed:" + e.getStackTrace());
		}
	}

	/**
	 * Test method for {@link com.amp.common.api.impl.ToolkitDataProvider#ToolkitDataProvider()}.
	 */
	@Ignore
	@Test
	public void validateToolkitDataProvider() 
	{
		try
		{
			if ( null == this.cToolkitDataProvider )
			{
				this.cToolkitDataProvider = new ToolkitDataProvider();
			}
			
			if ( this.cToolkitDataProvider.isLcRes() )
			{
				boolean cRes = this.cToolkitDataProvider.openConnection();
				if ( !cRes )
				{
					fail("testToolkitDataProvider failed to open DB connection!");
				}
			}
		}
		catch( Exception e)
		{
			fail("testToolkitDataProvider failed:" + e.getStackTrace());
		}
	}
}
