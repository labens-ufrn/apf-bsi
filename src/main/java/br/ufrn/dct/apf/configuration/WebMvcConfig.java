package br.ufrn.dct.apf.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.sql.DataSource;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${spring.datasource.url}")
    private String jdbcURl;

    @Value("${spring.datasource.driverClassName}")
    private String driverDB;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();

        dataSourceBuilder.driverClassName(driverDB);
        dataSourceBuilder.url(jdbcURl);
        dataSourceBuilder.username(dbUsername);
        dataSourceBuilder.password(dbPassword);

        return dataSourceBuilder.build();
    }

    @Bean("messageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("lang/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * The LocaleResolver comes from very smart interface definitions which makes use of not one but four different techniques to determine current locale, these are:
     * SessionLocaleResolver: uses a locale attribute in the user’s session
     * CookieLocaleResolver: store current locale in the browser’s cookie
     * AcceptHeaderLocaleResolver: uses the primary locale specified in the “accept-language” header of the HTTP request
     * FixedLocaleResolver: always return a fixed default locale
     * @return the current locale (never {@code null})
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new CookieLocaleResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
    }
}
