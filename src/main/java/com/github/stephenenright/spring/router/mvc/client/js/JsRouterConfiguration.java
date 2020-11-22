package com.github.stephenenright.spring.router.mvc.client.js;

public class JsRouterConfiguration {

	private String outputDirectory;
	private String routeFileBaseName = "routes";
	private boolean writeRoutes = false;
	private JsRoutesFileJavascriptWriters.JsRoutesFileJavascriptWriter javacriptFileWriter = new JsRoutesFileJavascriptWriters.JsRoutesFileRequireJsNamePatternWriter();
	
	
	public String getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public String getRouteFileBaseName() {
		return routeFileBaseName;
	}

	public void setRouteFileBaseName(String routeFileBaseName) {
		this.routeFileBaseName = routeFileBaseName;
	}

	public boolean isWriteRoutes() {
		return writeRoutes;
	}

	public void setWriteRoutes(boolean writeRoutes) {
		this.writeRoutes = writeRoutes;
	}

	public JsRoutesFileJavascriptWriters.JsRoutesFileJavascriptWriter getJavacriptFileWriter() {
		return javacriptFileWriter;
	}

	public void setJavacriptFileWriter(JsRoutesFileJavascriptWriters.JsRoutesFileJavascriptWriter javacriptFileWriter) {
		this.javacriptFileWriter = javacriptFileWriter;
	}
}
