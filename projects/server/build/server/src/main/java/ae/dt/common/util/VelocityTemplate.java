
/**
 * @copyright 2006 DubaiWorld. All rights reserved.
 * @service Velocity Template Wrapper
 * @Date 10 Oct 2006
 * @Description This is a wrapper for  the Velocity Template Engine
 * @author    <i>Carren Dsouza</i>
 * @version 1.0
 *
 */
package ae.dt.common.util;

import java.io.IOException;
import java.io.StringWriter;

import java.net.URL;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class VelocityTemplate {
    private static  Logger log = LoggerFactory.getLogger(VelocityTemplate.class);
  static {
      init();
  }
    /**
     * This method is used to intialise the Velocity engine
     */
    private static void init() {
            IntilizseVelocityEngine(loadProperties(getPropsFile()));
            log.debug("SuccessFully Intiliziated Velocity Engine using Default Properties");
    }

    private static Properties loadProperties(String propertyFileName)  {
        try {
         Properties props = new Properties();
         ClassLoader loader = Thread.currentThread().getContextClassLoader();
         URL url = loader.getResource(propertyFileName);
         props.load(url.openStream());
        log.debug("Loaded Properties File for Velocity");
         return props;
    } catch (IOException e) {
            log.error(e.getMessage(),e);
             throw new RuntimeException(e);
        }
    }

    private static void IntilizseVelocityEngine(Properties props) {
      try {
        Velocity.init(props);
        log.debug("Intialized Velocity Engine");
      } catch(Exception e ){
          log.error(e.getMessage(),e);
          throw new RuntimeException(e);
      }
    }


    /**
     * Used to Method to Override the Default Intialization of The Velocity Engine, with Custom Properties
     * @param props
     */
    protected static void init(Properties props){
        log.debug("SuccessFully Intiliziated Velocity Engine using Custom Properties");
        IntilizseVelocityEngine(props);
    }

    /**
     * Used to Method to Override the Default Intialization of The Velocity Engine, with Custom Properties File
     * @param fileName
     */
    protected static void init(String fileName){
        log.debug("SuccessFully Intiliziated Velocity Engine using Custom Properties File");
        IntilizseVelocityEngine(loadProperties(fileName));
    }


    /**
     * This returns the Merged String content of the corresponding tempalate
     * @param templateMap
     * @param template
     * @return String
     */
    protected static String getTemplatizedContent(Map templateMap,Template template){
          StringWriter stringWriter = new StringWriter();
          VelocityContext velocityContext = new VelocityContext();
           try {
               Set keySet = templateMap.keySet();
               for (Iterator iter= keySet.iterator();iter.hasNext();) {
                   Object val = iter.next();
                   velocityContext.put(val.toString(),templateMap.get(val));
               }
           
               template.merge(velocityContext,stringWriter);
               log.debug("Merged Veclocity Context to the Template Successfully");
               return stringWriter.toString();
           }  catch (MethodInvocationException e) {
               log.error(e.getMessage(),e);
               throw new RuntimeException(e);
           } catch (ParseErrorException e) {
               log.error(e.getMessage(),e);
               throw new RuntimeException(e);
           } catch (ResourceNotFoundException e) {
               log.error(e.getMessage(),e);
               throw new RuntimeException(e);
           } catch (Exception e) {
               log.error(e.getMessage(),e);
               throw new RuntimeException(e);
           } finally {
               velocityContext = null;
           }
   }


    /**
     * This method returns a Template for a vmFile in the classpath
     * @param vmFileName
     * @return Template
     */
    protected Template getTemplate(String vmFileName){
       try {
          return Velocity.getTemplate(vmFileName);
       } catch (Exception e) {
           log.error(e.getMessage(),e);
           throw new RuntimeException(e);
       }
       
   }

    private static String getPropsFile() {
       return "velocity.properties";
    }
}
