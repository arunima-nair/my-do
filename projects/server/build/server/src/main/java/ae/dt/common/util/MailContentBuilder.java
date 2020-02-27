package ae.dt.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class MailContentBuilder {
	   private TemplateEngine templateEngine;
	    private static final String templatePath="mailTemplates/";

	    @Autowired
	    public MailContentBuilder(TemplateEngine templateEngine) {
	        this.templateEngine = templateEngine;
	    }

	    public String build(String mailTemplate,Object message) {
	        Context context = new Context();
	        context.setVariable("message", message);
	        String templateName = templatePath+mailTemplate;
	        return templateEngine.process(templateName, context);
	    }
}
