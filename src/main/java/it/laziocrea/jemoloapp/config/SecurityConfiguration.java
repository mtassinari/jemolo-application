package it.laziocrea.jemoloapp.config;

import it.laziocrea.jemoloapp.security.*;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.security.*;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration {

    private static JHipsterProperties jHipsterProperties;
    private static RememberMeServices rememberMeServices;
    private static CorsFilter corsFilter;
    private static SecurityProblemSupport problemSupport;
    
    public SecurityConfiguration(JHipsterProperties jHipsterProperties, RememberMeServices rememberMeServices, CorsFilter corsFilter, SecurityProblemSupport problemSupport) {
        SecurityConfiguration.jHipsterProperties = jHipsterProperties;
        SecurityConfiguration.rememberMeServices = rememberMeServices;
        SecurityConfiguration.corsFilter = corsFilter;
        SecurityConfiguration.problemSupport = problemSupport;
    }

    @Bean
    public static AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
        return new AjaxAuthenticationSuccessHandler();
    }

    @Bean
    public static AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
        return new AjaxAuthenticationFailureHandler();
    }

    @Bean
    public static AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler() {
        return new AjaxLogoutSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Configuration
    @Order(1)
    public static class ConfigCandidato extends WebSecurityConfigurerAdapter {
    	@Resource
    	DomainCandidatoDetailsService domainCandidatoDetailService;
    	
    	@Resource
    	@Qualifier("candidatoRemeberMeService")
    	PersistentTokenRememberMeCandidatoServices candidatoRemeberMeService;
    	
	    @Override
	    public void configure(WebSecurity web) {
	        web.ignoring()
	            .antMatchers(HttpMethod.OPTIONS, "/**")
	            .antMatchers("/app/**/*.{js,html}")
	            .antMatchers("/i18n/**")
	            .antMatchers("/content/**")
	            .antMatchers("/swagger-ui/index.html")
	            .antMatchers("/test/**");
	    }
	
	    @Override
	    public void configure(HttpSecurity http) throws Exception {
	        // @formatter:off
	    	http.antMatcher("/api/candidato/**").userDetailsService(domainCandidatoDetailService)
	            .csrf()
	            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
	        .and()
	            .addFilterBefore(corsFilter, CsrfFilter.class)
	            .exceptionHandling()
	            .authenticationEntryPoint(problemSupport)
	            .accessDeniedHandler(problemSupport)
	        .and()
	            .rememberMe()
	            .rememberMeServices(candidatoRemeberMeService)
	            .rememberMeParameter("remember-me")
	            .key(jHipsterProperties.getSecurity().getRememberMe().getKey())
	        .and()
	            .formLogin()
	            .loginProcessingUrl("/api/candidato/authentication")
	            .successHandler(ajaxAuthenticationSuccessHandler())
	            .failureHandler(ajaxAuthenticationFailureHandler())
	            .permitAll()
	        .and()
	            .logout()
	            .logoutUrl("/api/candidato/logout")
	            .logoutSuccessHandler(ajaxLogoutSuccessHandler())
	            .permitAll()
	        .and()
	            .headers()
	            .contentSecurityPolicy("default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' https://fonts.googleapis.com 'unsafe-inline'; img-src 'self' data:; font-src 'self' https://fonts.gstatic.com data:")
	        .and()
	            .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
	        .and()
	            .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'")
	        .and()
	            .frameOptions()
	            .deny()
	        .and()
	            .authorizeRequests()
	            .antMatchers("/api/authenticate").permitAll()
	            .antMatchers("/api/candidato/authenticate").permitAll()
	            .antMatchers("/api/register").permitAll()
	            .antMatchers("/api/candidato/register").permitAll()
	            .antMatchers("/api/activate").permitAll()
	            .antMatchers("/api/candidati/activate").permitAll()
	            .antMatchers("/api/candidato/account/reset-password/init2").permitAll()
	            .antMatchers("/api/candidato/account/reset-password/init").permitAll()
	            .antMatchers("/api/candidato/account/reset-password/finish").permitAll()
	            .antMatchers("/api/**").authenticated()
	            .antMatchers("/management/health").permitAll()
	            .antMatchers("/management/info").permitAll()
	            .antMatchers("/management/prometheus").permitAll()
	            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN);
	        // @formatter:on
	    }
    }
    @Configuration
    @Order(2)
    public static class Config2 extends WebSecurityConfigurerAdapter {
    	
    	@Resource
    	DomainUserDetailsService userDetailsService;
    	
	    @Override
	    public void configure(WebSecurity web) {
	        web.ignoring()
	            .antMatchers(HttpMethod.OPTIONS, "/**")
	            .antMatchers("/app/**/*.{js,html}")
	            .antMatchers("/i18n/**")
	            .antMatchers("/content/**")
	            .antMatchers("/swagger-ui/index.html")
	            .antMatchers("/test/**");
	    }
	
	    @Override
	    public void configure(HttpSecurity http) throws Exception {
	        // @formatter:off
	    	http.userDetailsService(userDetailsService)
	            .csrf()
	            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
	        .and()
	            .addFilterBefore(corsFilter, CsrfFilter.class)
	            .exceptionHandling()
	            .authenticationEntryPoint(problemSupport)
	            .accessDeniedHandler(problemSupport)
	        .and()
	            .rememberMe()
	            .rememberMeServices(rememberMeServices)
	            .rememberMeParameter("remember-me")
	            .key(jHipsterProperties.getSecurity().getRememberMe().getKey())
	        .and()
	            .formLogin()
	            .loginProcessingUrl("/api/authentication")
	            .successHandler(ajaxAuthenticationSuccessHandler())
	            .failureHandler(ajaxAuthenticationFailureHandler())
	            .permitAll()
	        .and()
	            .logout()
	            .logoutUrl("/api/logout")
	            .logoutSuccessHandler(ajaxLogoutSuccessHandler())
	            .permitAll()
	        .and()
	            .headers()
	            .contentSecurityPolicy("default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' https://fonts.googleapis.com 'unsafe-inline'; img-src 'self' data:; font-src 'self' https://fonts.gstatic.com data:")
	        .and()
	            .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
	        .and()
	            .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'")
	        .and()
	            .frameOptions()
	            .deny()
	        .and()
	            .authorizeRequests()
	            .antMatchers("/api/v2/avvisi-homes").permitAll()
	            .antMatchers("/api/public/iscrittiall").permitAll()
	            .antMatchers("/api/authenticate").permitAll()
	            .antMatchers("/api/candidato/authenticate").permitAll()
	            .antMatchers("/api/register").permitAll()
	            .antMatchers("/api/candidato/register").permitAll()
	            .antMatchers("/api/activate").permitAll()
	            .antMatchers("/api/candidati/activate").permitAll()
	            .antMatchers("/api/account/reset-password/init").permitAll()
	            .antMatchers("/api/account/reset-password/finish").permitAll()
	            .antMatchers("/api/**").authenticated()
	            .antMatchers("/management/health").permitAll()
	            .antMatchers("/management/info").permitAll()
	            .antMatchers("/management/prometheus").permitAll()
	            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN);
	        // @formatter:on
	    }
    }
}
