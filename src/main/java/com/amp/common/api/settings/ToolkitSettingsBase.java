/**
 * 
 */
package com.amp.common.api.settings;

import com.amp.common.api.interfaces.ToolkitSettingsInterface;

/**
 * @author mveksler
 *
 */
public abstract class ToolkitSettingsBase implements ToolkitSettingsInterface 
{
	public boolean lcRes = true;
	
	public boolean isLcRes() {
		return lcRes;
	}
	
	public void setLcRes(boolean gStatus) {
		this.lcRes = gStatus;
	}
	
	public boolean init()
	{
		return true;
	}
	
	public boolean getStatus()
	{
		return isLcRes();
	}
	
	public boolean freeResources()
    {	
		return true;
    }
}
