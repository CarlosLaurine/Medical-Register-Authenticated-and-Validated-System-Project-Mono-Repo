package com.carloslaurinedev.medicalregisterapi.components;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.carloslaurinedev.medicalregisterapi.entities.User;
import com.carloslaurinedev.medicalregisterapi.repositories.UserRepository;

@SuppressWarnings("deprecation")
@Component
public class JwtTokenEnhancer implements TokenEnhancer {

	@Autowired
	private UserRepository userRepository;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		User user = userRepository.findByEmail(authentication.getName());

		Map<String, Object> additionalInfo = new HashMap<>();

		additionalInfo.put("userFirstName", user.getFirstName());
		additionalInfo.put("userId", user.getId());

		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;

		token.setAdditionalInformation(additionalInfo);

		return accessToken;
	}

}