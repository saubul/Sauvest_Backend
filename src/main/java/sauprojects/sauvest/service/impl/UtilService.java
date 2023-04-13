package sauprojects.sauvest.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import ru.tinkoff.piapi.core.InvestApi;


public final class UtilService {
	
	public static String getUsernameFromJwt(String authorizationHeader) {
		String accessToken = authorizationHeader.substring("Bearer ".length());
		
		DecodedJWT decodedJWT = JWT.decode(accessToken);
		
		String username = decodedJWT.getClaim("username").asString();
		return username;
	}
	
	@Cacheable(cacheNames = "InvestAPIs")
	public static InvestApi getInvestApi(String SSOToken) {
		InvestApi investApi = InvestApi.create(SSOToken);
		return investApi;
	}

	@CacheEvict(cacheNames = "InvestAPIs", key = "#ssoToken")
	public static void evictInvestApi(String SSOToken) {
	}
	
}
