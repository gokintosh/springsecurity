package com.gokul.springsecurityamigo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.gokul.springsecurityamigo.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {



    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //security class
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()//TODO: i will do it later
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*")
                .permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails gokul=User.builder()
                .username("gokul")
                .password(passwordEncoder.encode("gokul"))
                .roles(STUDENT.name())
                .build();

        UserDetails rahul=User.builder()
                .username("rahul")
                .password(passwordEncoder.encode("rahul"))
                .roles(ADMIN.name())
                .build();

        UserDetails mehul=User.builder()
                .username("mehul")
                .password(passwordEncoder.encode("mehul"))
                .roles(ADMINTRAINEE.name())
                .build();


        return new InMemoryUserDetailsManager(
                gokul,
                rahul,
                mehul
        );
    }
}
