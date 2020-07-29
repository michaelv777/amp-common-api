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
public class QueriesConfig
{
	@SuppressWarnings("unused")
	private final static Logger LOG = 
			LoggerFactory.getLogger(QueriesConfig.class);

	@Autowired
	ApplicationContext applicationContext;
	
	@Value("${amp.datasource.sqlFile}")
    private String sqlFile;
	
	@Value("${amp.datasource.isLoadSettingsFromFS:false}")
    private boolean isLoadSettingsFromFS;
	
	public String getSqlFile() {
		return sqlFile;
	}

	public void setSqlFile(String sqlFile) {
		this.sqlFile = sqlFile;
	}

	public boolean isLoadSettingsFromFS() {
		return isLoadSettingsFromFS;
	}

	public void setLoadSettingsFromFS(boolean isLoadSettingsFromFS) {
		this.isLoadSettingsFromFS = isLoadSettingsFromFS;
	}

}
