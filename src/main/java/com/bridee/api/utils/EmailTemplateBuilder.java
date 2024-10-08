package com.bridee.api.utils;

import com.bridee.api.entity.enums.email.fields.EmailFields;
import com.bridee.api.entity.enums.email.template.EmailTemplate;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.context.Context;

import java.util.Map;

@Component
public class EmailTemplateBuilder {

    public static String generateHtmlEmailTemplate(EmailTemplate emailTemplate, Map<EmailFields, Object> values){

        ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setSuffix(".html");
        classLoaderTemplateResolver.setCharacterEncoding("UTF-8");
        classLoaderTemplateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(classLoaderTemplateResolver);
        Context context = setContextVariables(values);

        return templateEngine.process("/email/templates/%s".formatted(emailTemplate.getValue()), context);

    }

    private static Context setContextVariables(Map<EmailFields, Object> values) {
        Context context = new Context();

        values.forEach((key, value) -> {
            context.setVariable(key.getValue(), value);
        });

        return context;
    }

}
