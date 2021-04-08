/**
 * 
 */
package it.laziocrea.jemoloapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author mtassinari
 *
 */
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
	
	private String uploadDir;

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
}
