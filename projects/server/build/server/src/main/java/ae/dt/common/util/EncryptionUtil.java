package ae.dt.common.util;

import ae.dt.common.dto.UserDTO;
import ae.dt.common.exception.ApplicationException;
import ae.dt.deliveryorder.exception.UnAuthorizedResourceAccessException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ThreadLocalRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class EncryptionUtil
{
  //  @Value("${encryptionKey}")
    private static String encryptionKey="abcdefghijlkmnop";
    private static final String[] saltArray={"aaaaaaaaaaaaaaaaaaaaaaaa","hdhdhdhshhdhdhdhshhdhdhdhsh","sdfsdccsdfsdccsdfsdcc","eddddddddedddddddd","eddddddddedddddddd","ffsdfsdfffsdfsdfffsdfsdf","gdg","eddddddddedddddddd","eddddddddedddddddd","saltff"};
    private static final String encAlgorith="AES";
    private static final String timeStampPattern="$TIMESTAMP$";
    private static final String userNamePattern="$USERNAME$";


    public static String encrypt(String plainText)
    {
        String enc = Base64.encodeBase64URLSafeString(encryptData(plainText));
        return enc;
    }

    public static String encryptBase64(String plainText)
    {
        String enc = new String(Base64.encodeBase64(plainText.getBytes()));
        return enc;
    }
    
    public static String decryptBase64(String plainText)
    {
        String enc = new String(Base64.decodeBase64(plainText));
        return enc;
    }

    public static String encryptPublic(String plainText)
    {
        String enc = Base64.encodeBase64URLSafeString(encryptDataPublic(plainText));
        return enc;
    }

    private static String getTimeStamp(){
        return timeStampPattern+ new Long( System.currentTimeMillis()).toString()+timeStampPattern;
    }

    private static String getUserName(String userName){
        return userNamePattern+userName+userNamePattern;
    }
    private static String stripTimeStamp(String text){

        int i = text.indexOf(timeStampPattern);
        return text.substring(0, i);
    }

    private static byte[] encryptData(String plainText) {
        byte[] encryptedBytes=null;
        try {
            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
            int key =ThreadLocalRandom.current().nextInt(0,8);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            UserDTO userDTO= (UserDTO) attr.getRequest().getAttribute("userName");
            String userName=userDTO.getUserName();
            if (!StringUtils.isEmpty(userName)) {
                plainText = plainText + saltArray[key] + saltArray[key].length() + Integer.toString(saltArray[key].length()).length() + getTimeStamp() + getUserName(userName);
            }else{
                throw new ApplicationException("User Name Not Available");
            }
            encryptedBytes = cipher.doFinal(plainText.getBytes());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return encryptedBytes;
    }

    private static byte[] encryptDataPublic(String plainText) {
        byte[] encryptedBytes=null;
        try {
            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
            int key =ThreadLocalRandom.current().nextInt(0,8);
            plainText = plainText +saltArray[key]+saltArray[key].length()+ Integer.toString(saltArray[key].length()).length()+getTimeStamp();
            encryptedBytes = cipher.doFinal(plainText.getBytes());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return encryptedBytes;
    }

    public static String decrypt(String encrypted)
    {
        String decryptedText = decryptData(encrypted);
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        UserDTO userDTO= (UserDTO) attr.getRequest().getAttribute("userName");
        String userName=userDTO.getUserName();
        if(!StringUtils.isEmpty(userName)) {
                validateAuthorizedUserName(decryptedText, userName);
        }else{
            throw new UnAuthorizedResourceAccessException("Not allowed to access this Resource");
        }
        decryptedText = stripTimeStamp(decryptedText);
        int keyLength= new Integer(decryptedText.substring(decryptedText.length()-1, decryptedText.length()));
        int saltLength = new Integer(decryptedText.substring(decryptedText.length()-(keyLength+1), decryptedText.length()-1));
        decryptedText= decryptedText.substring(0, (decryptedText.length()-keyLength)-(saltLength+1));
        return  decryptedText;
    }
    

    public static String decryptWithoutUserInfo(String encrypted)
    {
        if (StringUtils.isEmpty(encrypted)){
            return StringUtils.EMPTY;
        }
        String decryptedText = decryptData(encrypted);
        decryptedText = stripTimeStamp(decryptedText);
        int keyLength= new Integer(decryptedText.substring(decryptedText.length()-1, decryptedText.length()));
        int saltLength = new Integer(decryptedText.substring(decryptedText.length()-(keyLength+1), decryptedText.length()-1));
        decryptedText= decryptedText.substring(0, (decryptedText.length()-keyLength)-(saltLength+1));
        return  decryptedText;
    }

    private  static void validateAuthorizedUserName(String decryptText,String userName){

        if (!StringUtils.equalsIgnoreCase(userName, StringUtils.substringBetween(decryptText, userNamePattern, userNamePattern)))
            throw new UnAuthorizedResourceAccessException(userName+" Not allowed to access this Resource");

    }

    private static String decryptData(String encryptedText){
        Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
        byte[] plainBytes =null;
        String decryptedText =null;
        try {
            plainBytes = cipher.doFinal(Base64.decodeBase64(encryptedText));
            decryptedText = new String(plainBytes);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return decryptedText;
    }

    private static Cipher getCipher(int cipherMode)
    {
        String encryptionAlgorithm = encAlgorith;
        SecretKeySpec keySpecification =null;
        Cipher cipher = null;
        try {
            keySpecification = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), encAlgorith);
            cipher = Cipher.getInstance(encryptionAlgorithm);
            cipher.init(cipherMode, keySpecification);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return cipher;
    }

    public static Long getTimeDiffinHours(String encryptedText){
        String decryptedText = decryptData(encryptedText);
        Long createTimeStamp= Long.valueOf(StringUtils.substringBetween(decryptedText, timeStampPattern, timeStampPattern));
        Long timeHoursDiff =  (System.currentTimeMillis()-createTimeStamp)/(1000*60*60);
        return timeHoursDiff;
    }

    public static String urlEncodeString(String text){
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }

        return StringUtils.EMPTY;
    }

    public static void main(String[] arguments)
    {
       // //System.out.println(encrypt("ukwk55TyYIowaFWAumxKYjXIJjm66lBWYgH1kKANGvQUr4vVdh9GJsCiPpCjT5m6FR1Ffvjv4vggcgketCMYuenyhnUhI2J4IA-BAcm0pBWKO4XJYy0T6DbKh_PeFfVc"));
    }
}