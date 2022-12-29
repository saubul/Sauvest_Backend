package sauprojects.sauvest.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.entity.NotificationEmail;
import sauprojects.sauvest.service.MailService;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
	
	private final JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String from;
	
	@Override
	@Async
	public void sendMail(NotificationEmail notificationEmail) {
		
		MimeMessagePreparator mimeMessagePreparator = (mimeMessage) -> {
				
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
			mimeMessageHelper.setFrom(from);
			mimeMessageHelper.setTo(notificationEmail.getRecipient());
			mimeMessageHelper.setSubject(notificationEmail.getSubject());
			mimeMessageHelper.setText(notificationEmail.getBody());
			
		};
		try {
			javaMailSender.send(mimeMessagePreparator);
		} catch (MailException e) {
			throw new RuntimeException("Exception occured while sending a mail to: " + notificationEmail.getRecipient(), e);
		}
		
	}
}
