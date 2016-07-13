package com.anke.ice;

import javax.ws.rs.ApplicationPath;

import org.apache.log4j.Logger;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;

import com.anke.ice.dao.DBHelper;
import com.anke.ice.util.LoggerUtil;

@ApplicationPath("/api")
public class APIApplication extends ResourceConfig {
	private static final Logger logger = LoggerUtil.getInstance(APIApplication.class);

	public APIApplication() {
		packages("com.anke.ice.service");
		register(JacksonJsonProvider.class);
		DBHelper.init();

		logger.debug("the web service is running!");
	}

}
