package com.amp.common.api.settings;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.amp.common.api.impl.ToolkitConstants;
import com.amp.common.api.impl.ToolkitReflection;
import com.amp.common.api.impl.ToolkitXML;
import com.amp.common.api.interfaces.DatabaseSettingsInterface;

public class DatabaseSettings implements DatabaseSettingsInterface
{
	protected ToolkitReflection iReflection = null;
    protected String     className   = "";
    protected String     classNameL  = "";
    
	public String  Server 	      	  = ToolkitConstants.DEFAULT_STR;
	public String  Port           	  = ToolkitConstants.DEFAULT_STR;
    public String  Database  	  	  = ToolkitConstants.DEFAULT_STR;
    public String  UserID 		  	  = ToolkitConstants.DEFAULT_STR;
    public String  PWD 			  	  = ToolkitConstants.DEFAULT_STR;
    public String  Authentication     = ToolkitConstants.DEFAULT_STR;
    public String  JDBCConnectionPool = ToolkitConstants.DEFAULT_STR;
    public String  JDBCResource       = ToolkitConstants.DEFAULT_STR;   
    public String  JDBCUrl            = ToolkitConstants.DEFAULT_STR;
    public String  JDBCDriverName     = ToolkitConstants.DEFAULT_STR;
    public boolean IsUpperCase        = false;
    public String  dbsDataType        = ToolkitConstants.AMP_DB_TYPE_MYSQL;
    
    /*-----------------------------------------------------*/
    public DatabaseSettings()
    {
         try
         {
        	 this.iReflection = new ToolkitReflection();	
             
             this.initSettings();	 	
         }
         catch( Exception e )
         {
        	 
         }
         finally {}
    }
    
    public DatabaseSettings(ToolkitXML iXML)
	{
		try
		{
			this.iReflection = new ToolkitReflection();	
			
			this.setSettings(iXML);
		}
		catch( Exception e)
		{
			
		}
	}
    /*-----------------------------------------------------*/
    public boolean initSettings()
    {
        try
        {
            this.Server         = ToolkitConstants.DEFAULT_STR;
            this.Database       = ToolkitConstants.DEFAULT_STR;
            this.UserID         = ToolkitConstants.DEFAULT_STR;
            this.PWD            = ToolkitConstants.DEFAULT_STR;
            this.Authentication = ToolkitConstants.DEFAULT_STR;
            this.JDBCConnectionPool = ToolkitConstants.DEFAULT_STR;
            this.JDBCResource       = ToolkitConstants.DEFAULT_STR; 
            this.JDBCUrl            = ToolkitConstants.DEFAULT_STR;
            this.JDBCDriverName     = ToolkitConstants.DEFAULT_STR;
            this.IsUpperCase    	= false;

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
        finally
        {
        }
    }
    /*-----------------------------------------------------*/
    public boolean getReflectionData()
	{
	 boolean cRes = true;
	
	 try
	 {
		 className  = this.iReflection.getClassName();
		 if ( className == null ) { cRes = false; }
		 
		 classNameL = getClass().getSimpleName();
		 
		 return cRes;
	 }
	 catch( Exception e)
	 {
		 return false;
	 }
	 finally{}
	} 
    /*-----------------------------------------------------*/
	public boolean setSettings(ToolkitXML iXML)
	{
	 boolean cRes = true;
	 Node  cNode  = null;
	 Field cField = null;
	 
	 try
	 {
		 //String methodName = this.iReflection.getMethodName();
		 
		 if( iXML == null )
		 {
			 cRes = false;
			 return cRes;
		 }
		 
		 if ( !this.getReflectionData())
		 {
			 cRes = false;
			 return cRes;
		 }
		
		 Node settNode = iXML.getXMLNode(this.classNameL);
		 if ( settNode == null)
		 {
		  cRes = false;
		  return cRes;
		 }
		 
		 NodeList cNodes = settNode.getChildNodes();
		 Field[] cFields = getClass().getDeclaredFields();
		 
		 for( int index = 0; index < cNodes.getLength(); ++index)
		 {
			 for( int jondex = 0; jondex < cFields.length; ++jondex )
			 {
				 cNode  = (Node)cNodes.item(index);
				 cField = (Field)cFields[jondex];
				 
				 if ( (cField.getName()).equals(cNode.getNodeName()) )
				 {
					  String textContent = cNode.getTextContent(); 
					  Type type = (Type) cField.getGenericType();
					  	
					  if ( type.equals(String.class ))
					  {
					   cField.set(this, textContent);
					  }
					  else if ( type.equals(boolean.class ))
					  {
					   boolean cBoolSet = Boolean.parseBoolean(textContent);
					   cField.setBoolean(this, cBoolSet);	
					  }
					  else if ( type.equals(int.class ))
					  {
					   int cIntSet = Integer.parseInt(textContent);
					   cField.setInt(this, cIntSet);	
					  }
				 }
			 }
		 }
		 return cRes;
	 }
	 catch( Exception e)
	 {
		 return false;
	 }
	 finally{}
	}
	/*-----------------------------------------------------*/
	
	public String getAuthentication() {
		return Authentication;
	}
	public String getDbsDataType() {
		return dbsDataType;
	}

	public void setDbsDataType(String dbsDataType) {
		this.dbsDataType = dbsDataType;
	}

	public void setAuthentication(String authentication) {
		Authentication = authentication;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassNameL() {
		return classNameL;
	}
	public void setClassNameL(String classNameL) {
		this.classNameL = classNameL;
	}
	public String getDatabase() {
		return Database;
	}
	public void setDatabase(String database) {
		Database = database;
	}
	public ToolkitReflection getIReflection() {
		return iReflection;
	}
	public void setIReflection(ToolkitReflection reflection) {
		iReflection = reflection;
	}
	public boolean isUpperCase() {
		return IsUpperCase;
	}
	public void setUpperCase(boolean isUpperCase) {
		IsUpperCase = isUpperCase;
	}
	public String getPWD() {
		return PWD;
	}
	public void setPWD(String pwd) {
		PWD = pwd;
	}
	public String getServer() {
		return Server;
	}
	public void setServer(String server) {
		Server = server;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getPort() {
		return Port;
	}
	public void setPort(String port) {
		Port = port;
	}
	public String getJDBCConnectionPool() {
		return JDBCConnectionPool;
	}
	public void setJDBCConnectionPool(String jDBCConnectionPool) {
		JDBCConnectionPool = jDBCConnectionPool;
	}
	public String getJDBCResource() {
		return JDBCResource;
	}
	public void setJDBCResource(String jDBCResource) {
		JDBCResource = jDBCResource;
	}
	public boolean isIsUpperCase() {
		return IsUpperCase;
	}
	public void setIsUpperCase(boolean isUpperCase) {
		IsUpperCase = isUpperCase;
	}
	public String getJDBCUrl() {
		return JDBCUrl;
	}
	public void setJDBCUrl(String jDBCUrl) {
		JDBCUrl = jDBCUrl;
	}
	public String getJDBCDriverName() {
		return JDBCDriverName;
	}
	public void setJDBCDriverName(String jDBCDriverName) {
		JDBCDriverName = jDBCDriverName;
	}
	
}