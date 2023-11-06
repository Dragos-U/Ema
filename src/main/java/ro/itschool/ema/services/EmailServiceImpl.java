package ro.itschool.ema.services;

import ro.itschool.ema.models.dtos.ParticipantDTO;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {

    public static final String SUPPORT_EMAIL = "support@ema.com";
    public static final String APP_NAME = "EmaWebApp";
    private static final Logger logger = LogManager.getLogger(EmailService.class);

    @Value("${app.sendgrid.email}")
    private String fromEmail;

    @Value("${app.sendgrid.templateId}")
    private String templateId;

    @Autowired
    SendGrid sendGrid;

    @Override
    public void sendEmail(ParticipantDTO participantDTO, String accountLink) {
        try {
            Mail mail = prepareMail(participantDTO, accountLink);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            logger.info ("  Email sent successfully with status code: " + response.getStatusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Mail prepareMail(ParticipantDTO participantDTO, String accountLink) {
        Email from = new Email(fromEmail);
        String subject = "Welcome to the " + APP_NAME + ". Your account was created.";

        Personalization personalization = new Personalization();
        personalization.addDynamicTemplateData("firstName", participantDTO.getName());
        personalization.addDynamicTemplateData("appName", APP_NAME);
        personalization.addDynamicTemplateData("supportEmail", SUPPORT_EMAIL);
        personalization.addDynamicTemplateData("accountLink", accountLink);
        personalization.addTo(new Email(participantDTO.getEmail()));

        Mail email = new Mail();
        email.setTemplateId(templateId);
        email.addPersonalization(personalization);
        email.setFrom(from);
        email.setSubject(subject);
        return email;
    }
}
