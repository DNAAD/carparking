package com.coalvalue.update;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import com.coalvalue.configuration.DemoProperty;
import com.coalvalue.configuration.Docker;
import com.coalvalue.domain.pojo.UpdateFX;
import com.coalvalue.update.model.Application;
import com.coalvalue.update.model.Release;
import com.github.dockerjava.api.DockerClient;
import javafx.concurrent.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


@Component
public class UpdateService /*extends Service<Release> */{
	Logger logger = LoggerFactory.getLogger(UpdateService.class);
	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private DemoProperty demoProperty;

	@Autowired
	private Docker docker;
	private Application application;
	private int releaseID;
	private int licenseVersion;

/*
	public UpdateService(Application application, int releaseID, int licenseVersion) {
		this.application = application;
		this.releaseID = releaseID;
		this.licenseVersion = licenseVersion;
	}
*/


	private static UpdateFX getPropertiesForApp(Class<?> applicationMain) throws IOException {
		Properties properties = new Properties();
		properties.load(applicationMain.getResourceAsStream("app-info.properties"));
		System.out.println("update release -----------------------{}" + properties.toString());


		UpdateFX updateFX = new UpdateFX(new URL(properties.getProperty("app.updatefx.url")),
				Integer.parseInt(properties.getProperty("app.release")),
				properties.getProperty("app.version"),
				Integer.parseInt(properties.getProperty("app.licenseVersion")),
				new URL("http://localhost:8002/update/AppUpdateFX.xml")
		);

		return updateFX;
	}

	@Scheduled(fixedDelay = 1000*60*6,   initialDelay=1000*60*4)



	public void update() throws IOException {

		RestTemplate restTemplate = new RestTemplate();

		UpdateFX localApplication = getPropertiesForApp(UpdateService.class);
		String fooResourceUrl = localApplication.getUpdateXML().toString();


		Application applicationRemote = null;
		try {
			applicationRemote
					= restTemplate.getForObject(fooResourceUrl , Application.class);

			System.out.println(applicationRemote.toString());
			applicationRemote.getReleases().stream().forEach(e->System.out.print(e.toString()));
			//System.out.println(applicationRemote.getReleases().toString());
		} catch (HttpStatusCodeException exception) {
			int statusCode = exception.getStatusCode().value();
			//	logger.error("获取更新，网络错误" + exception.getCause().toString());

		}

		//	Release release = applicationRemote.getReleases().get(0);


		//	System.out.println("----------- release" + release.getReleaseDate() + release.getLicenseVersion());

		//TODO 判断是否更新，（决定是否下载 ，然后更新）






		DockerClient dockerClient = docker.getDockerClient();
		docker.isValid(dockerClient);


		//docker.restart(); // TODO 重新启动docker


	}


	public static void main(String args[]) throws IOException {

		RestTemplate restTemplate = new RestTemplate();

		UpdateFX localApplication = getPropertiesForApp(UpdateService.class);
		String fooResourceUrl = localApplication.getUpdateXML().toString();


		Application applicationRemote = null;
		try {
			applicationRemote
					= restTemplate.getForObject(fooResourceUrl , Application.class);

			System.out.println(applicationRemote.toString());
			applicationRemote.getReleases().stream().forEach(e->System.out.print(e.toString()));
			//System.out.println(applicationRemote.getReleases().toString());
		} catch (HttpStatusCodeException exception) {
			int statusCode = exception.getStatusCode().value();
		//	logger.error("获取更新，网络错误" + exception.getCause().toString());

		}

	//	Release release = applicationRemote.getReleases().get(0);


		//	System.out.println("----------- release" + release.getReleaseDate() + release.getLicenseVersion());

		//TODO 判断是否更新，（决定是否下载 ，然后更新）


		//docker.all_together____(release);  //下载 docker


		//docker.restart(); // TODO 重新启动docker


	}


}