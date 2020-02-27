
package ae.dt.common.util;


import org.apache.velocity.Template;


import java.util.Map;




public class EmailTemplateVelocity extends VelocityTemplate {
 


  private Template template = null;
     
//     static {
//        getInstance();
//     }
   private String vMFile;
//   public static EmailTemplateVelocityImpl  getInstance(){
//        if (emailTemplateImpl == null)
//             emailTemplateImpl = new EmailTemplateVelocityImpl();
//     return emailTemplateImpl;
//   }
   
    public  EmailTemplateVelocity(String vmFile) {
            template = getTemplate(vmFile);
    }
    
    public String getEmailContent(Map templateMap){
        return getTemplatizedContent(templateMap,template);
    }


    public String getVMFile() {
       return vMFile;
    }


    public void setVMFile(String vMFile) {
        this.vMFile = vMFile;
    }




    public void setTemplate(Template template) {
        this.template = template;
    }


    public Template get_template() {
        return template;
    }
}


