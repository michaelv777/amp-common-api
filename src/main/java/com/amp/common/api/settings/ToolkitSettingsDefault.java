/**
 * 
 */
package com.amp.common.api.settings;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amp.common.api.impl.ToolkitConstants;
import com.amp.common.api.impl.ToolkitReflection;
import com.amp.common.api.impl.ToolkitSQL;
import com.amp.common.api.impl.ToolkitXML;

/**
 * @author MVEKSLER
 *
 */
public class ToolkitSettingsDefault extends ToolkitSettingsBase
{
	private static Logger LOG = 
			LoggerFactory.getLogger(ToolkitSQL.class);
	
	//---class variables
	private CommonSettings    cSettings   = null;
	
	private SQLSettings       sqlSettings = null;
	
	private DatabaseSettings  dbSettings  = null;
	
	private HibernateSettings hbsSettings = null;
	
	private ToolkitXML iSettingsXML = null;
	
	private String settingsFilePath = ""; 
	
	private String configsFolderPath = "";
	
	private ToolkitReflection iReflection = null;
	
	//---getters/setters
	public SQLSettings getSqlSettings() {
		return sqlSettings;
	}
	public void setSqlSettings(SQLSettings sqlSettings) {
		this.sqlSettings = sqlSettings;
	}
	public DatabaseSettings getDbSettings() {
		return dbSettings;
	}
	public void setDbSettings(DatabaseSettings dbSettings) {
		this.dbSettings = dbSettings;
	}
	
	public ToolkitXML getSetiingsXML() {
		return iSettingsXML;
	}
	public void setSettingsXML(ToolkitXML iXML) {
		this.iSettingsXML = iXML;
	}
	
	public String getConfigsFolderPath() {
		return configsFolderPath;
	}
	public void setConfigsFolderPath(String folderPath) {
		this.configsFolderPath = folderPath;
	}
	
	public void setcSettings(CommonSettings cSettings) {
		this.cSettings = cSettings;
	}
	
	public String getSettingsFilePath() {
		return settingsFilePath;
	}
	public void setSettingsFilePath(String settingsFilePath) {
		this.settingsFilePath = settingsFilePath;
	}
	
	public HibernateSettings getHbsSettings() {
		return hbsSettings;
	}
	
	public void setHbsSettings(HibernateSettings hbsSettings) {
		this.hbsSettings = hbsSettings;
	}
	
	public ToolkitXML getiSettingsXML() {
		return iSettingsXML;
	}
	public void setiSettingsXML(ToolkitXML iSettingsXML) {
		this.iSettingsXML = iSettingsXML;
	}
	public ToolkitReflection getiReflection() {
		return iReflection;
	}
	public void setiReflection(ToolkitReflection iReflection) {
		this.iReflection = iReflection;
	}
	//---class functions
	public ToolkitSettingsDefault()
	{
		 String methodName = "";
			 
		 try
         {
			 this.iReflection = new ToolkitReflection();
			 
			 methodName = this.iReflection.getMethodName();
			 
			 this.setLcRes(this.init());
         }
         catch( Exception e )
         {
        	 LOG.error(methodName + "::" + e.getMessage(), e);
        	 
        	 this.setLcRes(false);
         }
	}
	
	public ToolkitSettingsDefault(boolean isInit)
	{
		 String methodName = "";
			 
		 try
         {
			 this.iReflection = new ToolkitReflection();
			 
			 methodName = this.iReflection.getMethodName();
			 
			 if ( isInit )
			 {
				 this.setLcRes(this.init());
			 }
         }
         catch( Exception e )
         {
        	 LOG.error(methodName + "::" + e.getMessage(), e);
        	 
        	 this.setLcRes(false);
         }
	}
	
	public boolean init()
    {
    	boolean cRes = true;
    	 
        try
        {
        	 if ( ToolkitConstants.isLoadSettingsFromWar )
        	 {
        		 cRes = this.initSettingsFromWar();
        	 }
        	 
        	 else if ( ToolkitConstants.isLoadSettingsFromJar )
        	 {
        		 cRes = this.initSettingsFromJar();
        	 }
        	 
        	 else if ( ToolkitConstants.isLoadSettingsFromFS )
			 {
				 cRes = this.initSettingsFromFS();
			 }
			 
			 if ( cRes )
			 {
				 cRes = this.loadAllSettings();
			 }
			 
			 this.setLcRes(cRes);
			 
			 return cRes;
        }
        catch (Exception e)
        {
        	LOG.error(e.getMessage(), e);
        	
            this.setLcRes(false);

            return false; 
        }
    }
	
	/**
	 * @param 
	 * @return
	 */
	private boolean initSettingsFromFS() 
	{
		boolean cRes = true;
		
		try
        {
			 if ( ToolkitConstants.isLoadSettingsFromFS)
			 {
				 cRes = this.setConfigFolder(ToolkitConstants.FS_CONFIG_FODLER);
			 }
			 //-------------------------------------------------------------
			 if ( ToolkitConstants.isLoadSettingsFromFS )
			 {
				 cRes = this.setFSSettingsFile();
			 }
			 //-------------------------------------------------------------
			 if ( ToolkitConstants.isLoadSettingsFromFS)
			 {
				if ( this.iSettingsXML != null )
				{
					this.iSettingsXML.freeResources();
				}
				
			    this.iSettingsXML = new ToolkitXML(this.getSettingsFilePath());
			    
			    cRes =  this.iSettingsXML.isLcRes();
			 }
			 
			 return cRes;
        }
	    catch (Exception e)
	    {
	    	LOG.error(e.getMessage(), e);
	    	
	        this.setLcRes(false);
	
	        return false; 
	    }
	}
	
	/**
	 * 
	 * @return boolean
	 */
	
	private boolean initSettingsFromJar() 
	{
		boolean cRes = true;
   	 
        try
        {
        	
        	if ( !ToolkitConstants.isLoadSettingsFromFS)
        	{
				
				 if ( cRes )
				 {
					 cRes = this.setJarSettingsFile();
				 }
				 if ( cRes )
				 {
					if ( this.iSettingsXML != null )
					{
						this.iSettingsXML.freeResources();
					}
					 
				    this.iSettingsXML = new ToolkitXML(this.settingsFilePath);
				    
				    cRes =  this.iSettingsXML.isLcRes();
				 }
        	}

        	return cRes;
        }
	    catch (Exception e)
	    {
	    	LOG.error(e.getMessage(), e);
	    	
            this.setLcRes(false);

            return false; 
	    }
	}
	
	/**
	 * 
	 * @return boolean
	 */
	private boolean initSettingsFromWar() 
	{
		boolean cRes = true;
   	 
		String cSettingsFile = "";
		
        try
        {
    		
			 if ( cRes )
			 {
				if ( this.iSettingsXML != null )
				{
					this.iSettingsXML.freeResources();
				}
			 }
			 
			 if ( cRes )
			 {
				cSettingsFile = ToolkitConstants.TOOLKIT_SETTINGS_FILE_JAR;
				
				if ( (null == cSettingsFile) || (cSettingsFile.equals("")) )
				{
					cRes = false;
				}
			 }
			 
			 if ( cRes )
			 {
			    this.iSettingsXML = new ToolkitXML(cSettingsFile);
			    
			    cRes =  this.iSettingsXML.isLcRes();
			 }

        	return cRes;
        }
	    catch (Exception e)
	    {
	    	LOG.error(e.getMessage(), e);
	    	
            this.setLcRes(false);

            return false; 
	    }
	}
	
	public boolean loadAllSettings()
    {
        try
        {
        	 this.setLcRes(true);
			
			 /*--------set common settings--------------*/
			 this.cSettings = new CommonSettings();
			 if ( !this.cSettings.setSettings(this.iSettingsXML))
			 {
				 this.setLcRes(false);
			 }
			 
			 /*-------set sql statements settings-------*/
			 this.sqlSettings = new SQLSettings();
			 if ( !this.sqlSettings.setSettings(this.iSettingsXML))
			 {
				 this.setLcRes(false);
			 }
			 
			 /*-------set database settings-------------*/
			 this.dbSettings = new DatabaseSettings();
			 if ( !this.dbSettings.setSettings(this.iSettingsXML))
			 {
				 this.setLcRes(false);
			 }
			 
			 /*-------set Hibernate settings-------------*/
			 this.hbsSettings = new HibernateSettings();
			 if ( !this.hbsSettings.setSettings(this.iSettingsXML))
			 {
				 this.setLcRes(false);
			 }
			 
			 return this.isLcRes();
        }
        catch (Exception e)
        {
        	LOG.error(e.getMessage(), e);
        	
            this.setLcRes(false);

            return false; 
        }
    }
	/*======================================================================*/
	public boolean setFSSettingsFile()
	{
		 boolean cRes = true;
			
		 String methodName = "";
 		
	     try
	     {
	        methodName = this.iReflection.getMethodName();
			/*----------if settings file exists - load it----------------------*/
			this.setSettingsFilePath(ToolkitConstants.TOOLKIT_SETTINGS_FILE_FS);
			
			File sfile = new File(this.getSettingsFilePath());
			
		    if( !sfile.exists() )
            {
				System.out.println(
						methodName + "::LoadConfiguration settings file not exists:" + 
						this.getSettingsFilePath());
				
				this.setLcRes(cRes = false);
            }
			 
			return cRes;
		 }
		 catch( Exception e)
		 {
			 LOG.error(methodName + "::" + e.getMessage(), e);
			 
			 this.setLcRes( cRes = false );
			 
			 return cRes;
		 }
	}
	
	public boolean setConfigFolder(String configFolder)
	{
		 boolean cRes = true;
			
		 try
		 {
			 this.setConfigsFolderPath(configFolder);  
			 
			 if ( this.getConfigsFolderPath() == null)
			 {
				 cRes = false;
			 }
			 else
			 {
				 if ( StringUtils.isBlank(this.getConfigsFolderPath()) )
				 {
					 cRes = false;
				 }
			 }
			 
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 LOG.error(e.getMessage(), e);
			 
			 this.setLcRes( cRes = false );
			 
			 return cRes;
		 }
	}
	
	
    public boolean setJarSettingsFile()
    {
    	boolean cRes = true;
		
    	String methodName = "";
    		
        try
        {
        	methodName = this.iReflection.getMethodName();
			
            this.setSettingsFilePath(ToolkitConstants.TOOLKIT_SETTINGS_FILE_JAR);
			
            return cRes;
        }
        catch (Exception e)
        {
        	LOG.error(methodName + "::" + e.getMessage(), e);
            
        	this.setLcRes(cRes = false);
        	
            return cRes;
        }
    }
	
    public CommonSettings getcSettings() 
    { 
    	return this.cSettings; 
    }
   
    public SQLSettings getSQLSettings() 
    { 
    	return this.sqlSettings; 
    }
   
    public DatabaseSettings getDatabaseSettings() 
    { 
    	return this.dbSettings; 
    }
	
    public boolean freeResources()
    {
        boolean cRes = true;

        try
        {
	    	if ( this.iSettingsXML != null )
	    	{
	    		this.iSettingsXML.freeResources();
	    	}
	    	
    		return cRes;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
	@Override
	public HibernateSettings getHibernateSettingsData() {
		
		return this.getHbsSettings();
	}
	
	@Override
	public SQLSettings getSQLSettingsData() {
		
		return this.getSqlSettings();
	}
}


