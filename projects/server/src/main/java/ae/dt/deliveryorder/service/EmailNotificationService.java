package ae.dt.deliveryorder.service;

import ae.dt.common.dto.MailDTO;

public interface EmailNotificationService {
	
	public void sendMail(MailDTO mailDto,String mailPurpose);
}
