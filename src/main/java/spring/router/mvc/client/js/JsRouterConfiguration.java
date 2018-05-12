package spring.router.mvc.client.js;

import spring.router.mvc.client.js.JsRoutesFileJavascriptWriters.JsRoutesFileJavascriptWriter;
import spring.router.mvc.client.js.JsRoutesFileJavascriptWriters.JsRoutesFileRequireJsNamePatternWriter;

public class JsRouterConfiguration {

	private String outputDirectory;
	private String routeFileBaseName = "routes";
	private boolean writeRoutes = false;
	private JsRoutesFileJavascriptWriter javacriptFileWriter = new JsRoutesFileRequireJsNamePatternWriter();
	
	
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

	public JsRoutesFileJavascriptWriter getJavacriptFileWriter() {
		return javacriptFileWriter;
	}

	public void setJavacriptFileWriter(JsRoutesFileJavascriptWriter javacriptFileWriter) {
		this.javacriptFileWriter = javacriptFileWriter;
	}
}
