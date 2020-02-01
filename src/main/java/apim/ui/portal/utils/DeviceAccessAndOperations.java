package apim.ui.portal.utils;

import java.io.File;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeviceAccessAndOperations {

	private static String OS = null;

	public static void deleteFileIfExists(String filePath, String fileName) {
		File[] listOfFiles = new File(filePath).listFiles();
		for (File downloadedFile : listOfFiles) {
			if (downloadedFile.isFile()) {
				if (downloadedFile.getName().contains(fileName)) {
					downloadedFile.delete();
				}
			}
		}
	}

	public static boolean killProc(String imageName) {
		if (isWindows()) {
			String task = "taskkill /f /im " + imageName;
			return execCmd(task);
		} else {
			// TODO [RL]: Implement kill firefox on MACOS.
			log.warn("Killing process on '" + getOsName() + "' OS has not implemented yet !!!");
			return false;
		}
	}

	public static String getOsName() {
		if (OS == null) {
			OS = System.getProperty("os.name");
		}
		return OS;
	}

	public static boolean isWindows() {
		return getOsName().toUpperCase().contains("WIN");
	}

	public static boolean isUnix() {
		return getOsName().toUpperCase().contains("UNIX");
	}

	public static boolean execCmd(String command) {
		try {
			Process process = Runtime.getRuntime().exec(command);
			int processResult = process.waitFor();

			log.info("Command '" + command + "' was executed.");
			return processResult == 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("Command '" + command + "' couldn't been executed.");
		return false;
	}
}
