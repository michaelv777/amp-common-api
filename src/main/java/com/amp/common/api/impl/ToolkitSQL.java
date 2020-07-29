/**
 * 
 */
package com.amp.common.api.impl;

import java.io.File;
import java.net.URL;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;

import com.amp.common.api.settings.SQLSettings;


/**
 * @author MVEKSLER
 *
 */
@Component
public class ToolkitSQL 
{
	private static Logger LOG = LoggerFactory.getLogger(ToolkitSQL.class);
	
	/*-------------Class Variables-------------------------------------*/
	private SQLSettings sqlSettings = null;
	
	private ToolkitReflection iReflection = null;
    
    private ToolkitXML iXML = null;
    
	/*-------------Save result for last call to class's methods--------*/

    /*----------settings file - read and save at the program start-----*/
    private String sqlFile = "";
   
    
    /*-------------Getters/Setters---------------------------------------*/
  
	public ToolkitReflection getIReflection() {
		return iReflection;
	}
	
	public void setIReflection(ToolkitReflection reflection) {
		iReflection = reflection;
	}
	
	public String getSqlFile() {
		return sqlFile;
	}
	
	public void setSqlFile(String sqlFile) {
		this.sqlFile = sqlFile;
	}
    
	
   	public SQLSettings getSqlSettings() {
		return sqlSettings;
	}

	public void setSqlSettings(SQLSettings sqlSettings) {
		this.sqlSettings = sqlSettings;
	}

	/**
   	 * @return the iXML
   	 */
   	public ToolkitXML getiXML() {
   		return iXML;
   	}
   	/**
   	 * @param iXML the iXML to set
   	 */
   	public void setiXML(ToolkitXML iXML) {
   		this.iXML = iXML;
   	}
    
   	
    /*-------------Class Methods---------------------------------------*/
    public ToolkitSQL()
	{
    	try
    	{
    		this.iReflection = new ToolkitReflection();
    	}
    	catch( Exception e ){}
	}
   
	/*=================================================================*/
    public ToolkitSQL(SQLSettings sqlSettings)
    {
        
        try
        {
        	this.iReflection = new ToolkitReflection();
        	
        	this.init(sqlSettings);
        }
        catch (Exception e)
        {
        	LOG.error(e.getMessage(), e );
        }
    }
    /*==================================================================================*/
    public boolean checkSQLFile()
    {
    	String methodName = "";
    	
        try
        {
        	methodName = this.iReflection.getMethodName();
            
        	LOG.warn(methodName + ":" + this.sqlFile + " check if sql file exists!" );
        	
        	File sFile = null;
        	
        	if ( this.sqlSettings.isLoadSettingsFromFS())
        	{
        		sFile = new File(this.sqlFile);
        	}
        	else
        	{
        		ClassLoader classLoader = getClass().getClassLoader();
    			
    			URL url = classLoader.getResource(this.sqlFile);
    			
    			if ( url != null )
    			{
    				sFile = new File(url.getFile());
    			}
        	}
        	
			if ( (sFile == null) || (!sFile.exists()))
            {
				LOG.warn(methodName + ":" + this.sqlFile + " doesn't exists!" );
            }
           
			return true;
        }
        catch (Exception e)
        {
        	LOG.error(methodName + ":" + e.getMessage(), e );
            
            return false;
        }
    }
    /*==================================================================================*/
    public boolean init(SQLSettings sqlSettings)
    {
    	String methodName = "";
        
        try
        {
        	methodName = this.iReflection.getMethodName();
         
        	this.setSqlSettings(sqlSettings);
       	 
        	this.setSqlFile(sqlSettings.getSqlFile());
        	
        	this.iXML = new ToolkitXML(this.sqlFile);
        	
        	return true;
        }
        catch (Exception e)
        {
        	LOG.error(methodName + ":" + e.getMessage(), e );

            return false; 
        }
    }
    /*==================================================================================*/
   
    /*========Get sql query by function name===================================================*/
    public String getSqlQueryByFunctionName(String sqlFunName,
                                            Vector<String> repParams)
    {
    	String methodName = "";
        String sqlQuery = "";

        try
        {
        	methodName = this.iReflection.getMethodName();

            if (this.iXML.getInputSource() == null)
            {
            	LOG.warn(methodName + ":" + " is null for " + this.sqlFile);

                return StringUtils.EMPTY;
            }

            Node csNode = this.iXML.getXMLNode(sqlFunName);
            
            if (csNode != null)
            {
                sqlQuery = csNode.getTextContent();
            }
            else
            {
            	LOG.error(methodName + ":" + "Sql statement not found for " + sqlFunName);
            	
            	return StringUtils.EMPTY;
            }

            /*------------------replace all query parameters------------------------------------*/
            StringBuilder sqlQueryBld = new StringBuilder(sqlQuery);
            
            int par_index = 0;
            int rep_index = 0;

            rep_index = sqlQuery.indexOf(ToolkitConstants.SQL_REP);
            
            while (rep_index >= 0)
            {
            	String parValue = String.valueOf(repParams.elementAt(par_index));
                
            	sqlQueryBld = new StringBuilder(sqlQuery);
                
            	sqlQueryBld.replace(rep_index,
                					rep_index + 
                					ToolkitConstants.SQL_REP.length(),
                                    parValue);
                
            	sqlQuery = sqlQueryBld.toString();

                ++par_index;
                
                rep_index = sqlQuery.indexOf(ToolkitConstants.SQL_REP);
            }
            

            return sqlQuery;
        }
        catch (Exception e)
        {
        	LOG.error(methodName + ":" + e.getMessage(), e );

            return StringUtils.EMPTY;
        }
        finally { }
    }
    
    /*---Get sql query by function name---*/
    public String getSqlQueryByFunctionName(String sqlFunName)
    {
    	String methodName = "";
        String sqlQuery = "";

        try
        {
        	methodName = this.iReflection.getMethodName();

            if (this.iXML.getInputSource() == null)
            {
            	LOG.warn(methodName + ":" + " is null for " + this.sqlFile);

                return ToolkitConstants.DEFAULT_STR;
            }

            Node csNode = this.iXML.getXMLNode(sqlFunName);
            if (csNode != null)
            {
                sqlQuery = csNode.getTextContent();
            }
            else
            {
            	LOG.error(methodName + ":" + "Sql statement not found for " + sqlFunName);
            }

            return sqlQuery;
        }
        catch (Exception e)
        {
        	LOG.error(methodName + ":" + e.getMessage(), e );
        	
            return StringUtils.EMPTY;
        }
        finally { }
    }
    /*------------------------------------------------------------------------------*/
    public boolean freeResources()
    {
        boolean cRes = true;

        String methodName = "";
        
        try
        {
        	methodName = this.iReflection.getMethodName();
        	
	    	if ( this.iXML != null )
	    	{
	    		this.iXML.freeResources();
	    	}
	    	
    		return cRes;
        }
        catch (Exception e)
        {
        	LOG.error(methodName + ":" + e.getMessage() );
        	
            return false;
        }
    }
}
