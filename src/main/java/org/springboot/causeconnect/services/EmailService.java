package org.springboot.causeconnect.services;

import org.springboot.causeconnect.entities.Event;
import org.springboot.causeconnect.entities.OTP;
import org.springboot.causeconnect.entities.Volunteer;
import org.springboot.causeconnect.repository.OTPRepository;
import org.springboot.causeconnect.utilities.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    @Qualifier("taskScheduler")
    private TaskScheduler taskScheduler;

    public String generateOTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder(6);
        String characters = "0123456789";

        for (int i = 0; i < 6; i++) {
            otp.append(characters.charAt(random.nextInt(characters.length())));
        }

        return otp.toString();
    }

    public void sendNgoOTP(String email, String otp) throws ApiException {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Verify Email");
            String emailBody = String.format("Your OTP for NGO email verification is: %s\n\nBest regards,\nThe Cause Connect Team", otp);
            message.setText(emailBody);
            mailSender.send(message);
        }catch (Exception e) {
            throw new ApiException(e.getMessage(), 500);
        }
    }

    public void sendVolunteerOTP(String email, String otp) throws ApiException {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Verify Email");
            String emailBody = String.format("Your OTP for Volunteer email verification is: %s\n\nBest regards,\nThe Cause Connect Team", otp);
            message.setText(emailBody);
            mailSender.send(message);
        }catch (Exception e) {
            throw new ApiException(e.getMessage(), 500);
        }
    }

    public void sendNgoApprovalEmail(String recipientEmail, String recipientName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Approval of Your NGO Account");

        String emailBody = String.format(
                "Dear %s,\n\n" +
                        "We are pleased to inform you that your NGO account on the Cause Connect platform has been approved. " +
                        "Our team has thoroughly reviewed the documents you provided, and they have been successfully verified by the admin team.\n\n" +
                        "Thank you for taking the initiative to join our platform. Your efforts to contribute to society and make a meaningful impact are highly commendable. " +
                        "By being a part of Cause Connect, you are joining a network of organizations and individuals dedicated to driving positive change.\n\n" +
                        "Should you have any questions or require assistance, please do not hesitate to contact us.\n\n" +
                        "Thank you once again for your valuable contribution to society.\n\n" +
                        "Best regards,\n" +
                        "The Cause Connect Team",
                recipientName
        );

        message.setText(emailBody);
        mailSender.send(message);
    }

    public void sendNgoDisapprovalEmail(String recipientEmail, String recipientName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Disapproval of Your NGO Account");

        String emailBody = String.format(
                "Dear %s,\n\n" +
                        "We regret to inform you that your NGO account on the Cause Connect platform could not be approved. " +
                        "After thoroughly reviewing the documents you provided, our admin team has determined that they do not meet the necessary requirements for verification.\n\n" +
                        "We appreciate your interest in joining our platform and your efforts to contribute to society. However, we encourage you to review the guidelines for registration and ensure that all required documents are complete and accurate before submitting again.\n\n" +
                        "Should you have any questions, require further clarification, or wish to reapply, please do not hesitate to contact us. We are here to assist you throughout the process.\n\n" +
                        "Thank you for your understanding and your continued dedication to making a difference.\n\n" +
                        "Best regards,\n" +
                        "The Cause Connect Team",
                recipientName
        );

        message.setText(emailBody);
        mailSender.send(message);
    }

    public int generateAndSendNgoOtp(String recipientEmail) throws ApiException {
        String otp = generateOTP();
        OTP temp = new OTP();
        temp.setOTPCode(otp);
        sendNgoOTP(recipientEmail, otp);
        this.otpRepository.save(temp);

        taskScheduler.schedule(() -> {
            this.otpRepository.delete(temp);
        }, triggerContext -> {
            return new Date(System.currentTimeMillis() + 60000 * 5).toInstant(); // Execute after 5 minutes
        });
        return temp.getId();
    }

    public int generateAndSendVolunteerOtp(String recipientEmail) throws ApiException {
        String otp = generateOTP();
        OTP temp = new OTP();
        temp.setOTPCode(otp);
        sendVolunteerOTP(recipientEmail, otp);
        this.otpRepository.save(temp);

        taskScheduler.schedule(() -> {
            this.otpRepository.delete(temp);
        }, triggerContext -> {
            return new Date(System.currentTimeMillis() + 60000 * 5).toInstant(); // Execute after 5 minutes
        });

        return temp.getId();
    }

    public boolean verifyEmail(int id,String otp) throws ApiException {
        Optional<OTP> otp1=this.otpRepository.findById((long) id);
        if(otp1.isPresent()) {
            OTP otp2 = otp1.get();
            if(otp2.getOTPCode().equals(otp)) {
                this.otpRepository.delete(otp2);
                return true;
            }
            else {
                throw new ApiException("Wrong OTP", 401);
            }
        }
        throw new ApiException("Wrong Id Please generate OTP again", 401);
    }

    public void sendEventCancellationNotification(Event event, String reason) {
        List<Volunteer> volunteers = event.getVolunteerRequestList(); // Get the volunteers who applied to participate

        for (Volunteer volunteer : volunteers) {
            String to = volunteer.getEmail();
            String subject = "Cancellation of Event: " + event.getName();
            String body = buildEmailBody(event, reason, volunteer);

            sendEmail(to, subject, body);
        }
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public String createVolunteerAcceptedEmailBody(String volunteerName, String ngoName, String eventName, LocalDateTime eventDate, String eventLocation, String assignedTask) {

        // Define the correct date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm");

        // Format the date properly
        String formattedDate = (eventDate != null) ? eventDate.format(formatter) : "Date not available";

        // Construct the email body using StringBuilder
        StringBuilder body = new StringBuilder();

        body.append("Dear ").append(volunteerName).append(",\n\n")
                .append("Your request to join the event \"").append(eventName)
                .append("\" organized by ").append(ngoName).append(" has been approved.\n\n")
                .append("Below are the details of the event and your assigned task:\n")
                .append("📅 Event Name: ").append(eventName).append("\n")
                .append("🗓 Date & Time: ").append(formattedDate).append("\n")
                .append("📍 Location: ").append(eventLocation).append("\n")
                .append("📝 Assigned Task: ").append(assignedTask).append("\n\n")
                .append("We appreciate your contribution to this initiative. If you have any questions or require additional information, please do not hesitate to reach out to us.\n\n")
                .append("Thank you for your participation.\n\n")
                .append("Best regards,\n")
                .append(ngoName).append(" Team");

        return body.toString();
    }

    public String createVolunteerDeclinedEmailBody(String volunteerName, String ngoName, String eventName, String reason) {

        // Construct the email body using StringBuilder
        StringBuilder body = new StringBuilder();

        body.append("Dear ").append(volunteerName).append(",\n\n")
                .append("Thank you for your interest in participating in the event \"").append(eventName)
                .append("\" organized by ").append(ngoName).append(".\n\n")
                .append("After careful consideration, we regret to inform you that your request to join the event has been declined.\n")
                .append("Reason for decline: ").append(reason).append("\n\n")
                .append("We truly appreciate your enthusiasm and willingness to contribute. We encourage you to stay connected for future opportunities where we can collaborate.\n\n")
                .append("If you have any questions or would like to volunteer for other events, please feel free to reach out to us.\n\n")
                .append("Thank you for your understanding.\n\n")
                .append("Best regards,\n")
                .append(ngoName).append(" Team");

        return body.toString();
    }

    private String buildEmailBody(Event event, String reason, Volunteer volunteer) {
        return String.format(
                "Dear %s,\n\n" +
                        "We regret to inform you that the event, '%s,' organized by '%s,' has been canceled.\n\n" +
                        "Reason provided by the NGO: %s\n\n" +
                        "Event Details:\n" +
                        "Date: %s\n" +
                        "Location: %s, %s\n\n" +
                        "We deeply apologize for the inconvenience caused and sincerely appreciate your enthusiasm " +
                        "in volunteering for this event. We hope to see your participation in our future initiatives.\n\n" +
                        "Thank you for your understanding and support.\n\n" +
                        "Warm regards,\n" +
                        "CauseConnect Team",
                volunteer.getFullName(),
                event.getName(),
                event.getHost().getNgoName(),
                reason,
                event.getEventDate(),
                event.getAddress(),
                event.getCity()
        );
    }
}
