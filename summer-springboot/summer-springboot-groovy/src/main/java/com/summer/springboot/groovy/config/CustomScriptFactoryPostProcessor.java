package com.summer.springboot.groovy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.scripting.support.ScriptFactoryPostProcessor;
import org.springframework.scripting.support.StaticScriptSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class CustomScriptFactoryPostProcessor extends ScriptFactoryPostProcessor {
	public static final String DATABASE_SCRIPT_PREFIX = "database:";
	@Autowired
	private DataSource dataSource;

	@Override
	protected ScriptSource convertToScriptSource(String beanName, String scriptSourceLocator, ResourceLoader resourceLoader) {
		if (scriptSourceLocator.startsWith(INLINE_SCRIPT_PREFIX)) {
			return new StaticScriptSource(
					scriptSourceLocator.substring(INLINE_SCRIPT_PREFIX.length()), beanName);
		} else if (scriptSourceLocator.startsWith(DATABASE_SCRIPT_PREFIX)) {
			return new DatabaseScriptSource(
					scriptSourceLocator.substring(DATABASE_SCRIPT_PREFIX.length()),
					dataSource);
		} else {
			return new ResourceScriptSource(
					resourceLoader.getResource(scriptSourceLocator));
		}
	}

}