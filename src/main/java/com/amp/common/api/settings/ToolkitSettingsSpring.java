/**
 * 
 */
package com.amp.common.api.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amp.common.api.config.bean.PersistenceConfig;
import com.amp.common.api.config.bean.QueriesConfig;

/**
 * @author mveksler
 *
 */
@Component("toolkitSettingsSpring")
public class ToolkitSettingsSpring  extends ToolkitSettingsBase
{
	@Autowired
	private PersistenceConfig persistenceConfig;

	@Autowired
	private QueriesConfig queriesConfig;
	
	public PersistenceConfig getPersistenceConfig() {
		return persistenceConfig;
	}

	public void setPersistenceConfig(PersistenceConfig persistenceConfig) {
		this.persistenceConfig = persistenceConfig;
	}

	public QueriesConfig getQueriesConfig() {
		return queriesConfig;
	}

	public void setQueriesConfig(QueriesConfig queriesConfig) {
		this.queriesConfig = queriesConfig;
	}
	
	public ToolkitSettingsSpring()
	{
		
	}
	
	@Override
	public HibernateSettings getHibernateSettingsData() {
		
		HibernateSettings hbsSettings = new HibernateSettings();
		hbsSettings.setHibernateDialect(this.getPersistenceConfig().getHIBERNATE_DIALECT());
		hbsSettings.setHibernateConnectionUrl(this.getPersistenceConfig().getDB_URL());
		hbsSettings.setHibernateConnectionDriverClass(this.getPersistenceConfig().getDB_DRIVER());
		hbsSettings.setHibernateConnectionUsername(this.getPersistenceConfig().getDB_USERNAME());
		hbsSettings.setHibernateConnectionPassword(this.getPersistenceConfig().getDB_PASSWORD());
		
		return hbsSettings;
	}
	
	@Override
	public SQLSettings getSQLSettingsData() {
		
		SQLSettings sqlSettings = new SQLSettings();
		sqlSettings.setSqlFile(this.getQueriesConfig().getSqlFile());
		sqlSettings.setLoadSettingsFromFS(this.getQueriesConfig().isLoadSettingsFromFS());
		
		return sqlSettings;
	}
}
