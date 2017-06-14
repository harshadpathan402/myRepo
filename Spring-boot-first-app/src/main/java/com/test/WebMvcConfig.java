package com.test;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
@ComponentScan(basePackages = { "com.test"}) 
@EnableWebMvc
public class WebMvcConfig {

	
	 @Bean
	    public ViewResolver getViewResolver() {
	        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	        resolver.setPrefix("templates/");
	        //resolver.setSuffix(".html");
	        return resolver;
	    }
	    
	    public void addResourceHandlers(ResourceHandlerRegistry registry)
	    {
	        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	    }
	    
	    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
	    {
	        configurer.enable();
	    }
	    @Bean(name = "messageSource")
	    public MessageSource configureMessageSource()
	    {
	        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	        messageSource.setBasename("classpath:messages");
	        messageSource.setCacheSeconds(5);
	        messageSource.setDefaultEncoding("UTF-8");
	        return messageSource;
	    }
	
}
