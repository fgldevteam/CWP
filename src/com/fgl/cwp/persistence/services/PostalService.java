/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Mar 16, 2005
 */
package com.fgl.cwp.persistence.services;


import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author bting
 */
public class PostalService {

    
    private static Log log = LogFactory.getLog(PostalService.class);
    private static PostalService instance = null;
    private static Session session = null;
    private static final String SMTP_HOST = "calmta1vs01.fglcorporate.net";
    private static final String ENVIRONMENT_CONTEXT_NAME = "java:comp/env";
    private static final String JNDI_NAME = "mail/CWPMAILSESSION";
    private static String DEFAULT_EMAIL_FROM = "do-not-reply@corp.fgl.net";

    private PostalService() {
        // empty
    }
    
    /**
     * Get a singleton instance.
     * @return PostalService
     */
    public static PostalService getInstance() {

        if (instance == null) {
            instance = new PostalService();
            
            try {
                Context initContext = new InitialContext();
                Context envContext = (Context)initContext.lookup(ENVIRONMENT_CONTEXT_NAME);
                session = (Session)envContext.lookup(JNDI_NAME);
                Properties props = session.getProperties();
                log.debug("using mail host from jndi: " + props.getProperty("mail.smtp.host"));
            } catch (NamingException ne) {
                log.error("failed to initialize mail host. using default: " + SMTP_HOST);
                Properties props = System.getProperties();
                props.put( "mail.smtp.host", SMTP_HOST );
                session = Session.getDefaultInstance( props, null );
            }
        }
        return instance;
    }

    
    /**
     * Create a new message.
     * @return Message
     */
    protected Message createMessage() {
        Message message = null;
        message = new MimeMessage(session);
        return message;
    }
    
    protected Message createMessage(String[] to, String[] cc, String from, String subject) {
        Message message = createMessage();
        try {
            if (message != null) {
                InternetAddress[] toAddresses = new InternetAddress[to.length];
                for (int i=0; i<to.length; ++i) {
                	log.error("*** To addrees: " + to[i]);
                    toAddresses[i] = new InternetAddress(to[i]);
                }                
                message.setRecipients(Message.RecipientType.TO, toAddresses);
                
                if (cc != null) {
                    InternetAddress[] ccAddresses = new InternetAddress[cc.length];
                    for (int i=0; i<cc.length; ++i) {
                        ccAddresses[i] = new InternetAddress(cc[i]);
                    }
                    message.setRecipients(Message.RecipientType.CC, ccAddresses);
                }
                
                if (StringUtils.isEmpty(from)) {
                    from = DEFAULT_EMAIL_FROM;
                }
                message.setFrom(new InternetAddress(from));
                message.setSubject(subject);
            }
        } catch(MessagingException me) {
            log.error(me.getMessage(), me);
        }
        return message;
    }

  
    /**
     * Create a Message and set recipient, from, subject and text with given
     * parameters.
     * @param to
     * @param cc
     * @param from
     * @param subject
     * @param body
     * @return
     */
    protected Message createMessage(String[] to, String[] cc, String from, String subject, String body) {
        Message message = createMessage(to, cc, from, subject);
        try {
            if (message != null) {
                message.setText(body);
            }
        } catch(MessagingException me) {
            log.error(me.getMessage(), me);
        }
        return message;
    }
    
    protected Message createMessage(String[] to, String from, String subject) {
        Message message = createMessage();
        try {
            if (message != null) {
                
                InternetAddress[] address = new InternetAddress[to.length];
                for (int i=0; i<to.length; ++i) {
                    address[i] = new InternetAddress(to[i]);
                }
                message.setRecipients(Message.RecipientType.TO, address);
                if (StringUtils.isEmpty(from)) {
                    //from = System.getProperty(ReporterConstants.KEY_EMAIL_REPORT_FROM);
                }
                message.setFrom(new InternetAddress(from));
                message.setSubject(subject);
            }
        } catch(MessagingException me) {
            log.error(me.getMessage(), me);
        }
        return message;
    }
    
    /**
     * Send the specifed message.
     * Send a message.
     * @param message
     * @return boolean - true if successfully sent to mail server, false otherwise.
     */
    protected boolean sendMessage(Message message) {
        boolean success = false;
        try {
            if (message != null) {
                log.debug("Getting ready to send message");
                if (log.isDebugEnabled()) {
                    Address[] address = message.getRecipients(Message.RecipientType.TO);
                    if (address != null) {
                        for (int i=0; i<address.length; ++i) {
                            log.debug("recipient " + i + ": " + address[i].toString());
                        }
                    }
                }
                
                message.setSentDate(new Date());
                Transport.send(message);
                log.debug("Sending mail...");
                success = true;
            }
        } catch (MessagingException me) {
            success = false;
            me.printStackTrace();
            log.error(me.getMessage(), me);
        }
        return success;
    }

    /**
     * Convenience method if only one 'to' and one 'cc'.
     * @param to
     * @param cc
     * @param from
     * @param subject
     * @param body
     * @return
     */
    public boolean sendMessage(String to, String cc, String from, String subject, String body) {
        String[] tos = null;
        String[] ccs = null;
        
        if (to != null) {
            tos = new String[] {to};
            
        }
        if (cc != null) {
            ccs = new String[] {cc};
        }
        return sendMessage(tos, ccs, from, subject, body);
    }
    
    /** 
     * Send a message to the given recipient (to).
     * @param to
     * @param cc
     * @param from
     * @param subject
     * @param body
     * @return boolean - true if successfully sent to mail server, false otherwise.
     */
    public boolean sendMessage(String[] to, String[] cc, String from, String subject, String body) {
        boolean success = false;
        try {
            Message message = createMessage(to, cc, from, subject, body);
            if (message != null) {
                success = sendMessage(message);
            }
        } catch (Exception e) {
            success = false;
            log.error(e.getMessage(), e);
        }
        return success;
    }
    
    /**
     * Convenience method if only one 'to' and one 'cc'.
     * @param to
     * @param cc
     * @param from
     * @param subject
     * @param body
     * @param file
     * @return
     */
    public boolean sendMessage(String to, String cc, String from, String subject, String body, File file) {
        String[] tos = null;
        String[] ccs = null;
        
        if (to != null) {
            tos = new String[] {to};
            
        }
        if (cc != null) {
            ccs = new String[] {cc};
        }
        return sendMessage(tos, ccs, from, subject, body, file);
    }
    
    /** 
     * Send a message to the given recipient (to).
     * @param to
     * @param from
     * @param subject
     * @param body
     * @return boolean - true if successfully sent to mail server, false otherwise.
     */
    public boolean sendMessage(String[] to, String from, String subject, String body) {
        boolean success = false;
        try {
            Message message = createMessage(to, from, subject, body);
            if (message != null) {
                success = sendMessage(message);
            }
        } catch (Exception e) {
            success = false;
            log.error(e.getMessage());
        }
        return success;
    }
    
    /**
     * Create a Message and set recipient, from, subject and text with given
     * parameters.
     * @param to
     * @param from
     * @param subject
     * @param body
     * @return Message
     */
    protected Message createMessage(String[] to, String from, String subject, String body) {
        Message message = createMessage(to, from, subject);
        try {
            if (message != null) {
                message.setText(body);
            }
        } catch(MessagingException me) {
            log.error(me.getMessage(), me);
        }
        return message;
    }

    /**
     * Send a message to the given recipient (to) with the given file attachment.
     * @param to
     * @param cc
     * @param from
     * @param subject
     * @param body
     * @param file
     * @return
     */
    public boolean sendMessage(String[] to, String[] cc, String from, String subject, String body, File file) {
        boolean success = false;
        try {
        	log.error("Message Details :: to : " + to.toString() + ", from : " + from + ", body : " + body);
            Message message = createMessage(to, cc, from, subject);
            if (message != null) {            
                BodyPart bodyPart = new MimeBodyPart();
                bodyPart.setText(body);
                
                Multipart multipart = new MimeMultipart();
                
                // Add the text message
                multipart.addBodyPart(bodyPart);
                
                // Create the attachment
                bodyPart = new MimeBodyPart();
                DataSource ds = new FileDataSource(file);
                bodyPart.setDataHandler(new DataHandler(ds));
                bodyPart.setFileName(file.getName());
                
                // Add the attachment
                multipart.addBodyPart(bodyPart);
                
                message.setContent(multipart);
                success = sendMessage(message);
            }
        } catch (Exception e) {
            success = false;
            log.error(e.getMessage(), e);
        }
        return success;
    }
    
    /**
     * Send a message to the given recipient (to) with the given file attachment.
     * @param to
     * @param from
     * @param subject
     * @param body
     * @param file
     * @return
     */
    public boolean sendMessage(String[] to, String from, String subject, String body, File file) {
        boolean success = false;
        try {
            Message message = createMessage(to, from, subject);
            if (message != null) {            
                BodyPart bodyPart = new MimeBodyPart();
                bodyPart.setText(body);
                
                Multipart multipart = new MimeMultipart();
                
                // Add the text message
                multipart.addBodyPart(bodyPart);
                
                // Create the attachment
                bodyPart = new MimeBodyPart();
                DataSource ds = new FileDataSource(file);
                bodyPart.setDataHandler(new DataHandler(ds));
                bodyPart.setFileName(file.getName());
                
                // Add the attachment
                multipart.addBodyPart(bodyPart);
                
                message.setContent(multipart);
                success = sendMessage(message);
            }
        } catch (Exception e) {
            success = false;
            log.error(e.getMessage());
        }
        return success;
    }
}