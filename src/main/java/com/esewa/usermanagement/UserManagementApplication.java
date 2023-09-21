package com.esewa.usermanagement;

//import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
//import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@EnableCaching
@SpringBootApplication
@EnableTransactionManagement
//@EnableEncryptableProperties
//@PropertySource(value = "file:/C:/Users/ThinkPad/Documents/Property_File/external_application.properties",ignoreResourceNotFound = true)
public class UserManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}

}
