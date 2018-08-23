package org.gear.util.gateway;

import javax.validation.Validator;

import org.gear.common.Gear;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@EnableAspectJAutoProxy
public class WebConfig {
	
	@Bean("validator")
	public Validator validator() {
		LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
		factory.setProviderClass(HibernateValidator.class);
		factory.setValidationMessageSource(messageSource());
		return factory;
	}
	
	@Bean("messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		String langFile = "classpath:conf/lang/lang_" + Gear.config().getLocale().mark();
		messageSource.setBasenames(new String[] {langFile, "classpath:org/hibernate/validator/ValidationMessages"});
		messageSource.setUseCodeAsDefaultMessage(false);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(60);
		return messageSource;
	}
}
