package com.coalvalue.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
//@ConfigurationProperties(prefix = "example")
//@PropertySource(value ={"file:${user.dir}/config/custom.properties","file:${user.dir}/config/custom_prison.properties"}, ignoreResourceNotFound = true)
@PropertySource(value ={"classpath:BACK/app.properties"}/*, ignoreResourceNotFound = true*/,encoding="utf-8")


public class DemoProperty {

	@Value("${app.updatefx.url}")
	private String updateXML;
	@Value("${app.release}")
	private String releaseID;
	@Value("${app.version}")
	private String version;
	@Value("${app.licenseVersion}")


	private String licenseVersion;
	@Value("${app.version}")

//	private String css;

	public String getUpdateXML() {
		return updateXML;
	}

	public void setUpdateXML(String updateXML) {
		this.updateXML = updateXML;
	}

	public String getReleaseID() {
		return releaseID;
	}

	public void setReleaseID(String releaseID) {
		this.releaseID = releaseID;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLicenseVersion() {
		return licenseVersion;
	}

	public void setLicenseVersion(String licenseVersion) {
		this.licenseVersion = licenseVersion;
	}
}
