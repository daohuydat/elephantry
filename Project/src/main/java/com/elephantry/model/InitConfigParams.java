package com.elephantry.model;

public class InitConfigParams {
	private String hadoopIpAddress;
	private String hdfsPort;
	private String jobTrackerPort;
	private String hdfsJarLibsDir;
	private int iJobTrackerPort;
	private boolean startQueueAtStartup;
	private String hadoopUser ;

	
	public InitConfigParams() {
		
	}

	public String getHadoopIpAddress() {
		return hadoopIpAddress;
	}

	public void setHadoopIpAddress(String hadoopIpAddress) {
		this.hadoopIpAddress = hadoopIpAddress;
	}

	public String getHdfsPort() {
		return hdfsPort;
	}

	public void setHdfsPort(String hdfsPort) {
		this.hdfsPort = hdfsPort;
	}

	public String getJobTrackerPort() {
		return jobTrackerPort;
	}

	public void setJobTrackerPort(String jobTrackerPort) {
		this.jobTrackerPort = jobTrackerPort;
		try {
			this.iJobTrackerPort = Integer.parseInt(jobTrackerPort);
		} catch (Exception e) {
			this.iJobTrackerPort = 54311;
		}
	}

	public String getHdfsJarLibsDir() {
		return hdfsJarLibsDir;
	}

	public void setHdfsJarLibsDir(String hdfsJarLibsDir) {
		this.hdfsJarLibsDir = hdfsJarLibsDir;
	}

	public int getiJobTrackerPort() {
		return iJobTrackerPort;
	}

	public void setStartQueueAtStartup(boolean startQueueAtStartup) {
		this.startQueueAtStartup = startQueueAtStartup;
	}

	public boolean isStartQueueAtStartup() {
		return startQueueAtStartup;
	}

	public String getHadoopUser() {
		return hadoopUser;
	}

	public void setHadoopUser(String hadoopUser) {
		this.hadoopUser = hadoopUser;
	}

}
