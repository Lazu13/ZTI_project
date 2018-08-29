package pl.edu.agh.zti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Rest controller of authorization service
 */
@Controller
public class OAuthController {
    private TokenStore tokenStore;

    /**
     * Constructor with autowired argument
     *
     * @param tokenStore token store
     */
    @Autowired
    public OAuthController(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    /**
     * Revoke token method.
     * It allows to remove access token from token store.
     *
     * @param request http request
     */
    @RequestMapping(value = "/oauth/revoke-token", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
    }
}
