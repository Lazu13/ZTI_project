package pl.edu.agh.zti.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Collections;

/**
 * Configuration of authorization server
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    private final String CLIENT_ID = "ZTI";
    private final String CLIENT_SECRET = "ZTI";

    private final TokenStore tokenStore;
    private final JwtAccessTokenConverter accessTokenConverter;
    private final AuthenticationManager authenticationManager;

    /**
     * Constructor with autowired arguments
     *
     * @param tokenStore            token store
     * @param accessTokenConverter  access token converter
     * @param authenticationManager authentication manager
     */
    @Autowired
    public AuthorizationServer(
            TokenStore tokenStore,
            JwtAccessTokenConverter accessTokenConverter,
            AuthenticationManager authenticationManager) {
        this.tokenStore = tokenStore;
        this.accessTokenConverter = accessTokenConverter;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Overridden configuration method to expose token verification endpoints
     *
     * @param oauthServer authorization server security congfigurer
     * @throws Exception if something goes wrong
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * Overridden configuration method for client authorization based on tokens.
     * It specifies options for token: validity time, scopes based on client id and client secret
     *
     * @param clients client details service configurer
     * @throws Exception if something goes wrong
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient(CLIENT_ID)
                .secret(CLIENT_SECRET)
                .authorizedGrantTypes("password", "refresh_token", "authorization_code")
                .scopes("read", "write")
                .accessTokenValiditySeconds(60 * 60 * 8)
                .refreshTokenValiditySeconds(60 * 60 * 30);
    }

    /**
     * Overridden configuration method for properties and enhanced functionality
     * configuration of the Authorization Server endpoints.
     * It gather token store, token converter, authentication manager all together
     *
     * @param endpoints authorization server endpoints configurer
     * @throws Exception if something goes wrong
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Collections.singletonList(accessTokenConverter));
        endpoints.tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter)
                .tokenEnhancer(enhancerChain)
                .authenticationManager(authenticationManager);
    }
}
