/**
 * 
 */
package com.amp.common.api.config.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author mveksler
 *
 */

@Component
@PropertySource("classpath:amp-common-api.properties")
public class PersistenceConfig
{
	@SuppressWarnings("unused")
	private final static Logger LOG = 
				LoggerFactory.getLogger(PersistenceConfig.class);
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Value("${spring.datasource.driver-class-name}")
    private String DB_DRIVER;

    @Value("${spring.datasource.password}")
    private String DB_PASSWORD;

    @Value("${spring.datasource.url}")
    private String DB_URL;

    @Value("${spring.datasource.username}")
    private String DB_USERNAME;

    @Value("${hibernate.dialect}")
    private String HIBERNATE_DIALECT;

    @Value("${hibernate.show_sql}")
    private String HIBERNATE_SHOW_SQL;

    @Value("${hibernate.hbm2ddl.auto}")
    private String HIBERNATE_HBM2DDL_AUTO;

    @Value("${hibernate.entitymanager.packagesToScan}")
    private String ENTITYMANAGER_PACKAGES_TO_SCAN;

    @Value("${c3p0.maxPoolSize}")
    private String CONN_POOL_MAX_SIZE;

    @Value("${c3p0.minPoolSize}")
    private String CONN_POOL_MIN_SIZE;

    @Value("${c3p0.idleConnectionTestPeriod}")
    private String CONN_POOL_IDLE_PERIOD;
    
	public String getDB_DRIVER() {
		return DB_DRIVER;
	}


	public void setDB_DRIVER(String dB_DRIVER) {
		DB_DRIVER = dB_DRIVER;
	}


	public String getDB_PASSWORD() {
		return DB_PASSWORD;
	}


	public void setDB_PASSWORD(String dB_PASSWORD) {
		DB_PASSWORD = dB_PASSWORD;
	}


	public String getDB_URL() {
		return DB_URL;
	}


	public void setDB_URL(String dB_URL) {
		DB_URL = dB_URL;
	}


	public String getDB_USERNAME() {
		return DB_USERNAME;
	}


	public void setDB_USERNAME(String dB_USERNAME) {
		DB_USERNAME = dB_USERNAME;
	}


	public String getHIBERNATE_DIALECT() {
		return HIBERNATE_DIALECT;
	}


	public void setHIBERNATE_DIALECT(String hIBERNATE_DIALECT) {
		HIBERNATE_DIALECT = hIBERNATE_DIALECT;
	}


	public String getHIBERNATE_SHOW_SQL() {
		return HIBERNATE_SHOW_SQL;
	}


	public void setHIBERNATE_SHOW_SQL(String hIBERNATE_SHOW_SQL) {
		HIBERNATE_SHOW_SQL = hIBERNATE_SHOW_SQL;
	}


	public String getHIBERNATE_HBM2DDL_AUTO() {
		return HIBERNATE_HBM2DDL_AUTO;
	}


	public void setHIBERNATE_HBM2DDL_AUTO(String hIBERNATE_HBM2DDL_AUTO) {
		HIBERNATE_HBM2DDL_AUTO = hIBERNATE_HBM2DDL_AUTO;
	}


	public String getENTITYMANAGER_PACKAGES_TO_SCAN() {
		return ENTITYMANAGER_PACKAGES_TO_SCAN;
	}


	public void setENTITYMANAGER_PACKAGES_TO_SCAN(String eNTITYMANAGER_PACKAGES_TO_SCAN) {
		ENTITYMANAGER_PACKAGES_TO_SCAN = eNTITYMANAGER_PACKAGES_TO_SCAN;
	}


	public String getCONN_POOL_MAX_SIZE() {
		return CONN_POOL_MAX_SIZE;
	}


	public void setCONN_POOL_MAX_SIZE(String cONN_POOL_MAX_SIZE) {
		CONN_POOL_MAX_SIZE = cONN_POOL_MAX_SIZE;
	}


	public String getCONN_POOL_MIN_SIZE() {
		return CONN_POOL_MIN_SIZE;
	}


	public void setCONN_POOL_MIN_SIZE(String cONN_POOL_MIN_SIZE) {
		CONN_POOL_MIN_SIZE = cONN_POOL_MIN_SIZE;
	}


	public String getCONN_POOL_IDLE_PERIOD() {
		return CONN_POOL_IDLE_PERIOD;
	}


	public void setCONN_POOL_IDLE_PERIOD(String cONN_POOL_IDLE_PERIOD) {
		CONN_POOL_IDLE_PERIOD = cONN_POOL_IDLE_PERIOD;
	}
}
