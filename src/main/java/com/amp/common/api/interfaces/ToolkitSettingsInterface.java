/**
 * 
 */
package com.amp.common.api.interfaces;

import com.amp.common.api.settings.HibernateSettings;
import com.amp.common.api.settings.SQLSettings;

/**
 * @author mveksler
 *
 */
public interface ToolkitSettingsInterface 
{
	public HibernateSettings getHibernateSettingsData();
	
	public SQLSettings getSQLSettingsData();
	
	public boolean init();
	
	public boolean getStatus();

	public boolean freeResources();
}
