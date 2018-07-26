/**
 * @author datdh
 * */
package com.elephantry.background;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.elephantry.mahout.hadoop.util.HDHelper;
import com.elephantry.model.InitConfigParams;
import com.elephantry.util.InitConstant;

/**
 * Application Lifecycle Listener implementation class PrepareHDEnv
 *
 */
public class PrepareHDEnv implements ServletContextListener {

	public PrepareHDEnv() {

	}

	public void contextDestroyed(ServletContextEvent sce) {

	}

	public void contextInitialized(ServletContextEvent sce) {
		InitConfigParams configParams = HDHelper.getInitConfigParams();
		InitConstant.webServerRootPath = sce.getServletContext().getRealPath("");
		String uploadedDir = InitConstant.webServerRootPath + InitConstant.UPLOADED_DIR_PATH;
		String resultDir = InitConstant.webServerRootPath + InitConstant.RESULT_DIR_PATH;
		InitConstant.SENDER_EMAIL = sce.getServletContext().getInitParameter("senderEmail");
		InitConstant.SENDER_PASSWORD = sce.getServletContext().getInitParameter("senderPassword");
		InitConstant.MAIL_HOST = sce.getServletContext().getInitParameter("mailHost");
		InitConstant.MAIL_PORT = sce.getServletContext().getInitParameter("mailPort");

		try {
			File dir = new File(uploadedDir);
			if (!dir.exists()) {
				System.out.println("Make uploaded dir status: " + dir.mkdirs());
			}
			dir = new File(resultDir);
			if (!dir.exists()) {
				System.out.println("Make result dir status: " + dir.mkdirs());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (configParams.isStartQueueAtStartup()) {
			HDHelper.loadLibrary2HDFS(sce.getServletContext(), configParams);
		}
	}

	
}
