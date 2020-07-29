package com.amp.common.api.settings;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.amp.common.api.impl.ToolkitConstants;
import com.amp.common.api.impl.ToolkitReflection;
import com.amp.common.api.impl.ToolkitXML;

public class HibernateSettings
{
	protected ToolkitReflection iReflection = null;
    protected String     className   = "";
    protected String     classNameL  = "";
    
	public String hibernateArchiveAutodetection;
	public String hibernateSessionFactory;
	public String hibernateJndiUrl;
	public String hibernateJndiClass;
	public String hibernateDialect;
	public String hibernateConnectionDatasource;
	public String hibernateOrderUpdates;
	
	public String hibernateCacheProviderClass;
	public String hibernateTransactionFactoryClass;
	public String hibernateCurrentSessionContextClass;
	public String hibernateConnectionDriverClass;
	public String hibernateConnectionUrl;
	public String hibernateConnectionUsername;
	public String hibernateConnectionPassword;
	/*-----------------------------------------------------*/
    public HibernateSettings()
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
    /*-----------------------------------------------------*/
    public boolean initSettings()
    {
        try
        {
        	this.hibernateArchiveAutodetection = ToolkitConstants.DEFAULT_STR;
        	this.hibernateSessionFactory       = ToolkitConstants.DEFAULT_STR;
        	this.hibernateJndiUrl              = ToolkitConstants.DEFAULT_STR;
        	this.hibernateJndiClass            = ToolkitConstants.DEFAULT_STR;
        	this.hibernateDialect              = ToolkitConstants.DEFAULT_STR;
        	this.hibernateConnectionDatasource = ToolkitConstants.DEFAULT_STR;
        	this.hibernateOrderUpdates         = ToolkitConstants.DEFAULT_STR;

        	this.hibernateCacheProviderClass         = ToolkitConstants.DEFAULT_STR;
        	this.hibernateTransactionFactoryClass    = ToolkitConstants.DEFAULT_STR;
        	this.hibernateCurrentSessionContextClass = ToolkitConstants.DEFAULT_STR;
        	this.hibernateConnectionDriverClass      = ToolkitConstants.DEFAULT_STR;
        	this.hibernateConnectionUrl 			 = ToolkitConstants.DEFAULT_STR;
        	this.hibernateConnectionUsername 		 = ToolkitConstants.DEFAULT_STR;
        	this.hibernateConnectionPassword 		 = ToolkitConstants.DEFAULT_STR;
    		
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
	public ToolkitReflection getiReflection() {
		return iReflection;
	}
	public void setiReflection(ToolkitReflection iReflection) {
		this.iReflection = iReflection;
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
	public String getHibernateArchiveAutodetection() {
		return hibernateArchiveAutodetection;
	}
	public void setHibernateArchiveAutodetection(
			String hibernateArchiveAutodetection) {
		this.hibernateArchiveAutodetection = hibernateArchiveAutodetection;
	}
	public String getHibernateSessionFactory() {
		return hibernateSessionFactory;
	}
	public void setHibernateSessionFactory(String hibernateSessionFactory) {
		this.hibernateSessionFactory = hibernateSessionFactory;
	}
	public String getHibernateJndiUrl() {
		return hibernateJndiUrl;
	}
	public void setHibernateJndiUrl(String hibernateJndiUrl) {
		this.hibernateJndiUrl = hibernateJndiUrl;
	}
	public String getHibernateJndiClass() {
		return hibernateJndiClass;
	}
	public void setHibernateJndiClass(String hibernateJndiClass) {
		this.hibernateJndiClass = hibernateJndiClass;
	}
	public String getHibernateDialect() {
		return hibernateDialect;
	}
	public void setHibernateDialect(String hibernateDialect) {
		this.hibernateDialect = hibernateDialect;
	}
	public String getHibernateConnectionDatasource() {
		return hibernateConnectionDatasource;
	}
	public void setHibernateConnectionDatasource(
			String hibernateConnectionDatasource) {
		this.hibernateConnectionDatasource = hibernateConnectionDatasource;
	}
	public String getHibernateOrderUpdates() {
		return hibernateOrderUpdates;
	}
	public void setHibernateOrderUpdates(String hibernateOrderUpdates) {
		this.hibernateOrderUpdates = hibernateOrderUpdates;
	}
	/**
	 * @return the hibernateCacheProviderClass
	 */
	public String getHibernateCacheProviderClass() {
		return hibernateCacheProviderClass;
	}
	/**
	 * @param hibernateCacheProviderClass the hibernateCacheProviderClass to set
	 */
	public void setHibernateCacheProviderClass(String hibernateCacheProviderClass) {
		this.hibernateCacheProviderClass = hibernateCacheProviderClass;
	}
	/**
	 * @return the hibernateTransactionFactoryClass
	 */
	public String getHibernateTransactionFactoryClass() {
		return hibernateTransactionFactoryClass;
	}
	/**
	 * @param hibernateTransactionFactoryClass the hibernateTransactionFactoryClass to set
	 */
	public void setHibernateTransactionFactoryClass(
			String hibernateTransactionFactoryClass) {
		this.hibernateTransactionFactoryClass = hibernateTransactionFactoryClass;
	}
	/**
	 * @return the hibernateCurrentSessionContextClass
	 */
	public String getHibernateCurrentSessionContextClass() {
		return hibernateCurrentSessionContextClass;
	}
	/**
	 * @param hibernateCurrentSessionContextClass the hibernateCurrentSessionContextClass to set
	 */
	public void setHibernateCurrentSessionContextClass(
			String hibernateCurrentSessionContextClass) {
		this.hibernateCurrentSessionContextClass = hibernateCurrentSessionContextClass;
	}
	/**
	 * @return the hibernateConnectionDriverClass
	 */
	public String getHibernateConnectionDriverClass() {
		return hibernateConnectionDriverClass;
	}
	/**
	 * @param hibernateConnectionDriverClass the hibernateConnectionDriverClass to set
	 */
	public void setHibernateConnectionDriverClass(
			String hibernateConnectionDriverClass) {
		this.hibernateConnectionDriverClass = hibernateConnectionDriverClass;
	}
	/**
	 * @return the hibernateConnectionUrl
	 */
	public String getHibernateConnectionUrl() {
		return hibernateConnectionUrl;
	}
	/**
	 * @param hibernateConnectionUrl the hibernateConnectionUrl to set
	 */
	public void setHibernateConnectionUrl(String hibernateConnectionUrl) {
		this.hibernateConnectionUrl = hibernateConnectionUrl;
	}
	/**
	 * @return the hibernateConnectionUsername
	 */
	public String getHibernateConnectionUsername() {
		return hibernateConnectionUsername;
	}
	/**
	 * @param hibernateConnectionUsername the hibernateConnectionUsername to set
	 */
	public void setHibernateConnectionUsername(String hibernateConnectionUsername) {
		this.hibernateConnectionUsername = hibernateConnectionUsername;
	}
	/**
	 * @return the hibernateConnectionPassword
	 */
	public String getHibernateConnectionPassword() {
		return hibernateConnectionPassword;
	}
	/**
	 * @param hibernateConnectionPassword the hibernateConnectionPassword to set
	 */
	public void setHibernateConnectionPassword(String hibernateConnectionPassword) {
		this.hibernateConnectionPassword = hibernateConnectionPassword;
	}
	
	
}