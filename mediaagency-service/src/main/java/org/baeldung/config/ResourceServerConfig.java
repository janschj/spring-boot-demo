package org.baeldung.config;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.DelegatingJwtClaimsSetVerifier;
import org.springframework.security.oauth2.provider.token.store.IssuerClaimVerifier;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtClaimsSetVerifier;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import org.springframework.context.annotation.Profile;

@Configuration
@EnableResourceServer

// This isn't the main/standard Resource Server of the project (that's in a different module)
// This is the Resource Server for the Testing OAuth2 with Spring MVC article: http://www.baeldung.com/oauth-api-testing-with-spring-mvc 
// Notice that it's only active via the mvc profile
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	   @Override
	    public void configure(final HttpSecurity http) throws Exception {
	        http.sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
	            .and()
	            .authorizeRequests()
	            // .antMatchers("/swagger*", "/v2/**")
	            // .access("#oauth2.hasScope('read')")
	            .anyRequest()
	            .permitAll();

	    }

	    // JWT token store

	    @Override
	    public void configure(final ResourceServerSecurityConfigurer config) {
	        config.tokenServices(tokenServices());
	    }

	    @Bean
	    public TokenStore tokenStore() {
	        return new JwtTokenStore(accessTokenConverter());
	    }

	    @Bean
	    public JwtAccessTokenConverter accessTokenConverter() {
	        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//	        converter.setSigningKey("123");
	        converter.setJwtClaimsSetVerifier(jwtClaimsSetVerifier());

	        final Resource resource = new ClassPathResource("public.txt");
	        String publicKey = null;
	        try {
	            publicKey = IOUtils.toString(resource.getInputStream(), Charset.defaultCharset());
	        } catch (final IOException e) {
	            throw new RuntimeException(e);
	        }
	        converter.setVerifierKey(publicKey);
	        return converter;
	    }

	    @Bean
	    public JwtClaimsSetVerifier jwtClaimsSetVerifier() {
	        return new DelegatingJwtClaimsSetVerifier(Arrays.asList(issuerClaimVerifier(), customJwtClaimVerifier()));
	    }

	    @Bean
	    public JwtClaimsSetVerifier issuerClaimVerifier() {
	        try {
	            return new IssuerClaimVerifier(new URL("http://localhost:9090"));
	        } catch (final MalformedURLException e) {
	            throw new RuntimeException(e);
	        }
	    }

	    @Bean
	    public JwtClaimsSetVerifier customJwtClaimVerifier() {
	        return new CustomClaimVerifier();
	    }

	    @Bean
	    @Primary
	    public DefaultTokenServices tokenServices() {
	        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
	        defaultTokenServices.setTokenStore(tokenStore());
	        return defaultTokenServices;
	    }
}