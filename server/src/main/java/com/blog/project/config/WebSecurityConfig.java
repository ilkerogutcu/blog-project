package com.blog.project.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private AuthTokenFilter jwtRequestFilter;

    /**
     * @param auth
     * @throws Exception kullanıcının bilgilerinin hafızada tutulacağını şifrelerinin passwordEncoder ile şifreliyoruz.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * @param httpSecurity
     * @throws Exception e
     *                   Güvenlik Ayarları
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                csrf().
                disable()
                .authorizeRequests().
                antMatchers(
                        "/api/auth/register",
                        "/api/auth/login",
                        "/api/auth/get/**",
                        "/api/get/posts",
                        "/api/get/post/**",
                        "/api/get/**",
                        "http://localhost:8081/**",
                        "/api/get/tag/**",
                        "/api/get/tags",
                        "/api/image/**",
                        "/api/images/**",
                        "/api/create/**"


                ).
                permitAll().
                anyRequest().
                authenticated().
                and().
                exceptionHandling().
                authenticationEntryPoint(jwtAuthenticationEntryPoint).
                and().
                sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        /**
         * HER ISTEKTE DOĞRULAMAK IÇIN FILTRE EKLIYORUZ
         */
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}