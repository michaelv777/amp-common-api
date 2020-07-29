package com.amp.common.api.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.amp.common.api.interfaces.ToolkitSettingsInterface;
import com.amp.common.api.settings.ToolkitSettingsSpring;

@Service
public class ToolkitDataProvider
{
	private static Logger LOG = 
			LoggerFactory.getLogger(ToolkitDataProvider.class);
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	ToolkitDatabase tDatabase = null;
		
	@Autowired
	ToolkitSQL tSQL = null;
	
	@Autowired
    @Qualifier("toolkitSettingsSpring")
    private ToolkitSettingsInterface tSettings = null;
	//---Variables
    private ToolkitReflection iReflection = null;
	
	private boolean lcRes = true;
	
	//---getters/setters
    public ToolkitReflection getiReflection() {
		return iReflection;
	}
    
	public void setiReflection(ToolkitReflection iReflection) {
		this.iReflection = iReflection;
	}
	
	public ToolkitSettingsInterface gettSettings() {
		return tSettings;
	}
	public void settSettings(ToolkitSettingsInterface tSettings) {
		this.tSettings = tSettings;
	}
	
	public ToolkitDatabase gettDatabase() {
		return tDatabase;
	}
	
	public void settDatabase(ToolkitDatabase tDatabase) {
		this.tDatabase = tDatabase;
	}
	
	public ToolkitSQL gettSQL() {
		return tSQL;
	}
	
	public void settSQL(ToolkitSQL tSQL) {
		this.tSQL = tSQL;
	}

	public ToolkitReflection getIReflection() {
		return iReflection;
	}

	public void setIReflection(ToolkitReflection reflection) {
		iReflection = reflection;
	}
	
	public boolean isLcRes() {
		return lcRes;
	}

	public void setLcRes(boolean lcRes) {
		this.lcRes = lcRes;
	}
	
	//---
	public ToolkitDataProvider()
	{
		 try
		 {
			 //this.setLcRes(this.init());
		 }
		 catch( Exception e )
		 {
			 LOG.error( e.getMessage() );
			 
			 this.setLcRes(false);
		 }
	}
	
	//---
	@PostConstruct
    public boolean init()
    {
    	String methodName = "";
        
    	boolean cRes = true;

        try
        {
        	this.iReflection = new ToolkitReflection();
        	
			methodName = this.iReflection.getMethodName();
			
			if ( cRes )
			{
				/*------create and load settings---------------------------*/
				/*
				this.tSettings = (ToolkitSettingsSpring)
						this.applicationContext.getBean("toolkitSettingsSpring");
				*/
				if ( null == this.tSettings )
	    		{
					LOG.error(methodName + "::toolkitSettings is NULL.");
					
					this.tSettings = new ToolkitSettingsSpring();
	    		}

				/*
				this.tSettings = new ToolkitSettingsConfig().
						getToolkitSettings(StringUtils.EMPTY, Optional.of(new Boolean(true)));
				
				*/
				cRes = this.tSettings.getStatus();
			}
		
			if ( cRes )
			{
	            /*------create and load sql queries file-------------------*/
				if ( this.tSQL != null )
				{
					this.tSQL.init(this.gettSettings().getSQLSettingsData());
				}
				else
				{
					this.tSQL = new ToolkitSQL(this.gettSettings().getSQLSettingsData());
				}
			}
			
			if ( cRes )
			{
	            /*------create and load database class---------------------*/
				if ( this.tDatabase != null )
				{
					this.tDatabase.init(this.gettSettings().getHibernateSettingsData());
				}
				else
				{
					this.tDatabase = new ToolkitDatabase(this.gettSettings().getHibernateSettingsData());
				}
			}
			
			this.setLcRes(cRes);
			
            return cRes;
        }
        catch (Exception e)
        {
        	if ( LOG != null )
        	{
        		LOG.error( methodName + ":" + e.getMessage() );
        	}
	   		
        	this.setLcRes(cRes = false);
        	
        	return cRes;
        }
    }
	
	//---
	@PreDestroy
	public boolean releaseResources()
	{
		 boolean cRes = true;
			
		 try
		 {
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 this.setLcRes(cRes = false);
	        	
	         return cRes;
		 }
	}
	//---
	
	//---
	public boolean loadSettings()
	{
		 boolean cRes = true;
			
		 try
		 {
			 if ( this.gettSettings() != null )
			 {
				 this.gettSettings().init();
			 }
			 
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 this.setLcRes(cRes = false);
	        	
	         return cRes;
		 }
		 finally{}
	}
	
    //---
	
    //---
    public boolean openConnection()
    {
    	boolean cRes = true;
    	
    	String methodName = "";
      
        try
        {
			methodName = this.iReflection.getMethodName();
			/*-------------Connect to database------------------------------*/
			cRes = this.tDatabase.openSQLConnectionToDatabase(
	            	 this.tSettings.getHibernateSettingsData());
			
			if (!cRes)
			{
				LOG.error(methodName + ":tDatabase.OpenSQLConnectionToDatabase is false!" );
				
				this.setLcRes(cRes = false);
			}
			else
			{
				LOG.info(methodName + ":tDatabase.OpenSQLConnectionToDatabase is successfull:" +
								 this.tSettings.getHibernateSettingsData().getHibernateConnectionUrl());
				
				this.setLcRes(cRes = true);
			}
			
			return cRes;
        }
        catch( Exception e)
        {
        	LOG.error( methodName + ":" + e.getMessage() );
        	
        	this.setLcRes(cRes = false);
        	
        	return cRes;
        }
    }
    //---
   
	public boolean closeConnection() 
	{
		boolean cRes = true;
    	
    	String methodName = "";
      
        try
        {
			methodName = this.iReflection.getMethodName();
			/*-------------Connect to database------------------------------*/
			cRes = this.tDatabase.closeSQLConnectionToDatabase();
			
			if (!cRes)
			{
				LOG.error(methodName + ":tDatabase.OpenSQLConnectionToDatabase is false!" );
				 
				 cRes = false;
			}
			return cRes;
        }
        catch( Exception e)
        {
        	LOG.info( methodName + ":" + e.getMessage() );
        	 
        	this.setLcRes(cRes = false);
        	
        	return cRes;
        }
	}
    //---
	
	//---
	public boolean freeResources()
    {
        boolean cRes = true;
        
        String methodName = "";
        
        try
        {
			methodName = this.iReflection.getMethodName();
			
	    	if ( this.tDatabase != null )
	    	{
	    		this.tDatabase.freeResources();
	    	}
	    	
	    	if ( this.tSQL != null )
	    	{
	    		this.tSQL.freeResources();
	    	}
        	
	    	if ( this.tSettings != null )
	    	{
	    		this.tSettings.freeResources();
	    	}
	    	
    		return cRes;
        }
        catch (Exception e)
        {
        	LOG.error( methodName + ":" + e.getMessage(), e );
       	 
        	this.setLcRes(cRes = false);
        	
        	return cRes;
        }
    }
	
	/*=========End DataProvider====================================================*/
	
}

