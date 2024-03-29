package com.example.carrental.Services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import org.apache.commons.codec.binary.Base64;

import javax.activation.DataHandler;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
//import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Set;
import javax.activation.FileDataSource;
import javax.activation.DataSource;
import javax.mail.internet.MimeMultipart;


import static com.google.api.services.gmail.GmailScopes.GMAIL_SEND;
import static javax.mail.Message.RecipientType.TO;


//email : prorent9@gmail.com
//pass: sedq07k1
public class MailService {
    private static final String TEST_EMAIL = "prorent9@gmail.com";
    private final Gmail service;

    public MailService() throws Exception {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        service = new Gmail.Builder(httpTransport, jsonFactory, getCredentials(httpTransport, jsonFactory))
                .setApplicationName("Test Mailer")
                .build();
    }

    private static Credential getCredentials(final NetHttpTransport httpTransport, GsonFactory jsonFactory)
            throws IOException {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(MailService.class.getResourceAsStream("/client_secret_993358695920-53aubragi3j726vjacjg4033t5r7cfmo.apps.googleusercontent.com.json")));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, Set.of(GMAIL_SEND))
                .setDataStoreFactory(new FileDataStoreFactory(Paths.get("tokens").toFile()))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public void sendMail(int invoiceId, String clientMail) throws Exception {

        String filePath = "invoice" + invoiceId + ".pdf";

// Creare unei părți a mesajului pentru fișierul atașat
        MimeBodyPart pdfAttachment = new MimeBodyPart();
        DataSource source = (DataSource) new FileDataSource(filePath);
        pdfAttachment.setDataHandler(new DataHandler((javax.activation.DataSource) source));
        pdfAttachment.setFileName(filePath);

        // Creare un multipart pentru a combina textul mesajului și fișierul atașat
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(pdfAttachment);

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Mai jos gasiti factura dvs.:");
        multipart.addBodyPart(messageBodyPart);

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(TEST_EMAIL));
        email.addRecipient(TO, new InternetAddress(clientMail));
        email.setSubject("CarRent Invoice");
//        email.setText(message);
        email.setContent(multipart);


        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
        Message msg = new Message();
        msg.setRaw(encodedEmail);

        try {
            msg = service.users().messages().send("me", msg).execute();
            System.out.println("Message id: " + msg.getId());
            System.out.println(msg.toPrettyString());
        } catch (GoogleJsonResponseException e) {
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 403) {
                System.err.println("Unable to send message: " + e.getDetails());
            } else {
                throw e;
            }
        }
    }

//    public static void main(String[] args) throws Exception {
//        new MailService().sendMail("A new message", """
//                Dear reader,
//
//                Hello world.
//
//                Best regards,
//                myself
//                """);
//    }

}
