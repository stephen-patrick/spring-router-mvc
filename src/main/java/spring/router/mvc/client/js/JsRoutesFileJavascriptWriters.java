package spring.router.mvc.client.js;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spring.router.mvc.Route;

public class JsRoutesFileJavascriptWriters {

	/**
	 * Writes a javascript routes file
	 */
	public static interface JsRoutesFileJavascriptWriter {
		public void writeRoutes(File outputDirectory, String routesFileName, String configName, Map<String, Route> routes);
	}
	
	
	public static abstract class JsRoutesFileJavascriptNamePatternWriterBase implements JsRoutesFileJavascriptWriter {

		private static final Logger logger = LoggerFactory.getLogger(JsRoutesFileJavascriptNamePatternWriterBase.class);

		public JsRoutesFileJavascriptNamePatternWriterBase() {

		}
		
		@Override
		public void writeRoutes(File outputDir, String routesFileName,  
				String configName, Map<String, Route> routes) {

			logger.info("Using output directory: " + outputDir.getAbsolutePath());
			
			if (!outputDir.exists()) {
				try {

					FileUtils.forceMkdir(outputDir);
				} catch (IOException ioe) {
					throw new RuntimeException("Unable to create JS routes output directory", ioe);
				}
			}

			PrintWriter writer = null;
			try {
				writer = new PrintWriter(new FileWriter(new File(outputDir, routesFileName)));
				writeFileStart(writer);
				writeBeforeRoutes(writer);
				writeRoutes(writer, configName, routes);
				writeAfterRoutes(writer);
				writeFileEnd(writer);
			} catch (Exception e) {
				throw new RuntimeException("Unable to write routes.js file", e);
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (Exception e) {
						logger.warn("Unable to close writer", e);
					}
				}
			}

		}

		protected void writeFileStart(PrintWriter writer) {
			writer.println("(function() {");
		}

		protected void writeBeforeRoutes(PrintWriter writer) {

		}

		protected void writeRoutes(PrintWriter writer,String configName, Map<String, Route> routes) {
			writer.println("\tvar routesConfig = {");	
			writer.println("\t\tname: '" + configName + "',");	
			writer.println("\t\troutes: {");

			Set<String> keys = routes.keySet();

			int i = 0;
			for (String key : keys) {
				writer.print("\t\t\t");
				writer.print("'");
				writer.print(key);
				writer.print("'");
				writer.print(":");
				writer.print("'");
				writer.print(routes.get(key).getPath());

				if (i == keys.size() - 1) {
					writer.println("'");
				} else {
					writer.println("',");
				}

				i++;
			}

			writer.println("\t\t}");
			writer.println("\t};");
		}

		protected void writeAfterRoutes(PrintWriter writer) {

		}

		protected void writeFileEnd(PrintWriter writer) {
			writer.println("})();");
		}

	}

	public static class JsRoutesFileRequireJsNamePatternWriter extends JsRoutesFileJavascriptNamePatternWriterBase {

		@Override
		protected void writeAfterRoutes(PrintWriter writer) {
			writer.println("\tmodule.exports = routesConfig;");
		}
	}
}
