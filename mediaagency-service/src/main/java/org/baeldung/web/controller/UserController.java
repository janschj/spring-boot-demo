package org.baeldung.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	   @Autowired
	    private TokenStore tokenStore;

    @PreAuthorize("#oauth2.hasScope('read')")
    @RequestMapping(method = RequestMethod.GET, value = "/users/extra")
    @ResponseBody
    public Map<String, Object> getExtraInfo(OAuth2Authentication auth) {
    		System.out.println("xxxxxxxxxxxxxxxxgetExtraInfo1xxxxxxxxxxxxxxxxxxxx");
        final OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
		System.out.println("xxxxxxxxxxxxxxxxgetExtraInfo2xxxxxxxxxxxxxxxxxxxx");
        Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
		System.out.println("xxxxxxxxxxxxxxxxgetExtraInfo3xxxxxxxxxxxxxxxxxxxx");
//        System.out.println("User organization is " + details.get("user_id"));
        final OAuth2AccessToken accessToken = tokenStore.readAccessToken(oauthDetails.getTokenValue());
		System.out.println("xxxxxxxxxxxxxxxxgetExtraInfo4xxxxxxxxxxxxxxxxxxxx");
        System.out.println(accessToken);
		System.out.println("xxxxxxxxxxxxxxxxgetExtraInfo5xxxxxxxxxxxxxxxxxxxx  " + accessToken.getAdditionalInformation());
      System.out.println("User organization is " + accessToken.getAdditionalInformation().get("user_id"));
       return accessToken.getAdditionalInformation();
    }
}
