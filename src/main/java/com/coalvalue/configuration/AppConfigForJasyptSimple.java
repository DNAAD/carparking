package com.coalvalue.configuration;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EncryptablePropertySource(value = "encrypted.property")
public class AppConfigForJasyptSimple {


	@Bean
    @ConditionalOnMissingBean(ClassPathTldsLoader.class)
	public ClassPathTldsLoader classPathTldsLoader(){
		return new ClassPathTldsLoader();
	}

}