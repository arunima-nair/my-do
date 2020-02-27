package ae.dt.common.util;

import ae.dt.common.dto.PageDto;
import ae.dt.common.exception.BusinessException;
import ae.dt.deliveryorder.domain.Bol;
import ae.dt.deliveryorder.dto.FileDTO;
import lombok.extern.slf4j.Slf4j;


import org.apache.commons.lang.StringUtils;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import javax.net.ssl.*;
import java.io.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
public class Utilities {

	public static Pageable createPageRequest(PageDto pageDto) {
		return new PageRequest(pageDto.getPageNumber(), pageDto.getSize());
	}

	@Value("${doc.filePath}")
	private static String filePath;

	public static byte[] loadFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}
		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		if (offset < bytes.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}

		is.close();
		return bytes;
	}

	/*
	 * public static String convertFileToBase64(FileDto file) throws IOException{
	 * File dir = new File("D:\\temp" + File.separator); if (!dir.exists()) { } File
	 * doc = new File(dir.getAbsolutePath() + File.separator + file.getFileName());
	 * byte[] bytes = Utilities.loadFile(doc); // byte[] encoded =
	 * Base64.encodeBase64(bytes); // String encodedString = new String(encoded); //
	 * log.info(Base64.encodeBase64String(bytes)); return
	 * Base64.encodeBase64String(bytes); }
	 * 
	 * public static File saveBase64ToPath(FileDto file) { File doc = null; if (file
	 * != null && StringUtils.isNotEmpty(file.getFileContent())) { String
	 * fileContent = file.getFileContent(); String fileName = file.getFileName();
	 * int index = fileName.lastIndexOf('.'); String xtension =
	 * fileName.substring(index + 1); fileName =
	 * "File_"+Long.toString(System.currentTimeMillis()) + "." + xtension;
	 * 
	 * try { // File dir = new File("D:\\" + DateUtil.getDateVal(new Date()) +
	 * File.separator); File dir = new File("D:\\temp" + File.separator); if
	 * (!dir.exists()) { dir.mkdirs(); } doc = new File(dir.getAbsolutePath() +
	 * File.separator + fileName);
	 * 
	 * BufferedOutputStream bos = new BufferedOutputStream(new
	 * FileOutputStream(doc)); bos.write(Base64.decodeBase64(fileContent));
	 * bos.close(); } catch (Exception e) { e.printStackTrace(); } } return doc; }
	 */

	public static String reverseOctalPadded(Long id, int padLength) {
		return StringUtils.leftPad(Long.toOctalString(Long.valueOf(StringUtils.reverse(String.valueOf(id)))), padLength,
				'0');

	}

	public static void disableCertificateCheck() {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (GeneralSecurityException e) {
		}
		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}

	public static String zipExtract(String uploadDoc, String fileExtn) throws IOException {
		if (uploadDoc != null) {
			String filecontent = uploadDoc;
			String fileExtension = fileExtn;
			String filename = LocalDate.now() + "."+fileExtension;

			File dir = new File(fileExtension);
			if (!dir.exists()) {
				dir.mkdir();
				
			}
			dir.canExecute();
			dir.canRead();
			dir.canWrite();
			File doc = new File(dir.getAbsoluteFile() + File.separator + filename);

			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(doc));
			byte[] base64decodedBytes = Base64.getMimeDecoder().decode(filecontent);

			bos.write(base64decodedBytes);
			bos.close();
			return doc.getAbsolutePath();
		}
		return null;

	}

	public static String getFileExtn(byte[] fileContent) {
		TikaConfig config = TikaConfig.getDefaultConfig();
		MediaType mediaType = null;
		String extension = null;

		try {
			mediaType = config.getMimeRepository().detect(new ByteArrayInputStream(fileContent), new Metadata());
			MimeType mimeType = config.getMimeRepository().forName(mediaType.toString());
			extension = mimeType.getExtension().replace(".", "");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MimeTypeException e) {
			e.printStackTrace();
		}

		return extension;
	}

	public static byte[] decodeBase64(String content) {
		return Base64.getMimeDecoder().decode(content);

	}

	public static void deletefile(Path path) {
		try {
			Files.deleteIfExists(path);
		} catch (NoSuchFileException e) {
			//System.out.println("No such file/directory exists");
		} catch (DirectoryNotEmptyException e) {
			//System.out.println("Directory is not empty.");
		} catch (IOException e) {
			//System.out.println("Invalid permissions.");
		}

		//System.out.println("Deletion successful.");
	}
	   

public static String unZipIt(String zipFile, String outputFolder, String agentCode){

    byte[] buffer = new byte[1024];
	String OUTPUT_FOLDER="";
	String unzipFolder="";
	 String fileName=null;
    try{
   		
   	//create output directory is not exists
    	 OUTPUT_FOLDER=Paths.get(outputFolder).toAbsolutePath().toString();
   	File folder = new File(OUTPUT_FOLDER);
   	if(!folder.exists()){
   		folder.mkdir();
   	}
   	//get the zip file content
   	ZipInputStream zis = 
   		new ZipInputStream(new FileInputStream(zipFile));
   	//get the zipped file list entry
   	ZipEntry ze = zis.getNextEntry();
  	File subfolder = new File(OUTPUT_FOLDER+File.separator+ agentCode);
   	if(!subfolder.exists()){
   		subfolder.mkdir();
   	}
   
   	while(ze!=null){
   			
   	    fileName =  ze.getName();
   	    /*delete invoice if exist*/
   		deletefile(Paths.get(subfolder + File.separator + fileName));
          File newFile = new File(subfolder + File.separator + fileName);
          unzipFolder=newFile.getParent();
          log.info("file unzip : "+ newFile.getAbsolutePath());
               
           //create all non exists folders
           //else you will hit FileNotFoundException for compressed folder
           new File(newFile.getParent()).mkdirs();
             
           FileOutputStream fos = new FileOutputStream(newFile);             

           int len;
           while ((len = zis.read(buffer)) > 0) {
      		fos.write(buffer, 0, len);
           }
       		
           fos.close();   
           ze = zis.getNextEntry();
   	}
   	
       zis.closeEntry();
   	zis.close();
   		
   	//System.out.println("Done");
   	deletefile(Paths.get(zipFile));
   		
   }catch(IOException ex){
      ex.printStackTrace(); 
   }
	return unzipFolder;
  }

public static String putCSVtoFolder(Bol b, String uploadDoc,String path) throws IOException {
	if ( b!=null) {
		String filecontent = uploadDoc;
		String fileExtension =".csv";
		String filename = b.getBolNumber() + fileExtension;

		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
			
		}
	
		   
		File subfolder = new File(dir.getPath()+ File.separator + LocalDateTime.now().toString().replaceAll(":", ""));
		if (!subfolder.exists()) {
			subfolder.mkdir();
			
		}
		
		File doc = new File(subfolder.getAbsoluteFile() + File.separator + b.getBolGroupId()+"_"+b.getBolNumber()+fileExtension);

		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(doc));
		byte[] base64decodedBytes = Base64.getMimeDecoder().decode(filecontent);

		bos.write(base64decodedBytes);
		bos.close();
		return doc.getAbsolutePath();
	}
	return null;
}    
public static String saveDocumentstoPath(String fileContent,String fileName,String filePath,String bolNo) throws IOException {

	if(!fileContent.equalsIgnoreCase("amend")) {
	boolean isCreated=false;
		String filename = fileName;

		File dir = new File(filePath);
		if (!dir.exists()) {
			dir.mkdir();
			
		}
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh mm ss");
		String time = dateFormat.format(now);
		File subfolder = new File(dir.getPath()+File.separator+bolNo);

		if (!subfolder.exists()) {
			subfolder.mkdir();
			
		}
		File folderDateTime=new File(subfolder.getPath()+File.separator+ LocalDateTime.now().toString().replaceAll(":", ""));
		if (!folderDateTime.exists()) {
			 isCreated = folderDateTime.mkdir();
			
		}
		if(isCreated) {
		File doc = new File(folderDateTime.getAbsoluteFile()+ File.separator+ filename);
		

		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(doc));
		byte[] base64decodedBytes = Base64.getMimeDecoder().decode(fileContent);

		bos.write(base64decodedBytes);
		bos.close();
		return doc.getAbsolutePath();
		}
		return null;
	}
	return null;
}  
public static void sendEmail(JavaMailSender sender) throws Exception{
    MimeMessage message = sender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);
    
    
    helper.setText("How are you?");
    helper.setSubject("Hi");
    
    sender.send(message);
}
public static String putBOLtoFolder(String uploadDoc,String path,String agentCode, String fileName) throws IOException {
	
	boolean isCreated=false;


	File dir = new File(path);
	if (!dir.exists()) {
		dir.mkdir();
		
	}
	
	File subfolder = new File(dir.getPath()+ File.separator+ agentCode);

	if (!subfolder.exists()) {
		isCreated=subfolder.mkdir();
		
	}
	
	
	File doc = new File(subfolder.getAbsoluteFile()+ File.separator+fileName);
	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(doc));
	byte[] base64decodedBytes = Base64.getMimeDecoder().decode(uploadDoc);

	bos.write(base64decodedBytes);
	bos.close();
	
	return subfolder.getAbsolutePath();
}

	public static String saveInvoicePDFtoPath(String uploadDoc, String fileName, String invoicefilepath,
			String agentCode, String fileExtn, String invoiceDelimitter, String uploadStatus) throws IOException {
		boolean isCreated = false;
		String filename = fileName;
		Boolean validFile = false;
		/* FILE NAME VALIDATION OF INVOICE PDF */
		if (uploadStatus.equalsIgnoreCase("fromInvoiceUpload")) {
			if(fileExtn.equalsIgnoreCase("pdf")) {
			String msg = validateInvoiceFileName(fileExtn, invoiceDelimitter, filename);
			if (msg.equalsIgnoreCase("valid"))
				validFile = true;

		}
		}else {
			validFile = true;
		}
	
		if (validFile || fileExtn.equalsIgnoreCase("zip")) {
			File dir = new File(invoicefilepath);
			if (!dir.exists()) {
				dir.mkdir();

			}

	
	File subfolder = new File(dir.getPath()+File.separator+agentCode);

	if (!subfolder.exists()) {
		subfolder.mkdir();
		
	}
	File folderDateTime=new File(subfolder.getPath()+File.separator);
	if (!folderDateTime.exists()) {
		 isCreated = folderDateTime.mkdir();
		
	}

	File doc = new File(folderDateTime.getAbsoluteFile()+ File.separator+ filename);
	

	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(doc));
	byte[] base64decodedBytes = Base64.getMimeDecoder().decode(uploadDoc);

			bos.write(base64decodedBytes);
			bos.close();
			/* FILE NAME VALIDATION OF INVOICE ZIP */
			if(fileExtn.equalsIgnoreCase("zip")) {
			String msg=invoiceZipValidation(doc.getAbsolutePath(), invoicefilepath, agentCode,invoiceDelimitter);
			if(msg.equalsIgnoreCase("valid"))
				return doc.getAbsolutePath();
			else
				throw new BusinessException(ErrorCodes.INVALID_INVOICE_FILE_NAME_FORMAT+": Invoice file name should be Bol number"+invoiceDelimitter+"Invoice number.");
			}
			
			return doc.getAbsolutePath();
		}
		return null;

	}

	private static String invoiceZipValidation(String zipFile, String invoicefilepath, String agentCode, String invoiceDelimitter) {
		String fileName = null;
		try {

			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();
			
			while (ze != null) {

				fileName = ze.getName();
				String validateMsg=validateInvoiceFileName("zip",invoiceDelimitter,fileName);
				if(validateMsg.equalsIgnoreCase("valid")) {
				ze = zis.getNextEntry();
				}else {
					throw new BusinessException(ErrorCodes.INVALID_INVOICE_FILE_NAME_FORMAT+": Invoice file name should be Bol number"+invoiceDelimitter+"Invoice number.");

				}
			}

			zis.closeEntry();
			zis.close();

			// System.out.println("Done");
		

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return "valid";
		
	}

	private static String validateInvoiceFileName(String fileExtn, String invoiceDelimitter, String filename) {
		if (null == fileExtn) {
			throw new BusinessException(ErrorCodes.INVALID_INVOICE_FILE_NAME_FORMAT+": Invoice file name should be Bol number"+invoiceDelimitter+"Invoice number.");
		}else {

			if (null == invoiceDelimitter) {
				invoiceDelimitter="_";
			}else {
			
					if (filename.contains(invoiceDelimitter)) {
						String[] parts = filename.split(invoiceDelimitter);
						if (parts != null) {
							if (parts.length >= 2) {
								return "valid";

							} else {
								
								throw new BusinessException(ErrorCodes.INVALID_INVOICE_FILE_NAME_FORMAT+": Invoice file name should be Bol number"+invoiceDelimitter+"Invoice number.");

								
							}
						} else
							
							throw new BusinessException(ErrorCodes.INVALID_INVOICE_FILE_NAME_FORMAT+": Invoice file name should be Bol number"+invoiceDelimitter+"Invoice number.");

							
					} 
				
			}
			throw new BusinessException(ErrorCodes.INVALID_INVOICE_FILE_NAME_FORMAT+": Invoice file name should be Bol number"+invoiceDelimitter+"Invoice number.");

		}

	}

	public static String saveAndGetDocumentPath(String fileContent, String fileName, String rootPath,
			Long doAuthReqId) {
		if (!fileContent.equalsIgnoreCase("amend")) {
			boolean isCreated = false;
			File rootPathLocation = new File(rootPath);
			if (!rootPathLocation.exists()) {
				rootPathLocation.mkdir();
			}
			File dateDir = new File(rootPathLocation + File.separator + DateUtil.getDateVal(new Date()));
			if (!dateDir.exists()) {
				dateDir.mkdir();
			}
			File authIdDir = new File(dateDir.getAbsolutePath()+ File.separator + doAuthReqId);
			if (!authIdDir.exists()) {
				authIdDir.mkdir();
			}

			File doc = new File(authIdDir.getAbsoluteFile() + File.separator + fileName);
			BufferedOutputStream bos = null;
			try {
				bos = new BufferedOutputStream(new FileOutputStream(doc));
				byte[] base64decodedBytes = Base64.getMimeDecoder().decode(fileContent);
				bos.write(base64decodedBytes);
				bos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return doc.getAbsolutePath();
		}
		return null;
}
	
	public static boolean isEmpty(String value) {
		return (value == null || value.length() == 0);
		// return !value;
	}
	
	public static String[] cleanArray(String[] array) {
	    return Arrays.stream(array).filter(x -> !StringUtils.isBlank(x)).toArray(String[]::new);
	}
}
