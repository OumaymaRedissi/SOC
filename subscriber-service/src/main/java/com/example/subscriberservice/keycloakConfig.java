package com.example.subscriberservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class keycloakConfig{
    @Bean
    KeycloakSpringBootConfigResolver keycloakConfigResolver(){
        return new KeycloakSpringBootConfigResolver();
    }
}
@KeycloakConfiguration
class SercurityConfiguration extends KeycloakWebSecurityConfigurerAdapter{
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(keycloakAuthenticationProvider());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.authorizeRequests().antMatchers("/prods/**").authenticated();
    }
}
