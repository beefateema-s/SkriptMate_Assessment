package com.scripted.configurations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.scripted.dataload.PropertyDriver;
import com.scripted.generic.FileUtils;
import com.scripted.pdf.CucumberPDF;
import com.scripted.reporting.AllureReport;
import com.scripted.reporting.ExtentUtils;
import com.scripted.selfhealing.HealingConfig;

public class SkriptmateConfigurations {

	static String log4j_Path = "src\\main\\resources\\logConf\\log4j.properties";
	static String extentProFile_Path = "src\\main\\resources\\extent.properties";
	static String skrimateConfig_Path = "SkriptmateConfigurations\\SkriptmateConfig.Properties";
	static String logPath = "SkriptmateLogs\\Skriptmate.log";
	private static String cdir = System.getProperty("user.dir");
	static Boolean shFlag;
	static Boolean impactFlg;

	public static Logger LOGGER = Logger.getLogger(SkriptmateConfigurations.class);

	public static void beforeSuite() {
		try {

			Map<String, String> configMap = readConf();
			configMap.forEach((k, v) -> {
				if (k.equalsIgnoreCase("Skriptmate.Self.Healing")) {
					if (v.equalsIgnoreCase("true"))
						shFlag = true;
					else
						shFlag = false;

				}
				if (k.equalsIgnoreCase("Skriptmate.Impact.Report")) {
					if (v.equalsIgnoreCase("true"))
						impactFlg = true;
					else
						impactFlg = false;

				}
				if (k.equalsIgnoreCase("Skriptmate.allure.clear") && v.equalsIgnoreCase("true")) {
					File dirPath = new File(FileUtils.getCurrentDir() + "\\" + "allure-results");
					FileUtils.deleteDirectory(dirPath);
					File ocrDirPath = new File(cdir + "\\OCROutput" + "\\temp");
					FileUtils.deleteDirectory(ocrDirPath);
				}
				if (k.equalsIgnoreCase("Skriptmate.extent.report") && v.equalsIgnoreCase("true")) {
					try {
						//ExtentUtils.setSparkExtentPropValue(extentProFile_Path);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (k.equalsIgnoreCase("Skriptmate.Jira.Xray.ExtractScenatio") && v.equalsIgnoreCase("true")) {
					/*
					 * try { //XrayAPIClient client = new XrayAPIClient(); PropertyDriver propDriver
					 * = new PropertyDriver();
					 * propDriver.setPropFilePath("src/main/resources/JiraXray/jiraXray.properties")
					 * ; String id = PropertyDriver.readProp("TC_id"); String[] arrOfId =
					 * id.split(",");
					 * 
					 * for (String a : arrOfId) XrayAPIClient.getScenario(a);
					 * 
					 * } catch (Exception e) {
					 * 
					 * e.printStackTrace(); }
					 */
				}
				// com.scripted.selfhealing.smHealer.setTime()

			});
			HealingConfig.setSlfHelngProp(shFlag,impactFlg );


		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void afterSuite() {
		
		
		Map<String, String> configMap = readConf();
		if(configMap.get("Skriptmate.Self.Healing").equalsIgnoreCase("true")) {
			try {
		HealingConfig.afterSuite();
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		// BrowserDriver.closeBrowser();
		}

			if (configMap.get("Skriptmate.allure").equalsIgnoreCase("true"))
				try {
					AllureReport.customizeReport();

				} catch (Exception e) {
					e.printStackTrace();
				}
			if (configMap.get("Skriptmate.extent.report").equalsIgnoreCase("true")) {
				try {
					ExtentUtils.setSparkExtentPropValue(extentProFile_Path);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if (configMap.get("Skriptmate.PDF.report").equalsIgnoreCase("true")) {
				try {

					CucumberPDF.ExportJsondataPdf("target/cucumber.json");
					

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		// }

		// }
		// catch(Exception e)
		// {
		// System.out.println("Exception in aftersuite: "e);
		// }
		// ReportGeneration.reportWriter();
		/*
		 * try { configMap.forEach((k, v) -> { if
		 * (k.equalsIgnoreCase("Skriptmate.allure") && v.equalsIgnoreCase("true")) try {
		 * AllureReport.customizeReport(); } catch (Exception e) { e.printStackTrace();
		 * } if (k.equalsIgnoreCase("Skriptmate.extent") && v.equalsIgnoreCase("true"))
		 * { try { ExtentUtils.copyExtentLogo(); } catch (Exception e) {
		 * e.printStackTrace(); } }
		 * 
		 * if (k.equalsIgnoreCase("Skriptmate.Jira.Zephyr.ExecutionStatusUpdate") &&
		 * v.equalsIgnoreCase("true")) {
		 * JiraUtils.uploadResultsFromCucumberJson("target/cucumber.json"); } if
		 * (k.equalsIgnoreCase("Skriptmate.TestRail.ExecutionStatusUpdate") &&
		 * v.equalsIgnoreCase("true")) {
		 * TestRailUtils.uploadResultsFromCucumberJson("target/cucumber.json"); } if
		 * (k.equalsIgnoreCase("Skriptmate.Jira.Xray.ExecutionStatusUpdate") &&
		 * v.equalsIgnoreCase("true")) {
		 * XrayUtils.uploadResultsFromCucumberJson("target/cucumber.json"); } if
		 * (k.equalsIgnoreCase("Skriptmate.Report.enableMail") &&
		 * v.equalsIgnoreCase("true")) { try {
		 * //Sendmail.send("src/main/resources/Email/mail.properties",
		 * "target/cucumber.json"); } catch (IOException e) { e.printStackTrace(); } }
		 * 
		 * }); // Will kill all the open driver after the suite execution if any
		 * killDrivers(); } catch (Exception e) {
		 * 
		 * }
		 */
	}

	public static Map<String, String> readConf() {
		PropertyDriver propertyDriver = new PropertyDriver();
		propertyDriver.setPropFilePath(skrimateConfig_Path);
		return propertyDriver.readProp();
	}

	public static void setLog4jPropValue(String log4jPropFilePath) throws Exception {
		PropertiesConfiguration conf = new PropertiesConfiguration(log4jPropFilePath);
		conf.setProperty("log4j.appender.file.File", cdir + "\\" + logPath);
		conf.save();
	}

	public static void setlog4jConfig(String log4jPropFileLoc) throws Exception {
		String log4jPropFilePath = cdir + "\\" + log4jPropFileLoc;
		setLog4jPropValue(log4jPropFilePath);
		InputStream log4j = new FileInputStream(log4jPropFilePath);
		PropertyConfigurator.configure(log4j);
	}

	public static void killDrivers() {
		Process process;
		try {
			String line;
			String pidInfo = "";
			process = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = input.readLine()) != null) {
				pidInfo += line;
			}
			input.close();
			if (pidInfo.contains("ChromeDriver.exe")) {
				Runtime.getRuntime().exec("taskkill /f /im ChromeDriver.exe");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
