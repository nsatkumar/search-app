/*
 * @author nsatkumar
 * 
 * @version 1.0
 * 
 * @date 12 Dec, 2017
 * 
 */
package com.wipro.search.web.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * The Class SearchRestConfig.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.wipro.search.web")
@ComponentScan(basePackages = "com.wipro.search.facade")
@ComponentScan(basePackages = "com.wipro.search.service")
public class SearchRestConfig {

	/**
	 * Resolver.
	 *
	 * @return the internal resource view resolver
	 */
	@Bean
	public InternalResourceViewResolver resolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	/**
	 * Message source.
	 *
	 * @return the message source
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

}
