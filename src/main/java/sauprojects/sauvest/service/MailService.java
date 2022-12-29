package sauprojects.sauvest.service;

import sauprojects.sauvest.entity.NotificationEmail;

public interface MailService {

	void sendMail(NotificationEmail notificationEmail);
	
}
