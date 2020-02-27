package ae.dt.deliveryorder.service;

import java.net.URISyntaxException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ae.dt.common.constants.Constants;
import ae.dt.common.dto.MailDTO;
import ae.dt.common.util.ApplicationPropertyLoader;
import ae.dt.common.util.EmailTemplateVelocity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailNotificationServiceImpl implements EmailNotificationService {

	private Logger logger = LoggerFactory.getLogger(EmailNotificationServiceImpl.class);

	private JavaMailSender emailSender = new JavaMailSenderImpl();

	@Autowired
	private ApplicationPropertyLoader applicationPropertyLoader;

	@Override
	@Async
	public void sendMail(MailDTO mailDto, String purpose) {

		setMailHost();

		try {
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			createMimeMessage(mailDto, mimeMessage, purpose);
			emailSender.send(mimeMessage);
			logger.debug(" Mail Sent Succesfully to  {} ", mailDto.getToAddress());
		} catch (Exception exception) {
			logger.error(" {} ", exception);
		}

	}

	private MimeMessageHelper createMimeMessage(MailDTO mailDto, MimeMessage mimeMessage, String purpose)
			throws MessagingException, URISyntaxException {
		String[] listOfToMail = null;
		if( mailDto.getToAddress()!=null)
			listOfToMail=	mailDto.getToAddress().contains(";")?mailDto.getToAddress().split(";"):mailDto.getToAddress().split(",");
		if(mailDto.getToAddressList()!=null) {
			listOfToMail=mailDto.getToAddressList();

		}
	
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setFrom(applicationPropertyLoader.getStringProperty("from.email"));
		mimeMessageHelper.setTo(listOfToMail);

		mimeMessageHelper.setCc(mailDto.getCcAddress());

		if (mailDto.getBccAddress() != null && mailDto.getBccAddress().length() > 0)
			mimeMessageHelper.setBcc(mailDto.getBccAddress());

		mimeMessageHelper.setSubject(mailDto.getSubject());
		EmailTemplateVelocity emailVelTemplate = null;
		if (purpose.equalsIgnoreCase(Constants.EMAIL_TYPE.UPLOAD_BOL.value))
			emailVelTemplate = new EmailTemplateVelocity("/vmfiles/emailUploadBL.vm");
		else if (purpose.equalsIgnoreCase("UPLOAD_INVOICE"))
			emailVelTemplate = new EmailTemplateVelocity("/vmfiles/emailUploadInvoice.vm");
		else if (purpose.equalsIgnoreCase("BOL_REQUEST"))
			emailVelTemplate = new EmailTemplateVelocity("/vmfiles/emailRequestBlNew.vm");
		else if (purpose.equalsIgnoreCase("INVOICE_REQUEST"))
			emailVelTemplate = new EmailTemplateVelocity("/vmfiles/emailRequestInvoiceNew.vm");
		else if (purpose.equalsIgnoreCase("DO_REQUEST"))
			emailVelTemplate = new EmailTemplateVelocity("/vmfiles/emailRequestDO.vm");
		else if (purpose.equalsIgnoreCase("DO_PROCESSED"))
			emailVelTemplate = new EmailTemplateVelocity("/vmfiles/emailProcessDO.vm");
		else if (purpose.equalsIgnoreCase("NEW_DO_REQUEST"))
			emailVelTemplate = new EmailTemplateVelocity("/vmfiles/emailNewRequestDO.vm");
		else if (purpose.equalsIgnoreCase("NEW_DO_REQUEST_SA"))
			emailVelTemplate = new EmailTemplateVelocity("/vmfiles/emailNewRequestDOSA.vm");
		/*
		 * else if (purpose.equalsIgnoreCase("DO_REQUEST_PARTIAL_PAYMENT"))
		 * emailVelTemplate = new
		 * EmailTemplateVelocity("/vmfiles/emailPartialPayment.vm");
		 */
		else if(purpose.equalsIgnoreCase("FILE_PROCESS_REFERENCE"))
			emailVelTemplate = new EmailTemplateVelocity("/vmfiles/emailFileProcessReference.vm");
		else if(purpose.equalsIgnoreCase("FAILED_FILE_PROCCESS"))
			emailVelTemplate = new EmailTemplateVelocity("/vmfiles/emailFailedFileProcess.vm");
		else if (purpose.equalsIgnoreCase("UPLOAD_BOL_CONSIGNEE"))
			emailVelTemplate = new EmailTemplateVelocity("/vmfiles/emailUploadBLConsignee.vm");
		else if (purpose.equalsIgnoreCase("DO_APPROVED"))
			emailVelTemplate = new EmailTemplateVelocity("/vmfiles/emailApproveDO.vm");
		
		else if (purpose.equalsIgnoreCase(Constants.EMAIL_TYPE.DO_REQUEST_BOL.value))
			emailVelTemplate = new EmailTemplateVelocity("/vmfiles/requestDOEmailBl.vm");
		else if (purpose.equalsIgnoreCase(Constants.EMAIL_TYPE.DO_REQUEST_DO.value))
			emailVelTemplate = new EmailTemplateVelocity("/vmfiles/requestDOEmailDO.vm");
		else if (purpose.equalsIgnoreCase(Constants.EMAIL_TYPE.DO_REQUEST_PARTIAL_PAYMENT_DO.value))
			emailVelTemplate = new EmailTemplateVelocity("/vmfiles/requestDOPartialPaymentDO.vm");
		else if (purpose.equalsIgnoreCase(Constants.EMAIL_TYPE.DO_REQUEST_PARTIAL_PAYMENT_BOL.value))
			emailVelTemplate = new EmailTemplateVelocity("/vmfiles/requestDOPartialPaymentBOL.vm");
		else if (purpose.equalsIgnoreCase(Constants.EMAIL_TYPE.DO_REQUEST_SA.value))
			emailVelTemplate = new EmailTemplateVelocity("/vmfiles/emailApproveDO.vm");
		else if (purpose.equalsIgnoreCase(Constants.EMAIL_TYPE.UPLOAD_BOL_SA.value))
			emailVelTemplate = new EmailTemplateVelocity("/vmfiles/emailUploadBolSA.vm");
		else if (purpose.equalsIgnoreCase(Constants.EMAIL_TYPE.REJECT_RETURN_DO.value))
			emailVelTemplate = new EmailTemplateVelocity("/vmfiles/emailReturnRejectDO.vm");
		
		String mailContentStr = emailVelTemplate.getEmailContent(mailDto.getMailContentMap());
		mimeMessageHelper.setText(mailContentStr, true);

		return mimeMessageHelper;

	}

	private void setMailHost() {
		JavaMailSenderImpl javaMailSender = (JavaMailSenderImpl) this.emailSender;

		javaMailSender.setHost(applicationPropertyLoader.getStringProperty("mail.host"));
	}

}