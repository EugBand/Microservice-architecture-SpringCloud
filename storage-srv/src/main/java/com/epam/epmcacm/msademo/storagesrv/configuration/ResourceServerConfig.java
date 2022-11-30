package com.epam.epmcacm.msademo.storagesrv.configuration;

import com.epam.epmcacm.msademo.storagesrv.filter.CustomFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ResourceServerConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(new CustomFilter(), BearerTokenAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/storages/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/storages/**")
                .authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/v1/storages/**")
                .access("hasAuthority('SCOPE_write')")
//                .hasAuthority("storages.delete")
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }
}
