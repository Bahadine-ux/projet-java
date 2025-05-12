package tn.esprit.services;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.*;

import java.io.IOException;

public class EmailSender {

    private static final String SENDGRID_API_KEY = "generi key mta3ik ou 7otha lihna";

    public static void sendEmail(String toemail, String subjectemail, String bodyemail) throws IOException {
        Email from = new Email("mail mta3ik baha@ga77af.com");
        String subject = subjectemail;
        Email to = new Email(toemail);
        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
}
