package com.springbootREST.springbootREST.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class DemoSecurityConfig {


    // adding support for JDBC - no more hardcoded users

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        /* checking roles for users with default names

        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        JdbcTemplate jdbc = new JdbcTemplate(dataSource);

        List<String> usernames = jdbc.queryForList("SELECT username FROM users", String.class);
        System.out.println("Users:");
        usernames.forEach( username -> {
            List<String> roles = jdbc.queryForList("SELECT authority FROM authorities WHERE username = ?", String.class, username);
            System.out.println(username + roles);
            });

        */

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // defining query to retrieve a user by username - db with custom names for entities and column
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT uID, pw, active FROM customUsers where uID=?");

        // defining query to retrieve the roles by username - db with custom names for entities and column
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT uID_FK, role FROM customRoles where uID_FK=?");

        return jdbcUserDetailsManager;
    }



    @Bean
    public SecurityFilterChain filterChan(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/employees").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/employees/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/employees/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PATCH, "/employees/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/employees/**").hasRole("ADMIN")
        );

        // using http basic authentication

        http.httpBasic( Customizer.withDefaults() );

        // disabling CSRF - this is not required for stateless REST APIs that use POST, PUT, DELETE and/or PATCH

        http.csrf( csrf -> csrf.disable() );

        return http.build();

    }


    /* hardcoded users without DB

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {

        UserDetails employeeUser = User.builder()
                .username("employeeUser")
                .password("{noop}employee123")
                .roles("EMPLOYEE")
                .build();

        UserDetails managerUser = User.builder()
                .username("managerUser")
                .password("{noop}manager123")
                .roles("EMPLOYEE", "MANAGER")
                .build();


        UserDetails directorUser = User.builder()
                .username("directorUser")
                .password("{noop}director123")
                .roles("EMPLOYEE", "MANAGER")
                .build();

        return new InMemoryUserDetailsManager(employeeUser, managerUser, directorUser);
    }

    */




}
