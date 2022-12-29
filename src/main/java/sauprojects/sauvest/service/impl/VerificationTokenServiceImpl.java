package sauprojects.sauvest.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.entity.User;
import sauprojects.sauvest.entity.VerificationToken;
import sauprojects.sauvest.repository.VerificationTokenRepository;
import sauprojects.sauvest.service.VerificationTokenService;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService{
	
	private final VerificationTokenRepository verificationTokenRepo;
	
	@Override
	public String generateVerificationToken(User user) {
		
		String verificationTokenString = UUID.randomUUID().toString();
		VerificationToken verificationToken = VerificationToken.builder()
																   .token(verificationTokenString)
																   .user(user)
															   .build();
		return verificationTokenRepo.save(verificationToken).getToken();
		
	}

	@Override
	public VerificationToken findByToken(String token) {
		
		Optional<VerificationToken> verificationTokenOptional = verificationTokenRepo.findByToken(token);
		
		VerificationToken verificationToken = verificationTokenOptional.orElseThrow(() -> new RuntimeException(String.format("Verification token '%s' not found", token)));
		
		return verificationToken;
	}
	
}
