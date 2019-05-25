package br.ufrn.dct.apf.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// >> https://www.baeldung.com/web-mvc-configurer-adapter-deprecated
// public class WebMvcConfig extends WebMvcConfigurerAdapter {

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
     @Autowired
     public BCryptPasswordEncoder passwordEncoder;

}
