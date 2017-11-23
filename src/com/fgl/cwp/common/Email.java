package com.fgl.cwp.common;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * @author Jessica Wong
 */
public class Email {

    /**
     * @param mailFrom
     * @param mailto
     * @param mailCC
     * @param subject
     * @param reportName
     * @throws MessagingException
     */
    public static void mailReport(String mailFrom, String mailto, String mailCC, String subject, String reportName) throws MessagingException {

        boolean debug = false;
        /* todo: use properties files */
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "calmta1vs01.fglcorporate.net");
        Session session = Session.getInstance(props, null);
        session.setDebug(debug);
        Message msg = new MimeMessage(session);

        InternetAddress addressFrom = new InternetAddress(mailFrom);
        msg.setFrom(addressFrom);

        InternetAddress addressTo = new InternetAddress(mailto);
        InternetAddress addressCC = new InternetAddress(mailCC);
        msg.addRecipient(Message.RecipientType.TO, addressTo);
        msg.addRecipient(Message.RecipientType.CC, addressCC);
        msg.setSubject(subject);
        MimeMultipart mp = new MimeMultipart();
        mp.setSubType("related");
        MimeBodyPart mbp1 = new MimeBodyPart();
        mbp1.setText("Report Attached");
        MimeBodyPart mbp2 = new MimeBodyPart();
        FileDataSource fds = new FileDataSource(reportName);
        mbp2.setDataHandler(new DataHandler(fds));
        mbp2.setFileName(fds.getName());
        mp.addBodyPart(mbp1);
        mp.addBodyPart(mbp2);
        msg.setContent(mp);
        msg.setSentDate(new Date());
        Transport.send(msg);
    }

    /**
     * @param mailFrom
     * @param mailto
     * @param subject
     * @param message
     * @throws MessagingException
     */
    public static void mailMessage(String mailFrom, String mailto, String subject, String message) throws MessagingException {
        boolean debug = false;
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "calmta1vs01.fglcorporate.net");
        Session session = Session.getInstance(props, null);
        session.setDebug(debug);
        Message msg = new MimeMessage(session);
        InternetAddress addressFrom = new InternetAddress(mailFrom);
        msg.setFrom(addressFrom);
        InternetAddress addressTo = new InternetAddress(mailto);
        msg.addRecipient(Message.RecipientType.TO, addressTo);
        msg.setSubject(subject);
        MimeMultipart mp = new MimeMultipart();
        mp.setSubType("related");
        MimeBodyPart mbp1 = new MimeBodyPart();
        mbp1.setText(message);
        mp.addBodyPart(mbp1);
        msg.setContent(mp);
        msg.setSentDate(new Date());
        Transport.send(msg);
    }

}