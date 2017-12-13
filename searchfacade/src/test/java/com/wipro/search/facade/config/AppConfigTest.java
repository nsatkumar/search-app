/*
 * @author nsatkumar
 * 
 * @version 1.0
 * 
 * @date 12 Dec, 2017
 * 
 */
package com.wipro.search.facade.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * The Class AppTestConfig.
 */
@Configuration
@ComponentScan(basePackages = { "com.wipro.search.facade", "com.wipro.search.service" })
public class AppConfigTest {

}
