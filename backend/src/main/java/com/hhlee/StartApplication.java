package com.hhlee;

import jakarta.servlet.MultipartConfigElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.io.File;
import java.util.Locale;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@PropertySources({
//        @PropertySource(value = "classpath:config/application-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
        @PropertySource(value = "classpath:config/application.properties", ignoreResourceNotFound = true)
})

public class StartApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        final Logger logger = LoggerFactory.getLogger(StartApplication.class);
        SpringApplication app = new SpringApplication(StartApplication.class);
        //String profile = System.getProperty("spring.profiles.active");
        System.getProperty("spring.profiles.active", "dev");

        /*
         * ORACLE을 사용할때 Timestamp 컬럼을 변환하지 않고 그대로 가져오면 oracle.sql.Timestamp 타입으로
         * 가져오게된다. (ojdbc 떄문이라는 듯. ) 아래 property를 사용하면 java.sql.Timestamp로
         * 반환해준다.
         */
        // System.setProperty("oracle.jdbc.J2EE13Compliant", "true");

        String homeDir = System.getProperty("homeDir");
        if (homeDir == null || "".equals(homeDir)) {
            homeDir = System.getProperty("user.dir");
            homeDir = homeDir.replace(File.separatorChar + "bin", "");
            System.setProperty("homeDir", homeDir);
            logger.info("[homeDir : {}]", System.getProperty("homeDir"));
        }

        // Application Start
        logger.info("Initializer : " + String.format("%-12s%-10s%-5s", "Application", "Engine", "Start"));
        File pidFile = new File(homeDir + File.separatorChar + "bin" + File.separatorChar + "app.pid");

        app.addListeners(new ApplicationPidFileWriter(pidFile));
        ApplicationContext context = app.run(args);

    }

    /**
     * @param profile
     */
    private static void setProperty(String profile) {
        System.setProperty("response.header.frame.option", "ALLOW-FROM");
        System.setProperty("java.net.preferIPv4Stack", "true");
    }


    // 파일 업로드 관련 설정
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse("500MB"));
        factory.setMaxRequestSize(DataSize.parse("500MB"));
        return factory.createMultipartConfig();
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    // message 파일
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/messages");
        messageSource.setCacheSeconds(60); // reload messages every 10 seconds
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        // 기본값은 무조건 한국어!
        String defauleLocale = "ko";

        // 세션 기준으로 로케일을 설정 한다.
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();

        // 로케일 고정
        //FixedLocaleResolver localeResolver = new FixedLocaleResolver();

        LocaleContextHolder.setLocale(Locale.forLanguageTag(defauleLocale));
        localeResolver.setDefaultLocale(Locale.forLanguageTag(defauleLocale));
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        final Logger logger = LoggerFactory.getLogger(StartApplication.class);
        logger.info("localeChangeInterceptor : {}", localeChangeInterceptor);
        // request로 넘어오는 language parameter를 받아서 locale로 설정 한다.
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}
