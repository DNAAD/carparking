/*
package com.coalvalue.update;

import java.net.URL;

import com.coalvalue.update.model.Application;
import com.coalvalue.update.model.Release;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.coalvalue.update.model.Application;
import com.coalvalue.update.model.Release;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLRetrieverService extends Service<Application> {
	Logger logger = LoggerFactory.getLogger(UpdateFX.class);
	private URL xmlURL;
	
	public XMLRetrieverService(URL xmlURL) {
		this.xmlURL = xmlURL;
	}

	@Override
	protected Task<Application> createTask() {
		return new Task<Application>() {

			@Override
			protected Application call() throws Exception {
				JAXBContext ctx = JAXBContext.newInstance(Application.class);
				Unmarshaller um = ctx.createUnmarshaller();
				System.out.println("update release -----------------------{}"+xmlURL.toString());
				Application app = (Application) um.unmarshal(xmlURL);
				
				for (Release release : app.getReleases()) {
					release.setApplication(app);
				}

				System.out.println("update release -----------------------{}");
				return app;
			}
		};
	}

	protected Application call() throws JAXBException {
		System.out.println("获取更新信息{}"+xmlURL.toString());

		logger.debug("获取更新信息"+xmlURL);
		JAXBContext ctx = JAXBContext.newInstance(Application.class);
		Unmarshaller um = ctx.createUnmarshaller();
		Application app = (Application) um.unmarshal(xmlURL);

		for (Release release : app.getReleases()) {
			release.setApplication(app);
		}


		return app;
	}

}*/
