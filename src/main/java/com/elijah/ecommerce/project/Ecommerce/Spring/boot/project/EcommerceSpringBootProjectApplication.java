package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableCaching
@SpringBootApplication
public class EcommerceSpringBootProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceSpringBootProjectApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoders(){
		return new BCryptPasswordEncoder();
	}


//	@Bean
//	CommandLineRunner runner(UserAccessService userAccessService){
//		return args -> {
//
//			userAccessService.saveRole(new Role(null,"SUPER_ADMIN"));
//			userAccessService.saveRole(new Role(null,"ADMIN"));
//			userAccessService.saveRole(new Role(null,"USER"));
//			userAccessService.saveRole(new Role(null,"MANAGER"));
//			userAccessService.saveUser(new UsersAccess(null,"Ukeme Elijah","Ukemsco","ukeme",new ArrayList<>()));
//			userAccessService.saveUser(new UsersAccess(null,"Emediong Dan","Ekababa","emediong",new ArrayList<>()));
//
//			userAccessService.addRoleToUser("Ukemsco","SUPER_ADMIN");
//			userAccessService.addRoleToUser("Ukemsco","ADMIN");
//			userAccessService.addRoleToUser("Ekababa","USER");
//		};
//	}

}
