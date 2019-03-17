
package com.coalvalue.domain.pojo;

import java.net.URL;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
 * <p>This is the entry point for the UpdateFX framework.</p>
 * 
 * @author Michele Balistreri
 *
 */


public class UpdateFX {
	Logger logger = LoggerFactory.getLogger(UpdateFX.class);

	private URL updateXML;
	private int releaseID;
	private String version;
	private int licenseVersion;
	private URL css;



	/**
	 * Creates and initializes an instance of the UpdateFX class.
	 *
	 * @param updateXML      the URL to the XML file describing the updates
	 * @param releaseID      the ID of the current release
	 * @param version        the human readable version string
	 * @param licenseVersion the version of the license
	 * @param css            the css theme
	 */

	public UpdateFX(URL updateXML, int releaseID, String version, int licenseVersion, URL css) {
		this.updateXML = updateXML;
		this.releaseID = releaseID;
		this.version = version;
		this.licenseVersion = licenseVersion;
		this.css = css;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public URL getUpdateXML() {
		return updateXML;
	}

	public void setUpdateXML(URL updateXML) {
		this.updateXML = updateXML;
	}

	public int getReleaseID() {
		return releaseID;
	}

	public void setReleaseID(int releaseID) {
		this.releaseID = releaseID;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getLicenseVersion() {
		return licenseVersion;
	}

	public void setLicenseVersion(int licenseVersion) {
		this.licenseVersion = licenseVersion;
	}

	public URL getCss() {
		return css;
	}

	public void setCss(URL css) {
		this.css = css;
	}
}