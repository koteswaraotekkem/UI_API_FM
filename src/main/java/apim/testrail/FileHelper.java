package apim.testrail;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.testng.xml.XmlSuite;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @author kxue
 */
@Slf4j
public class FileHelper {

    public static String readFileAsText(@NonNull String fname) {
        Path fpath = Paths.get(fname);
        log.info("Read file as Text: " + fpath.toAbsolutePath());

        StringBuilder fileData = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fpath.toAbsolutePath().toString()));
            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
            }
            reader.close();
        } catch (Exception e) {
            log.error("fail to read file as text: " + e.getMessage());
            e.printStackTrace();
        }

        return fileData.toString();
    }

    /**
     * Read File As Bytes
     *
     * @param fname file name
     * @return byte[] content as bytes
     */
    public static byte[] readFileAsBytes(@NonNull String fname) {
        Path fpath = Paths.get(fname);
        log.info("Read file as Bytes: " + fpath.toAbsolutePath());
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(fpath);
        } catch (IOException e) {
            log.error("fail to read file as bytes " + fpath.toAbsolutePath());
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * Save File as Text
     *
     * @param fname file name
     * @param data  data as text
     */
    public static void saveFileAsText(@NonNull String fname, @NonNull String data) {
		/*
		 * Path fpath = Paths.get(fname); log.info("Save Text to file: " +
		 * fpath.toAbsolutePath()); try (BufferedWriter writer =
		 * Files.newBufferedWriter(fpath)) { writer.write(data); } catch (IOException e)
		 * { log.error("fail to save text to file " + fpath.toAbsolutePath());
		 * e.printStackTrace(); }
		 */}

    /**
     * Save File as Bytes
     *
     * @param fname file name
     * @param data  data as bytes
     */
    public static void saveFileAsBytes(@NonNull String fname, @NonNull byte[] data) {
        Path fpath = Paths.get(fname);
        log.info("Save Bytes to file: " + fpath.toAbsolutePath());
        try {
            Files.write(fpath, data);
        } catch (IOException e) {
            log.error("fail to save bytes to file " + fpath.toAbsolutePath());
            e.printStackTrace();
        }
    }

    /**
     * Delete file
     *
     * @param fname file name
     */
    public static void deleteFile(@NonNull String fname) {
        Path fpath = Paths.get(fname);
        log.info("Delete file: " + fpath.toAbsolutePath());
        try {
            Files.delete(fpath);
        } catch (IOException e) {
            log.error("fail to delete file " + fpath.toAbsolutePath());
            e.printStackTrace();
        }
    }

    public static String getEncodedAsPEM(String encoded, Map format) {
        //pem format
        String pem;
        if (format.size() == 0) {
            pem = String.join("\n", encoded.split("(?<=\\G.{" + 65 + "})"));
        } else {
            pem = format.get("begin") + "\n" + String.join("\n", encoded.split("(?<=\\G.{" + 65 + "})")) + format.get("end");
        }
        return pem;
    }

    /**
     * Creates xml file with data passed in mSuite
     *
     * @param mSuite xml suite
     * @param fname name file to be created
     */
	public static void createXmlFile(XmlSuite mSuite, String fname) {
		Path fpath = Paths.get(fname);
		BufferedWriter writer;
		try {
			writer = Files.newBufferedWriter(fpath);
			writer.write(mSuite.toXml());
		}
		catch (IOException e) {
			log.error("Fail to save data to file " + fpath.toAbsolutePath());
			e.printStackTrace();
		}
	}

    /**
     * Creates Json File from JSONArray
     *
     * @param testRunID ID of executed Test Run
     * @param offlineResults Results of test execution to be stored in json file
     */
    public static void createJsonFile(String testRunID, JSONArray offlineResults) {
        Path fpath = Paths.get(testRunID+"_TestCaseResults_Offline.json");
        try {
        BufferedWriter writer = Files.newBufferedWriter(fpath);
            writer.write(offlineResults.toJSONString());

        } catch (IOException e) {
            log.error("Fail to save results to file " + fpath.toAbsolutePath());
            e.printStackTrace();
        }
    }
}
