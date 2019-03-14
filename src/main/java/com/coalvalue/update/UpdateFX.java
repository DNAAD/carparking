/*
package com.coalvalue.update;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Properties;


import com.coalvalue.configuration.DemoProperty;
import com.coalvalue.update.model.Application;
import com.coalvalue.update.model.Release;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.bind.UnmarshalException;

*/
/**
 * <p>This is the entry point for the UpdateFX framework.</p>
 * 
 * @author Michele Balistreri
 *
 *//*

@Component
public class UpdateFX {
	Logger logger = LoggerFactory.getLogger(UpdateFX.class);

	private URL updateXML;
	private int releaseID;
	private String version;
	private int licenseVersion;
	private URL css;

	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private DemoProperty demoProperty;



	*/
/**
	 * Creates and initializes an instance of the UpdateFX class.
	 * 
	 * @param updateXML the URL to the XML file describing the updates
	 * @param releaseID the ID of the current release
	 * @param version the human readable version string
	 * @param licenseVersion the version of the license
	 * @param css the css theme
	 *//*

	public void UpdateFX_(URL updateXML, int releaseID, String version, int licenseVersion, URL css) {
		this.updateXML = updateXML;
		this.releaseID = releaseID;
		this.version = version;
		this.licenseVersion = licenseVersion;
		this.css = css;
	}
	
	*/
/**
	 * Creates and initializes an instance of the UpdateFX class.
	 * 
	 * @param propertyFile the property file containing the options
	 * @param css the css theme
	 * @throws IOException malformed URL
	 *//*

	public void  UpdateFX__(Properties propertyFile, URL css) throws IOException {
*/
/*		this(new URL(propertyFile.getProperty("app.updatefx.url")),
				Integer.parseInt(propertyFile.getProperty("app.release")), 
				propertyFile.getProperty("app.version"), 
				Integer.parseInt(propertyFile.getProperty("app.licenseVersion")),
				css
		);*//*



		UpdateFX_(new URL(propertyFile.getProperty("app.updatefx.url")),
				Integer.parseInt(propertyFile.getProperty("app.release")),
				propertyFile.getProperty("app.version"),
				Integer.parseInt(propertyFile.getProperty("app.licenseVersion")),
				css
		);
	}
	
	*/
/**
	 * Creates and initializes an instance of the UpdateFX class.
	 * 
	 * @param applicationMain the main class of the application, where app-info.properties will be looked for
	 * @throws IOException error reading property or css file
	 *//*

	@PostConstruct
	public void UpdateFX_() throws IOException {


		//this(getPropertiesForApp(applicationMain), getCSSForApp(applicationMain));
		//UpdateFX__(getPropertiesForApp(), null);

		UpdateFX_(new URL(demoProperty.getUpdateXML()),
				Integer.parseInt(demoProperty.getReleaseID()),
				demoProperty.getVersion(),
				Integer.parseInt(demoProperty.getLicenseVersion()),
				css
		);


	}

	public void UpdateFX(Class<?> applicationMain) throws IOException {


		//this(getPropertiesForApp(applicationMain), getCSSForApp(applicationMain));
		//UpdateFX__(getPropertiesForApp(applicationMain), getCSSForApp(applicationMain));


	}
	
*/
/*	private static Properties getPropertiesForApp(Class<?> applicationMain) throws IOException {
		Properties properties = new Properties();
		properties.load(applicationMain.getResourceAsStream("app-info.properties"));
		System.out.println("update release -----------------------{}"+properties.toString());
		return properties;
	}*//*


	private Properties getPropertiesForApp() throws IOException {

		Properties properties = new Properties();
		Resource res = resourceLoader.getResource("classpath:app-info.properties");
		properties.load(res.getInputStream());
		System.out.println("update release -----------------------{}"+properties.toString());
		return properties;
	}


	private static URL getCSSForApp(Class<?> applicationMain) throws IOException {
		return applicationMain.getResource("theme.css");
	}
	
	public URL getUpdateXML() {
		return updateXML;
	}

	public int getReleaseID() {
		return releaseID;
	}
	
	public int getLicenseVersion() {
		return licenseVersion;
	}

	public String getVersion() {
		return version;
	}
	Application application = null;
	*/
/**
	 * Checks for updates and prompts the user to eventually install them.
	 *//*

	String status = null;
	public void checkUpdates() {


		XMLRetrieverService xmlRetriever = new XMLRetrieverService(getUpdateXML());
		try {
			application = xmlRetriever.call();
		} catch (UnmarshalException e) {
			e.printStackTrace();
			status ="获取更新信息失败";
			logger.error("获取更新，网络错误"+e.getCause().toString());
			return;
		}
		catch (Exception e) {
			e.printStackTrace();
			status ="获取更新信息失败";
			logger.error("获取更新，网络错误");
			return;
		}
		Release release =null;//
		UpdateFinderService service = new UpdateFinderService(application, getReleaseID(), getLicenseVersion());
		try {
			release = service.call();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		System.out.println("----------- release" + release.getReleaseDate()+release.getLicenseVersion());

		//TODO 判断是否更新，（决定是否下载 ，然后更新）

*/
/*

		Path path = null;
		UpdateDownloadService downloadService = new UpdateDownloadService(release);
		try {
			path = downloadService.call();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
*//*

*/
/*

		System.out.println("----------- path" + path.toString());
		InstallerService installerService = new InstallerService(path); // TODO 这里带的 脚本是 ，解压，执行下载的脚本。

		// 这里的脚本，应该是一句话吧。  curl -L dddd -o 之类的。  归一化 ，一个 脚本了。


		//TODO 真正的的 是 一个 脚本。  脚本的 作用是 设置  运行环境， 下载 新程序，关闭原有程序， 启动新的程序。 这样各 脚本啊啊。
		try {
			installerService.call();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
*//*




		*/
/*xmlRetriever.valueProperty().addListener((observable, oldValue, application) -> {
			UpdateFinderService service = new UpdateFinderService(application, getReleaseID(), getLicenseVersion());
			service.valueProperty().addListener((obs, oldVal, release) -> {
			//	Platform.runLater(() -> UpdateDialogController.showUpdateDialog(release, getReleaseID(), getVersion(), getLicenseVersion(), css));
			});
			
			service.start();
		});
	//TODO shell 聪明的判断 ，自己来自那里， 如果来自 父亲的华， 报告资金下载的情况 给父亲，
	//

	//TODO 简单的， 布置环境，下载，然后启动，挂壁原有程序， 重新启动。就可以了。 收尾环境（布置开机启动，设置网络环境）
		xmlRetriever.start();*//*

		handleEXEInstallation();
	}
	private void handleEXEInstallation() {
		try {
			new ProcessBuilder("curl", "-L", "-o", "install.sh","http://ddfsdf","|","bash","install.sh").start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
}*/
