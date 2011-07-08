package de.uni_potsdam.hpi.wfapp2011.tests;

import java.io.*;
import java.util.ArrayList;
import org.junit.*;
import de.uni_potsdam.hpi.wfapp2011.server.FtpTransfer;


public class FtpTransferTest {
	
	private static FtpTransfer ftp = FtpTransfer.getInstance();
	private static String fileName;
	private static String filePath;
	private static String destFolder;
	private static File testFile;
	private static String fileName2;
	private static String savedFileName;
	
	@BeforeClass
	public static void setUp(){
		fileName = "testFile.txt";
		destFolder = "";
		filePath = "C:"+File.separatorChar;
		testFile = new File(filePath+fileName);
		savedFileName = "downloadedFile.txt";
		try {
		BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
		writer.write("lmaa roflmao");
		writer.close();
		} catch (IOException e){
			// Handle if you can!
		}
	}
	
	@Test
	public void testUpload() throws IOException{
		InputStream is = new FileInputStream(filePath+fileName);
		ArrayList<String> result = ftp.upload(is, fileName);
		is.close();
		fileName2 = result.get(1);
		destFolder = result.get(0);
	}
	
	@Test
	public void testDownload() throws IOException{
		InputStream is = ftp.download(destFolder, fileName2);
		OutputStream os = new FileOutputStream(new File(filePath+savedFileName));
		
		int read=0;
		byte[] bytes = new byte[1024];
 
		while((read = is.read(bytes))!= -1){
			os.write(bytes, 0, read);
		}
 
		is.close();
		os.flush();
		os.close();	
	}
	
	@AfterClass
	public static void tearDown(){
		testFile = new File(filePath+fileName);
		testFile.delete();
		testFile = new File(filePath+savedFileName);
		testFile.delete();
	}
}
