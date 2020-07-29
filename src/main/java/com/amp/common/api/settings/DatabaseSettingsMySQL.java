package com.amp.common.api.settings;

import com.amp.common.api.impl.ToolkitXML;

public class DatabaseSettingsMySQL extends DatabaseSettings
{
	public DatabaseSettingsMySQL(ToolkitXML iXML)
	{
		super(iXML);
		
		try
		{
			
		}
		catch( Exception e)
		{
			
		}
	}
	/*-----------------------------------------------------*/
	public boolean setSettings(ToolkitXML iXML)
	{
		 try
		 {
			 super.setSettings(iXML);
			 
			 return true;
		 }
		 catch( Exception e)
		 {
			 return false;
		 }
		 finally{}
	}
}
/*===============End DatabaseSettings======================*/