package com.amp.common.api.util;

import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amp.common.api.settings.HibernateSettings;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class ToolkitHibernateUtil 
{
	private static Logger cLogger = 
			LoggerFactory.getLogger(ToolkitHibernateUtil.class);
	
	private static ToolkitHibernateUtil hibernateUtil = null;
	
	private SessionFactory sessionFactory = null;
	
	private Configuration cAnnotationCfg = null;
	
	public <T> SessionFactory buildSessionFactory(
			HibernateSettings hbsSettings, 
			List<Class<? extends Object>> clazzes) 
	{
		try 
		{
			if (cAnnotationCfg == null) 
			{
				Properties props = new Properties();

				/*props.put("hibernate.archive.autodetection", 
						hbsSettings.getHibernateArchiveAutodetection());
				
				props.put("hibernate.cache.provider_class",
						hbsSettings.getHibernateCacheProviderClass());
				
				props.put("hibernate.session_factory",
						hbsSettings.getHibernateSessionFactory());

				props.put("hibernate.transaction.factory_class",
						hbsSettings.getHibernateTransactionFactoryClass());

				props.put("hibernate.current_session_context_classs"Environment.CURRENT_SESSION_CONTEXT_CLASS,
						hbsSettings.getHibernateCurrentSessionContextClass());*/

				props.put("javax.persistence.validation.mode","none");
				
				props.put(Environment.AUTOCOMMIT, "false");
				
				props.put(/*"hibernate.dialect"*/Environment.DIALECT,
						hbsSettings.getHibernateDialect());

				props.put(/*"hibernate.connection.url"*/Environment.URL,
						hbsSettings.getHibernateConnectionUrl());

				props.put(/*"hibernate.connection.driver_class"*/Environment.DRIVER,
						hbsSettings.getHibernateConnectionDriverClass());
				
				props.put(/*"hibernate.connection.username"*/Environment.USER,
						hbsSettings.getHibernateConnectionUsername());
				
				props.put(/*"hibernate.connection.password"*/Environment.PASS,
						hbsSettings.getHibernateConnectionPassword());
				
				props.put(/*"hibernate.connection.password"*/Environment.SHOW_SQL,
						true);
				
				cAnnotationCfg = new Configuration().setProperties(props);
				
				for (Class<? extends Object> clazz : clazzes)
				{
					cAnnotationCfg.addAnnotatedClass(clazz);
				}
			}
			
			ServiceRegistry serviceRegistry = 
					new StandardServiceRegistryBuilder().
						applySettings(cAnnotationCfg.getProperties()).build();
			
			if (sessionFactory == null) 
			{
				sessionFactory = cAnnotationCfg.buildSessionFactory(serviceRegistry);
			}

			cLogger.info("Test connection with the database created successfuly.");
			
			return getSessionFactory();
		} 
		catch (HibernateException e) 
		{
			cLogger.error(e.getMessage(), e);
			
			return null;
		}
		catch (Exception e) 
		{
			cLogger.error(e.getMessage(), e);

			return null;
		}
	}
	
	public static synchronized ToolkitHibernateUtil getInstance() throws HibernateException {
        if (hibernateUtil == null) {
        	hibernateUtil = new ToolkitHibernateUtil();
        }
 
        return hibernateUtil;
    }
	
	public static synchronized ToolkitHibernateUtil getInstance(
			HibernateSettings hbsSettings, 
			List<Class<? extends Object>> clazzes) throws HibernateException 
	{
        if (hibernateUtil == null)
        {
        	hibernateUtil = new ToolkitHibernateUtil();
        	
        	hibernateUtil.buildSessionFactory(hbsSettings, clazzes); 
        }
 
        return hibernateUtil;
    }
	
	public Session getSession() throws HibernateException 
	{
        Session session = sessionFactory.openSession();
    
        if (!session.isConnected()) 
        {
            this.reconnect();
        }
        return session;
    }
	
	private void reconnect() throws HibernateException 
	{
		sessionFactory = cAnnotationCfg.buildSessionFactory();
    }
	
	public SessionFactory getSessionFactory() 
	{ 
		return sessionFactory; 
	} 
	
}
