package com.hotelchatbot.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Send simple email notification using Booking information
    public void sendBookingEmail(
            String recipient, String subject, String hotelName, String firstName, String lastName,
            String email, String roomType, String billingAddress, String cardNo, String checkInDate, String checkOutDate) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            String emailTemplate = """
                <h2> Thank you for booking at the %s!</h2>
                <h4> Please see your booking information below:</h4>
                <ul style="font-size: 1em; padding-left: 20px;">
                  <li> Name: %s %s</li>
                  <li> Email: %s</li>
                  <li> Room Booked: %s</li>
                  <li> Billing Address: %s</li>
                  <li> Card No: %s</li>
                  <li> Booked For: %s - %s</li>
                </ul>
                <h4>We hope you enjoy your stay!</h4>
                """;
            String cardLastFourDigits = maskCardNo(cardNo);
            String body = String.format(emailTemplate, hotelName, firstName, lastName,
                    email, roomType, billingAddress, cardLastFourDigits, checkInDate, checkOutDate);
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(body, true); // true for HTML

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public String maskCardNo(String cardNo) {
        if (cardNo == null || cardNo.length() <= 4) {
            return cardNo;
        }
        return "#".repeat(cardNo.length() - 4) + cardNo.substring(cardNo.length() - 4);
    }

}
