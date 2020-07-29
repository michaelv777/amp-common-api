/**
 * 
 */
package com.amp.common.api.config.bean;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.amp.common.api.interfaces.ToolkitSettingsInterface;
import com.amp.common.api.settings.ToolkitSettingsDefault;
import com.amp.common.api.settings.ToolkitSettingsSpring;

/**
 * @author mveksler
 *
 */
@Component
public class ToolkitSettingsConfig
{
	
	private static final Logger LOG = 
			LoggerFactory.getLogger(ToolkitSettingsConfig.class);
	
	@Autowired
	ApplicationContext applicationContext;
	
	private ToolkitSettingsInterface toolkitSettings = null;
	
	public ToolkitSettingsConfig()
	{
		String cMethodName = "";
    	
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	      
		}
		catch( Exception e)
		{
			LOG.error(cMethodName + ":" + e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unused")
	public ToolkitSettingsInterface getToolkitSettings(
			String settingsType, Optional<Boolean> isInit) 
	{
		String methodName = "";
    	
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        methodName = ste.getMethodName();
		
	        boolean isInitValue = isInit.isPresent() ? isInit.get().booleanValue() : false;
	        
	        if ( StringUtils.isBlank(settingsType ) )
	        {
	        	this.toolkitSettings = (ToolkitSettingsSpring)
	        			this.applicationContext.getBean("toolkitSettingsSpring");
			
				if ( null == this.toolkitSettings )
	    		{
					LOG.error(methodName + "::toolkitSettings is NULL.");
					
					this.toolkitSettings = new ToolkitSettingsDefault();
	    		}
				else
				{
					LOG.info(methodName + "::toolkitSettings was created.");
				}
	        }
	        else
	        {
	        	this.toolkitSettings = new ToolkitSettingsDefault();
	        }
	        
	        return this.toolkitSettings;
		}
		catch( Exception e)
		{
			LOG.error(methodName + ":" + e.getMessage(), e);
			
			return new ToolkitSettingsDefault();
		}
	}
}
