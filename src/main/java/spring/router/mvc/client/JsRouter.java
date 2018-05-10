package spring.router.mvc.client;

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
import spring.router.mvc.RouteHelper;

public class JsRouter {

	public static interface JsRoutesJavascriptWriter {
		public void writeRoutes(Map<String, Route> routes);

	}

	public static interface JsRoutesFileWriter {
		public void writeRoutesFile();
	}

	public static abstract class JsRoutesJavascriptNamePatternWriterBase implements JsRoutesJavascriptWriter {

		private static final Logger logger = LoggerFactory.getLogger(JsRoutesJavascriptNamePatternWriterBase.class);

		private String outputDirectory;
		private static final String ROUTE_FILE_NAME = "routes.js";

		public JsRoutesJavascriptNamePatternWriterBase(String outputDirectory) {
			this.outputDirectory = outputDirectory;
		}

		@Override
		public void writeRoutes(Map<String, Route> routes) {
			File outputDir = new File(outputDirectory);

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
				writer = new PrintWriter(new FileWriter(new File(outputDir, ROUTE_FILE_NAME)));
				writeFileStart(writer);
				writeBeforeRoutes(writer);
				writeRoutes(writer, routes);
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

		protected void writeRoutes(PrintWriter writer, Map<String, Route> routes) {
			writer.println("\tvar routes = {");

			Set<String> keys = routes.keySet();

			int i = 0;
			for (String key : keys) {
				writer.print("\t\t");
				writer.print("\"");
				writer.print(key);
				writer.print("\"");
				writer.print(":");
				writer.print("\"");
				writer.print(routes.get(key).getPath());

				if (i == keys.size() - 1) {
					writer.println("\"");
				} else {
					writer.println("\",");
				}

				i++;
			}

			writer.println("\t};");
		}

		protected void writeAfterRoutes(PrintWriter writer) {

		}

		protected void writeFileEnd(PrintWriter writer) {
			writer.println("})();");
		}

	}

	public static class JsRoutesRequireJsNamePatternWriter extends JsRoutesJavascriptNamePatternWriterBase {

		public JsRoutesRequireJsNamePatternWriter(String outputDirectory) {
			super(outputDirectory);
		}

		@Override
		protected void writeAfterRoutes(PrintWriter writer) {
			writer.println("\tmodule.exports = routes;");
		}

		public static JsRoutesJavascriptWriter createWorkingDirectoryRelativeRoutesFile(String relativeDirPath) {

			if (!relativeDirPath.startsWith("//")) {
				return new JsRoutesRequireJsNamePatternWriter(System.getProperty("user.dir") + "/" + relativeDirPath);
			}

			return new JsRoutesRequireJsNamePatternWriter(System.getProperty("user.dir") + relativeDirPath);
		}
	}

	public static class JsRoutesFileWriterImpl implements JsRoutesFileWriter {

		private boolean writeRoutes;
		private JsRoutesJavascriptWriter jsWriter;

		public JsRoutesFileWriterImpl(boolean writeRoutes, JsRoutesJavascriptWriter jsWriter) {
			this.writeRoutes = writeRoutes;
			this.jsWriter = jsWriter;
		}

		@Override
		public void writeRoutesFile() {
			if (!writeRoutes) {
				return;
			}

			Map<String, Route> routes = RouteHelper.getRouteMappingsByName();
		
			jsWriter.writeRoutes(RouteHelper.getRouteMappingsByName());

		}
	}

}
