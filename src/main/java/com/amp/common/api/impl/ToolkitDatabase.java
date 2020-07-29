/**
 * 
 */
package com.amp.common.api.impl;

/**
 * @author michaelv
 *
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amp.common.api.settings.DatabaseSettings;
import com.amp.common.api.settings.HibernateSettings;
import com.amp.common.api.util.ToolkitHibernateUtil;
import com.amp.jpa.entities.manager.PersistenceManager;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;


@Component
public class ToolkitDatabase 
{
	private static Logger LOG = 
			LoggerFactory.getLogger(ToolkitDatabase.class);
	
	/*---Class Variables---*/
	private String sqlConnectionString = "";
	
	private Connection sqlConnection = null;
    
    private DataSource sqlDataSource = null;
    
    private SessionFactory hbsSessions = null;
	
	private HibernateSettings hbsSettings = null;
	
    //---Reflection Variabsles--
    private ToolkitReflection iReflection = null;
    
    private final String urlMySQl = "jdbc:mysql://";
	
	//---Getters/Setters--
	public Connection getSqlConnection() {
		return sqlConnection;
	}
	
	public void setSqlConnection(Connection sqlConnection) {
		this.sqlConnection = sqlConnection;
	}
	
	public String getSqlConnectionString() {
		return sqlConnectionString;
	}
	
	public void setSqlConnectionString(String sqlConnectionString) {
		this.sqlConnectionString = sqlConnectionString;
	}
	
	public DataSource getSqlDataSource() {
		return sqlDataSource;
	}
	
	public void setSqlDataSource(DataSource sqlDataSource) {
		this.sqlDataSource = sqlDataSource;
	}
	
	
	
	public ToolkitReflection getiReflection() {
		return iReflection;
	}
	public void setiReflection(ToolkitReflection iReflection) {
		this.iReflection = iReflection;
	}
	
	
	/**
	 * @return the hbsSessions
	 */
	public SessionFactory getHbsSessions() {
		return hbsSessions;
	}
	/**
	 * @param hbsSessions the hbsSessions to set
	 */
	public void setHbsSessions(SessionFactory hbsSessions) {
		this.hbsSessions = hbsSessions;
	}
	
	public HibernateSettings getHbsSettings() {
		return hbsSettings;
	}

	public void setHbsSettings(HibernateSettings hbsSettings) {
		this.hbsSettings = hbsSettings;
	}

	public String getUrlMySQl() {
		return urlMySQl;
	}

	public ToolkitDatabase()
	{
		try
		{
			this.iReflection = new ToolkitReflection();
		}
		catch( Exception e )
		{
			
		}
	}
	/*-----------Class Methods--------------------------------------------------------------*/
	
    public ToolkitDatabase(HibernateSettings hbsSettings)
	{
		try
		{
			this.iReflection = new ToolkitReflection();
			
			this.init(hbsSettings);
		}
		catch( Exception e )
		{
			LOG.error(e.getMessage(), e);
		}
	}
	
    public boolean init(HibernateSettings hbsSettings)
    {
    	String methodName = "";
        
        try
        {
        	this.iReflection = new ToolkitReflection();
        	
        	methodName = this.iReflection.getMethodName();
        
        	this.setHbsSettings(hbsSettings);
        	
        	return true;
        }
        catch (Exception e)
        {
            LOG.error(methodName + ":" + e.getMessage(), e);

            return false; 
        }
    }
   
	public boolean getReflectionData()
	{
		 boolean cRes = true;
		
		 try
		 {
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 LOG.error(e.getMessage(), e);
			 
			 return false;
		 }
	}
	
	public boolean closeHibernateSession(Session hbsSession)
	{
		boolean cRes = true;
		
		String methodName = "";
			
		 try
		 {
			 methodName = this.iReflection.getMethodName();
			 
			 if ( hbsSession != null )
			 {
				 hbsSession.close();
			 }
			 
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 LOG.error(methodName + ":" + e.getMessage(), e );
			 
			 return false;
		 }
	}
	
	public boolean getHibernateSession(List<Class<? extends Object>> clazzes)
	{
		 String methodName = "";
			
		 try
		 {
			 methodName = this.iReflection.getMethodName();
			 
			 this.hbsSessions = ToolkitHibernateUtil.getInstance(
					 this.getHbsSettings(), clazzes).getSessionFactory();

			 if ( null == this.hbsSessions )
			 {
				 LOG.error(methodName + ":: hbsSessions is null");
				 
				 return false;
			 }
			 
			 return true;
		 }
		 catch( Exception e)
		 {
			 LOG.error(methodName + ":" + e.getMessage(), e );
			 
			 return false;
		 }
		 finally{}
	}
	
	
	public org.hibernate.Session getHibernateSessionDataSource(
			List<Class<? extends Object>> clazzes)
	{
		 String methodName = "";
		
		 Session hbsSession  = null;
			
		 try
		 {
			 methodName = this.iReflection.getMethodName();
			 
			 hbsSession = ToolkitHibernateUtil.getInstance(
					 this.getHbsSettings(), clazzes).getSession();
			
			 this.hbsSessions = ToolkitHibernateUtil.getInstance(
					 this.getHbsSettings(), clazzes).getSessionFactory();
			
			 return hbsSession;
		 }
		 catch( Exception e)
		 {
			 LOG.error(methodName + ":" + e.getMessage(), e );
			 
			 return null;
		 }
		 finally{}
	}
    
    public void constactConnectionStringMySQL(DatabaseSettings dbSettings)
	{
    	String methodName = "";
    		
		try
		{
				methodName = this.iReflection.getMethodName();
				
				/*-----check if dbSettings-----------------*/
				if ( dbSettings != null )
				{
			
				 /*-----constract connection string--------*/
				 this.sqlConnectionString = this.urlMySQl;
				
				 this.sqlConnectionString = this.sqlConnectionString + 
				 dbSettings.Server;
				
				 this.sqlConnectionString = this.sqlConnectionString + 
				 ToolkitConstants.SEMICOLON;
				
				 this.sqlConnectionString = this.sqlConnectionString + 
				 dbSettings.Port;
				
				 this.sqlConnectionString = this.sqlConnectionString + 
				 ToolkitConstants.SLASH;
				
				 this.sqlConnectionString = this.sqlConnectionString + 
				 dbSettings.Database;
			}
			else
			{
				 this.sqlConnectionString = "";
			}
		}
		catch( Exception e )
		{
			LOG.error( methodName + ":" + e.getMessage(), e );
		}
	}
    
    protected boolean validateConnectionSettings(String dbResource,
    										     String contextFactory,
    										     String providerURL)
    {
    	boolean cRes = true;
    	
    	String methodName = "";
    	
    	try
        {
			methodName = this.iReflection.getMethodName();
			
			if ( "".equals(dbResource ) )
			{
				LOG.error( methodName + "::dbResource is empty!" );
				cRes = false;
			}
			
			if ( "".equals(contextFactory ) )
			{
				LOG.error( methodName + "::contextFactory is empty!" );
				cRes = false;
			}
			
			if ( "".equals(providerURL ) )
			{
				LOG.error( methodName + "::providerURL is empty!" );
				cRes = false;
			}
			
			return cRes;
        }
        catch (Exception e)
        {
        	LOG.error( methodName + ":" + e.getMessage() );
	        
	   		return false;
        }
    }
  
    public boolean openSQLConnectionToDatabase(HibernateSettings hbsSettings)
	{
    	String methodName = "";
    	
		try
		{
			methodName = this.iReflection.getMethodName();
			
			MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
			ds.setUrl(hbsSettings.getHibernateConnectionUrl());
		    ds.setUser(hbsSettings.getHibernateConnectionUsername());
		    ds.setPassword(hbsSettings.getHibernateConnectionPassword());
		    //ds.setServerName(hbsSettings.Server);
		    //ds.sesetPort(Integer.valueOf(hbsSettings.Port.trim()));
		    //ds.setDatabaseName(hbsSettings.Database);
		    
		    this.sqlConnection = ds.getConnection();
			//this.sqlConnection.setAutoCommit(true);
			this.sqlConnection.setTransactionIsolation(
						Connection.TRANSACTION_SERIALIZABLE);
			
		  return true;
		}
		catch ( Exception e )
		{
			 LOG.error( methodName + ":" + e.getMessage() );
			 
	         return false;
		}
	}
   
    public boolean closeSQLConnectionToDatabase()
	{
    	String methodName = "";
    	
		try
		{
			methodName = this.iReflection.getMethodName();
			
            if ( (!this.sqlConnection.isClosed()) )
            {
                this.sqlConnection.close();
            }

			return true;
		}
		catch ( Exception e )
		{
			LOG.error( methodName + ":" + e.getMessage() );
		    
			return false;
		}
	}
   
    public int executeSQLCommand(
    		String sqlQuery, String[] sqlParameters) throws SQLException 
	{
		int rowsAffected = -1; 
		
		String methodName = "";
		
		PreparedStatement sqlStatement = null;
		
		try
		{
			methodName = this.iReflection.getMethodName();
			
			/*---------Check if we can do opeartion------------------------------------------*/
			if ( (this.sqlConnection.isClosed())  || (this.sqlConnection.isReadOnly()) )
			{
				return rowsAffected;
			}
			
			/*---------Execute sql command--------------------------------------------------*/
			sqlStatement = this.sqlConnection.prepareStatement(sqlQuery, sqlParameters);
			
			rowsAffected = sqlStatement.executeUpdate();
			
			/*---------Check if we good results---------------------------------------------*/
            return rowsAffected;
	    }
		catch(SQLException ex)
		{
			LOG.info("SQLException�information");
			
			while( ex != null)
		    {
				LOG.info("ErrorMsg:"  + ex.getMessage());
				LOG.info("SQLSTATE:"  + ex.getSQLState());
				LOG.info("ErrorCode:" + ex.getErrorCode());
				
				ex = ex.getNextException();
			}
			
			return rowsAffected;
		}
       
		catch( Exception e )
		{
			LOG.info( methodName + ":" + e.getMessage() );
			
            return rowsAffected;
		}
		finally
		{ 
			if ( (!(sqlStatement == null)) && (!((Connection) sqlStatement).isClosed()))
			{
				sqlStatement.close();
			}
		}
	}
   
    public int executeSQLCommand(String sqlQuery) throws SQLException 
	{
		int rowsAffected       = -1; 
		
		String methodName 	   = "";
		
		java.sql.Statement sqlStatement = null;
		
		try
		{
			methodName = this.iReflection.getMethodName();
			
			/*---------Check if we can do opeartion------------------------------------------*/
			if ( (this.sqlConnection.isClosed())  || (this.sqlConnection.isReadOnly()) )
			{
				 return rowsAffected;
			}
			
			/*---------Execute sql command--------------------------------------------------*/
			sqlStatement = this.sqlConnection.createStatement();
			
			rowsAffected = sqlStatement.executeUpdate(sqlQuery);
			
			/*---------Check if we good results---------------------------------------------*/
			
            return rowsAffected;
	    }
		catch(SQLException ex)
		{
			LOG.error("SQLException�information");
			
			while( ex!=null )
		    {
				LOG.info("ErrorMsg:"  + ex.getMessage());
				LOG.info("SQLSTATE:"  + ex.getSQLState());
				LOG.info("ErrorCode:" + ex.getErrorCode());
				//ex.printStackTrace();
				ex = ex.getNextException();//�For�drivers�that�support�
			}
			
			return rowsAffected;
		}
       
		catch( Exception e )
		{
			LOG.error( methodName + ":" + e.getMessage() );
			
            return rowsAffected;
		}
		finally 
		{
			if ( !(sqlStatement == null))
			{
				sqlStatement.close();
			}
		}
	}
  
	public ResultSet executeSQLQuery(String sqlQuery, String[] sqlParameters) 
	{
		
		String methodName = "";
		
		PreparedStatement sqlStatement = null;
		
		ResultSet  sqlResultSet  = null;
		
		try
		{
			methodName = this.iReflection.getMethodName();
			
			/*---------Check if we can do opeartion------------------------------------------*/
			if ( (this.sqlConnection.isClosed())  ||
				 (this.sqlConnection.isReadOnly()) )
			{
				 return sqlResultSet;
			}
			
			/*---------Execute sql command--------------------------------------------------*/
			sqlStatement = this.sqlConnection.prepareStatement(sqlQuery, sqlParameters);
			
			sqlResultSet = sqlStatement.executeQuery();
			
			/*---------Check if we good results---------------------------------------------*/
			if ( sqlResultSet == null )
			{
				 return sqlResultSet;
			}
			
			//sqlStatement.close();
			
	        return sqlResultSet;
	    }
		catch(SQLException ex)
		{
			LOG.info("SQLException�information");
			
			while( ex != null)
		    {
				LOG.info("ErrorMsg:"  + ex.getMessage());
				LOG.info("SQLSTATE:"  + ex.getSQLState());
				LOG.info("ErrorCode:" + ex.getErrorCode());
				
				ex = ex.getNextException();//�For�drivers�that�support�
			}
			
			return sqlResultSet;
		}
		catch( Exception e )
		{
			LOG.info( methodName + ":" + e.getMessage() );
			
	        return sqlResultSet;
		}
	}
	
    public ResultSet executeSQLQuery(String sqlQuery) 
	{
		
		String methodName 	   = "";
		
		Statement sqlStatement = null;
		
		ResultSet  sqlResultSet  = null;
		
		try
		{
			methodName = this.iReflection.getMethodName();
			
			/*---------Check if we can do opeartion------------------------------------------*/
			if ( (this.sqlConnection.isClosed())  || (this.sqlConnection.isReadOnly()) )
			{
				 return sqlResultSet;
			}
			
			/*---------Execute sql command--------------------------------------------------*/
			sqlStatement = this.sqlConnection.createStatement();
			
			sqlResultSet = sqlStatement.executeQuery(sqlQuery);
			
			/*---------Check if we good results---------------------------------------------*/
			if ( sqlResultSet == null )
			{
				 return sqlResultSet;
			}
			
			//sqlStatement.close();
			
            return sqlResultSet;
	    }
		catch(SQLException ex)
		{
			LOG.info("SQLException�information");
			
			while( ex != null)
		    {
				LOG.info("ErrorMsg:"  + ex.getMessage());
				LOG.info("SQLSTATE:"  + ex.getSQLState());
				LOG.info("ErrorCode:" + ex.getErrorCode());
				
				ex = ex.getNextException();//�For�drivers�that�support�
			}
			
			return sqlResultSet;
		}
		catch( Exception e )
		{
			LOG.info( methodName + ":" + e.getMessage() );
			
            return sqlResultSet;
		}
	}
    /*========================================================================================*/
    public boolean closeResultSet(ResultSet  sqlResultSet)
	{
		try
		{
            if ( sqlResultSet != null )
            {
                  sqlResultSet.close();
            }

		    return true;
		}
		catch( Exception e )
		{
			return false;
		}
	}
   
  	public List<Class<? extends Object>> getPersistanceClasses()
  	{
  		List<Class<? extends Object>> cPersistanceClasses = new ArrayList<Class<? extends Object>>();
  		
  		try
  		{
  			cPersistanceClasses = PersistenceManager.getPersistanceClasses();
  			 
  			return cPersistanceClasses;
  		}
  		catch(Exception e)
  		{
  			return cPersistanceClasses;
  		}
  	}
  	
   
    public boolean freeResources()
    {
        boolean cRes = true;

        try
        {
	    	this.closeSQLConnectionToDatabase();
	    	
    		return cRes;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    
}
