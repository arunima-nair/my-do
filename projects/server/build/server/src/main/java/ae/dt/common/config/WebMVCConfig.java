package ae.dt.common.config;

import ae.dt.common.constants.Constants;
import ae.dt.common.dto.UserDTO;
import ae.dt.common.interceptor.AppInterceptor;


import ae.dt.common.interceptor.WebInterceptor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Carren.Dsouza on 26/05/2017.
 */
@Configuration
public class WebMVCConfig extends WebMvcConfigurerAdapter {


    @Autowired
    AppInterceptor appInterceptor;

    @Autowired
    WebInterceptor webInterceptor;



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
//        registry.addInterceptor(appInterceptor).addPathPatterns("/do/app/api/secure/**");
        registry.addInterceptor(webInterceptor).addPathPatterns("/**");
       // registry.addInterceptor(appInterceptor).addPathPatterns("/app/api/secure/**");
    }

    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.US);
        return sessionLocaleResolver;
    }

    @Bean
    LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return  localeChangeInterceptor;
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.basicAuthorization(Constants.CLIENT_ID, Constants.CLIENT_SECRET).build();
    }

//    @Bean
//    DateMapper dateMapper() {
//        return new DateMapper();
//    }

    @Bean
    ErrorViewResolver supportPathBasedLocationStrategyWithoutHashes() {
        return new ErrorViewResolver() {
            @Override
            public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
                //System.out.println(" Unable to find MVC");
                return status == HttpStatus.NOT_FOUND
                        ? new ModelAndView("index", Collections.<String, Object>emptyMap(), HttpStatus.OK)
                        : null;
            }
        };
    }



}
