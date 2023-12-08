package com;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.config.AppConstants;
import com.entites.Role;
import com.repositories.RoleRepo;

@SpringBootApplication
public class MySpringBootAa27BlogAppApIsVmApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(MySpringBootAa27BlogAppApIsVmApplication.class, args);
	}
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {
		
		try {
			
			Role role1 = new Role();
			role1.setId(AppConstants.ADMIN_USER);
			role1.setName("ROLE_ADMIN");
			
			Role role2 = new Role();
			role2.setId(AppConstants.NORMAL_USER);
			role2.setName("ROLE_NORMAL");
			
		List<Role> roles = List.of(role1,role2);
			
		List<Role> result =	roleRepo.saveAll(roles);
			
		result.forEach(r -> {
			System.out.println(r.getName());
		});
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
