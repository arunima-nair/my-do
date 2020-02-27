package ae.dt.common.util;

import ae.dt.common.dto.MailDTO;
import ae.dt.common.exception.ApplicationException;


import java.io.File;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service("emailService")
public class EMailServiceImp implements EMailService {
    private static  Logger logger = LoggerFactory.getLogger(EMailServiceImp.class);
    private final String ENCODING = "UTF-8";
    private JavaMailSender mailSender = new JavaMailSenderImpl();

    @Value("${mail.host}")
    private String mailhost;
    @Value("${from.email}")
    private String fromEmail;
    @Value("${cc.email}")
    private String ccEmail;



    private void setMailHost(){
        JavaMailSenderImpl javaMailSender = (JavaMailSenderImpl)this.mailSender;

        javaMailSender.setHost(mailhost);
    }

    /**
     * @param mailDTO
     */
    private void inspectMailDTO(MailDTO mailDTO) {
        if (mailDTO == null) {
            logger.error(" Null Pointer Exception");

        }

    /*    if (isArrayEmpty(mailDTO.getTo())) {
            logger.error(" Mail TO field cannot be Empty");

        }

        if (StringUtils.isEmpty(mailDTO.getFrom())) {
            logger.error(" Mail FROM field cannot be Empty");

        }*/
    }

    /**
     * @param strArray
     * @return boolean
     */
    private boolean isArrayEmpty(String[] strArray) {
        if (strArray == null) {
            return true;
        }

        for (int id = 0; id < strArray.length; id++) {
            if (!StringUtils.isEmpty(strArray[id])) {
                return false;
            }
        }

        return true;
    }



    private String[] formatArray(String [] array){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<array.length;i++){
           if (StringUtils.isNotBlank(array[i]))
                sb.append(array[i]).append(",");
        }
        String mailids = StringUtils.chop(sb.toString());
        return StringUtils.split(mailids,",");
    }

    public void sendHtmlMail(String velocityTemplateName,
                    Map<String, Object> mailContentMap, String toMail, String fromMail,
                    String ccMail, String subject) {
          //  if (ConfigManager.getBoolean(ConfigKey.SEND_EMAIL_NOTIFICATIONS)) {
                    try {
                            MimeMessage mimeMessage = mailSender.createMimeMessage();
                            createMimeMessage(mimeMessage, velocityTemplateName,
                                            mailContentMap, toMail,  fromMail, subject);
                            mailSender.send(mimeMessage);
                    } catch (Exception e) {
                            logger.error("Error when sending mail:", e);
//                            throw new ApplicationException("EMAIL_EX","Could Not send mail:"
//                                            + e.getMessage(), e);
                    }
         //   }
    }



    /**
         * @see (String, Map, String, String,
         *      String, String, Map)
         */
        public void sendMailWithAttachments(String velocityTemplateName,
                        Map<String, Object> mailContentMap, String toMail, String fromMail, String subject, Map<String, File> attachments) {
                        try {
                                MimeMessage mimeMessage = mailSender.createMimeMessage();
                                MimeMessageHelper helper = createMimeMessage(mimeMessage,
                                                velocityTemplateName, mailContentMap, toMail, 
                                                fromMail, subject);
                                if (attachments != null && !attachments.isEmpty()) {
                                        logger.debug("Attachment found, adding attachments to the mail");
                                        for (Map.Entry<String, File> entry : attachments.entrySet()) {
                                                String key = entry.getKey();
                                                File pass = entry.getValue();
                                                FileSystemResource file = new FileSystemResource(
                                                                pass);
                                                helper.addAttachment(key, file);
                                            logger.debug("Attachment Successfully added");
//                                                logger.debug(new StringBuilder("Attachment ").append(
//                                                                key).append(" Successfully added"));
                                        }
                                }
                                logger.debug("Trying to send Mail...");
                                mailSender.send(mimeMessage);
                                logger.debug("Mail Sent successfully");
                        } catch (MessagingException e) {
                            logger.error("MessagingException: Error when sending mail with attachment:" + e);
//                            throw new ApplicationException("EMAIL_EX","Could Not send mail with attachment:"
//                                            + e.getMessage(), e);

                        } catch (MailException e2) {
                            logger.error("MailException: Error when sending mail with attachment:" + e2);
//                            throw new ApplicationException("EMAIL_EX","Could Not send mail with attachment:"
//                                            + e2.getMessage(), e2);
                        }

        }

    private MimeMessageHelper createMimeMessage(MimeMessage mimeMessage,
                    String velocityTemplateName, Map<String, Object> mailContentMap,
                    String toMail,  String fromMail, String subject) {
            try {
                    // use the true flag to indicate you need a multipart message
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                    if (StringUtils.isNotEmpty(fromMail)) {
                            helper.setFrom(fromMail);
                    }else{
                           // helper.setFrom(("noreply"+ ConfigManager.getString(ConfigKey.SYSTEM_ENV)).trim()+ "@dubaitrade.ae");
                            //helper.setFrom(FROM_DUBAITRADE);
                    }
                    if (StringUtils.isNotEmpty(toMail)) {
                            helper.setTo(toMail.split(";"));
                            logger.debug(new StringBuilder("Sending Mail To:").append(
                                            toMail).toString());
                    }
                    // parse the ccAddress for mutiple addresses
                   /* if (StringUtils.isNotEmpty(ccMail) && !"".equals(ccMail.trim())) {
                            helper.setCc(ccMail.split(";"));
                            logger.debug(new StringBuilder("Sending Mail cc:").append(
                                            ccMail).toString());
                    }*/
                    if (subject != null) {
                            helper.setSubject(subject);
                    }
                //check this - parameters
                EmailTemplateVelocity emailVelTemplate = new EmailTemplateVelocity(velocityTemplateName);
                String mailContentStr= emailVelTemplate.getEmailContent(mailContentMap);

                    helper.setText(mailContentStr, true);
                   return helper;
            } catch (Exception e) {
                   logger.error("Error when sending mail:" + e);
                    throw new ApplicationException( "Email Exception",e.getMessage(), e);

            }

          }

    @Async
    public void sendEmail(Map<String,String>  mailMap) {
            setMailHost();
        logger.debug("----------sendEmail Starts----------");
       // String encryptdUserId = mailMap.get("userIdStr");
        String vm = mailMap.get("vm");
        String subject = mailMap.get("subject");
        String toEmail = mailMap.get("emailTo");
        String regUrl = mailMap.get("regUrl");
        String imageURL = mailMap.get("imageURL");

        String msg = mailMap.get("msg");
        String passRefNo = mailMap.get("passRefNo");
        String remark = mailMap.get("remark");
        String isPrint=mailMap.get("isPrint");
        String rejectReason = mailMap.get("rejectReason");
        String portCollection = mailMap.get("portCollection");
        String lostPassMsg = mailMap.get("lostPassMsg");
        String google_map_link = mailMap.get("google_map_link");
        String roleName = mailMap.get("roleName");
        Map<String, Object> mailContentMap = new HashMap<String, Object>();
        mailContentMap.put("regUrl", regUrl);
        mailContentMap.put("msg", msg);
        mailContentMap.put("passRefNo", passRefNo);
        mailContentMap.put("remark", remark);
        mailContentMap.put("google_map_link", google_map_link);
        mailContentMap.put("rejectReason", rejectReason);
        mailContentMap.put("lostPassMsg", lostPassMsg);
        mailContentMap.put("isPrint", isPrint);
        mailContentMap.put("portCollection", portCollection);
        mailContentMap.put("imageURL", imageURL);
        mailContentMap.put("roleName", roleName);
        sendHtmlMail(vm, mailContentMap,toEmail,
                                       fromEmail,
                                      null,
                                       subject);
         logger.debug("----------sendEmail Ends----------");
    }

    @Async
    public void sendEmailWithAttachment(Map<String,String>  mailMap, Map<String, File> attachmentMap){
            setMailHost();
        logger.debug("----------sendEmailWithAttachment Starts----------");

        String vm = mailMap.get("vm");
        String subject = mailMap.get("subject");
        String toEmail = mailMap.get("emailTo");
        String regUrl = mailMap.get("regUrl");
        String passRefNo = mailMap.get("passRefNo");
        String remark = mailMap.get("remark");
        String rejectReason = mailMap.get("rejectReason");
        String imageURL = mailMap.get("imageURL");
        String port = mailMap.get("port");
        String portCollection = mailMap.get("portCollection");
        String google_map_link = mailMap.get("google_map_link");
        String roleName = mailMap.get("roleName");
        Map<String, Object> mailContentMap = new HashMap<String, Object>();
        mailContentMap.put("regUrl", regUrl);
        mailContentMap.put("passRefNo", passRefNo);
        mailContentMap.put("remark", remark);
        mailContentMap.put("google_map_link", google_map_link);
        mailContentMap.put("rejectReason", rejectReason);
        mailContentMap.put("imageURL", imageURL);
        mailContentMap.put("port", port);
        mailContentMap.put("portCollection", portCollection);
        mailContentMap.put("roleName", roleName);
        sendMailWithAttachments(vm, mailContentMap,toEmail,
                                        fromEmail,subject,attachmentMap);
        logger.debug("----------sendEmailWithAttachment Ends----------");

    }

}
