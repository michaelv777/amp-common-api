/**
 * 
 */
package com.amp.common.api.config.spring;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.amp.common.api.config.bean.PersistenceConfig;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author mveksler
 *
 */
@Configuration
@PropertySource("classpath:amp-common-api.properties")
@EnableTransactionManagement
public class PersistenceBeansConfig
{
	private final static Logger LOG = 
				LoggerFactory.getLogger(PersistenceBeansConfig.class);
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	private PersistenceConfig persistenceConfig;
	
    public PersistenceConfig getPersistenceConfig() {
		return persistenceConfig;
	}

	public void setPersistenceConfig(PersistenceConfig persistenceConfig) {
		this.persistenceConfig = persistenceConfig;
	}

	@Bean
    public ComboPooledDataSource dataSource() 
    {
        // a named datasource is best practice for later jmx monitoring
        ComboPooledDataSource dataSource = new ComboPooledDataSource("ampds");

        try 
        {
            dataSource.setDriverClass(this.getPersistenceConfig().getDB_DRIVER());
        } 
        catch (PropertyVetoException pve)
        {
        	LOG.error("Cannot load datasource driver (" + 
        			this.getPersistenceConfig().getDB_DRIVER() +") : " + pve.getMessage());
            
            return null;
        }
        
        dataSource.setJdbcUrl(this.getPersistenceConfig().getDB_URL());
        dataSource.setUser(this.getPersistenceConfig().getDB_USERNAME());
        dataSource.setPassword(this.getPersistenceConfig().getDB_PASSWORD());
        dataSource.setMinPoolSize(Integer.parseInt(this.getPersistenceConfig().getCONN_POOL_MIN_SIZE()));
        dataSource.setMaxPoolSize(Integer.parseInt(this.getPersistenceConfig().getCONN_POOL_MAX_SIZE()));
        dataSource.setMaxIdleTime(Integer.parseInt(this.getPersistenceConfig().getCONN_POOL_IDLE_PERIOD()));

        return dataSource;
    }
    
    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource)
    {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(this.getPersistenceConfig().getENTITYMANAGER_PACKAGES_TO_SCAN());
 
        Properties jpaProperties = new Properties();
     
        //Configures the used database dialect. This allows Hibernate to create SQL
        //that is optimized for the used database.
        jpaProperties.put("hibernate.dialect", this.getPersistenceConfig().getHIBERNATE_DIALECT());
 
        //Specifies the action that is invoked to the database when the Hibernate
        //SessionFactory is created or closed.
        jpaProperties.put("hibernate.hbm2ddl.auto", this.getPersistenceConfig().getHIBERNATE_HBM2DDL_AUTO());
 
        
        
        //If the value of this property is true, Hibernate writes all SQL
        //statements to the console.
        jpaProperties.put("hibernate.show_sql", this.getPersistenceConfig().getHIBERNATE_SHOW_SQL());
 
        //If the value of this property is true, Hibernate will format the SQL
        //that is written to the console.
        
        //jpaProperties.put("hibernate.format_sql", 
        //        env.getRequiredProperty("hibernate.format_sql")
        //);
 		
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
 
        LOG.debug("entityManagerFactory()--done!!!");
        
        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf)
    {
    	JpaTransactionManager transactionManager = new JpaTransactionManager();
	      
	    transactionManager.setEntityManagerFactory(emf);
	      
	    return transactionManager;
    }

}
