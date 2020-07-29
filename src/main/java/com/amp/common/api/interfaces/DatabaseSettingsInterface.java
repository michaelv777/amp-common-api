package com.amp.common.api.interfaces;

import com.amp.common.api.impl.ToolkitXML;

public interface DatabaseSettingsInterface
{
	public boolean initSettings();
	
	public boolean getReflectionData();
	
	public boolean setSettings(ToolkitXML iXML);
}