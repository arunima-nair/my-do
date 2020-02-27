package ae.dt.common.util;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


/**
 * singleton wrapper class for Configuration class
 */
@Component
 public class ApplicationPropertyLoader {

    // private static final Log log = LogFactory.getLog(ApplicationPropertyLoader.class);

    @Autowired
    private  Environment env;


     public Environment getEnv(){
         return env;
     }
     public  Object getProperty(String property) {
         return env.getProperty(property);
     }
     public   String getStringProperty(String property)  {
         return env.getProperty(property);
     }
     
     public  int getIntProperty(String property) {

         return Integer.parseInt(env.getProperty(property));
     }

     public  String [] getPropertyArray(String property) {
         String arr =  env.getProperty(property);
        return  StringUtils.split(arr,",");
     }
 }


