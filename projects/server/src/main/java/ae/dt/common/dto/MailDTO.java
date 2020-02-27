package ae.dt.common.dto;

import java.io.File;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


	
	@Data
	@Builder
    @NoArgsConstructor
	@AllArgsConstructor
	public class MailDTO { 
		private String fromAddress;
	 
	private String[] ccAddress;
	 
	private String bccAddress;
	 
	private String toAddress;
	 
	private String subject;
	 
	private File[] attchments; 
	 
	private  Map<String, String> mailContentMap;
	 
	private  String body;

	private String[] toAddressList;
}
