/*
package com.coalvalue.update;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Scope("singleton")
public class ReloadableProperties {
	private final Logger logger = LoggerFactory.getLogger(ReloadableProperties.class);
	private static final int RELOAD_DELAY = 10;
	private PropertiesConfiguration configuration;
	@Value("${spring.config.location}")
	private String configLocation;

	@PostConstruct
	private void init() throws ConfigurationException {
		configuration = new PropertiesConfiguration(configLocation + "reloadable.properties");
		FileChangedReloadingStrategy fileChangedReloadingStrategy = new FileChangedReloadingStrategy();
		fileChangedReloadingStrategy.setRefreshDelay(RELOAD_DELAY * 1000);
		configuration.setReloadingStrategy(fileChangedReloadingStrategy);
		logger.debug("Loaded reloadable properties");
	}

	public String getProperty(String key) {
		return (String) configuration.getProperty(key);
	}

	public void setProperty(String key, Object value) {
		configuration.setProperty(key, value);
	}

	public void save() {
		try {
			configuration.save();
		} catch (ConfigurationException e) {
			logger.error("Unable to save properties", e);
		}
	}
}*/
