/**
 * 
 */
package com.amp.common.api.config.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import com.amp.common.api.config.bean.PersistenceConfig;
import com.amp.common.api.config.bean.QueriesConfig;
import com.amp.common.api.config.bean.ToolkitSettingsConfig;
import com.amp.common.api.impl.ToolkitDataProvider;
import com.amp.common.api.impl.ToolkitDatabase;
import com.amp.common.api.impl.ToolkitJAXB;
import com.amp.common.api.impl.ToolkitSQL;
import com.amp.common.api.settings.ToolkitSettingsSpring;

/**
 * @author MVEKSLER
 *
 */
@Configuration
public class ToolkitBeansConfig
{
	@Autowired
	ApplicationContext applicationContext;
	
	/**
	 * 
	 */
	public ToolkitBeansConfig() {
		
	}

	@Primary
	@Bean("toolkitDataProviderBean")
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public ToolkitDataProvider toolkitDataProvider() 
	{
	      return new ToolkitDataProvider();
	}
	
	@Primary
	@Bean("toolkitSettingsConfigBean") 
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public ToolkitSettingsConfig toolkitSettingsConfig() 
	{
	      return new ToolkitSettingsConfig();
	}
	
	@Primary
	@Bean("persistenceConfigBean")  
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public PersistenceConfig persistenceConfig() 
	{
	      return new PersistenceConfig();
	}
	
	@Primary
	@Bean("queriesConfigBean") 
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public QueriesConfig queriesConfig() 
	{
	      return new QueriesConfig();
	}
	
	@Primary
	@Bean("toolkitSettingsSpringBean") 
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public ToolkitSettingsSpring toolkitSettingsSpring() 
	{
	      return new ToolkitSettingsSpring();
	}
	
	@Primary
	@Bean("toolkitJAXBBean") 
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public ToolkitJAXB toolkitJAXB() 
	{
	      return new ToolkitJAXB();
	}
	
	@Primary
	@Bean("toolkitSQLBean")  
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public ToolkitSQL toolkitSQL() 
	{
	      return new ToolkitSQL();
	}
	
	@Primary
	@Bean("toolkitDatabaseBean") 
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public ToolkitDatabase toolkitDatabase() 
	{
	      return new ToolkitDatabase();
	}
}

