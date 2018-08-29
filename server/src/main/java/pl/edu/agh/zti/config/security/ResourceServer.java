package pl.edu.agh.zti.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

/**
 * Source enabling a Spring Security filter that authenticates requests via
 * an incoming OAuth2 token.
 */
@Configuration
@EnableResourceServer
public class ResourceServer extends ResourceServerConfigurerAdapter {

    private final DefaultTokenServices tokenServices;

    /**
     * Constructor with autowired argument
     *
     * @param tokenServices service providing tokens
     */
    @Autowired
    public ResourceServer(DefaultTokenServices tokenServices) {
        this.tokenServices = tokenServices;
    }

    /**
     * Overridden configuration method to set token service
     *
     * @param resources security configurer
     * @throws Exception if something goes wrong
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(tokenServices);
    }

    /**
     * Overridden configuration method to authorize specific http request.
     * It allows any new user to register, any unregistered user to query for non-security
     * prone resources. Any other requests require authentication+/authorization.
     *
     * @param http http security object
     * @throws Exception if something goes wrong
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.GET, "/matches/all/group").permitAll()
                .antMatchers(HttpMethod.GET, "/matches/all/knockout").permitAll()
                .antMatchers(HttpMethod.GET, "/deadline").permitAll()
                .antMatchers(HttpMethod.GET, "/next_match").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
