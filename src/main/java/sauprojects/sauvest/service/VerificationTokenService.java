package sauprojects.sauvest.service;

import sauprojects.sauvest.entity.User;
import sauprojects.sauvest.entity.VerificationToken;


public interface VerificationTokenService {

	String generateVerificationToken(User user);
	
	VerificationToken findByToken(String token);
	
}
