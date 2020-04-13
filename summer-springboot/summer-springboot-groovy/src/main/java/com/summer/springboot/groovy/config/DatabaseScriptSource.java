package com.summer.springboot.groovy.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scripting.ScriptSource;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Timestamp;


/**
 * @Author liuwei
 * @Description //TODO
 * @Date 1$ 1$
 **/
public class DatabaseScriptSource implements ScriptSource {

	private final String scriptName;
	private final JdbcTemplate jdbcTemplate;
	private Timestamp lastKnownUpdate;

	private final Object lastModifiedMonitor = new Object();

	public DatabaseScriptSource(String scriptName, DataSource dataSource) {
		this.scriptName = scriptName;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public String getScriptAsString() throws IOException {
		synchronized (this.lastModifiedMonitor) {
			this.lastKnownUpdate = retrieveLastModifiedTime();
		}
		return (String) jdbcTemplate.queryForObject(
				"select script_source from groovy_scripts where script_name = ?",
				new Object[]{this.scriptName}, String.class);
	}

	@Override
	public boolean isModified() {
		synchronized (this.lastModifiedMonitor) {
			Timestamp lastUpdated = retrieveLastModifiedTime();
			return lastUpdated.after(this.lastKnownUpdate);
		}
	}

	@Override
	public String suggestedClassName() {
		return StringUtils.stripFilenameExtension(this.scriptName);
	}

	private Timestamp retrieveLastModifiedTime() {
		return (Timestamp) this.jdbcTemplate.queryForObject(
				"select last_updated from groovy_scripts where script_name = ?",
				new Object[]{this.scriptName}, Timestamp.class);
	}
}
