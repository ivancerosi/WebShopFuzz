package hr.algebra.api.webshop2024api.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

//FOR SWAGGER  https://www.baeldung.com/spring-rest-openapi-documentation

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        logger.info("Configuring UserDetailsManager...");
        JdbcUserDetailsManager theUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        theUserDetailsManager
                .setUsersByUsernameQuery("select username, password, enabled from users where username=?");
        theUserDetailsManager
                .setAuthoritiesByUsernameQuery("select username, authority from authorities where username=?");

        return theUserDetailsManager;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring Security Filter Chain...");
        http.authorizeHttpRequests(configurer ->
                configurer
                        // Swagger UI and API Docs
                        .antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/webjars/**", "/api-docs/**").permitAll()
                        //Categories
                        .antMatchers(HttpMethod.GET, "/webShopApi/categories/allCategories").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET, "/webShopApi/categories/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST, "/webShopApi/categories").hasRole("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/webShopApi/categories/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/webShopApi/categories/**").hasRole("ADMIN")

                        //Subcategories
                        .antMatchers(HttpMethod.GET, "/webShopApi/subcategories/allSubcategories").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET, "/webShopApi/subcategories/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST, "/webShopApi/subcategories").hasRole("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/webShopApi/subcategories/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/webShopApi/subcategories/**").hasRole("ADMIN")

                        //Products
                        .antMatchers(HttpMethod.GET, "/webShopApi/products/allProducts").permitAll()
                        .antMatchers(HttpMethod.GET, "/webShopApi/products/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/webShopApi/products").hasRole("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/webShopApi/products/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/webShopApi/products/**").hasRole("ADMIN")

                        //Shopping
                        .antMatchers(HttpMethod.GET, "/webShopApi/cartItems/allCartItems").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET, "/webShopApi/cartItems/findByShoppingCartId/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET, "/webShopApi/cartItems/findBySession/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET, "/webShopApi/cartItems/findByUsername/**").hasRole("ADMIN")

        );
        // use HTTP Basic authentication
        http.httpBasic(Customizer.withDefaults());
        // disable Cross Site Request Forgery (CSRF)
        http.csrf(csfr->csfr.disable());
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/v3/api-docs/**");
    }
}