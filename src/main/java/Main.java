import com.google.api.services.gmail.model.Message;
import org.apache.commons.lang3.RandomStringUtils;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            GmailAPI.init();
        } catch (GeneralSecurityException gse) {
            System.err.println(">>> GSE: " + gse.getMessage());
        } catch (IOException ioe) {
            System.err.println(">>> IOE: " + ioe.getMessage());
        }

        Thread thread = new Thread(() -> {
            do {
                try {
                    List<Message> unreadPrimaryMessages = GmailAPI.listMessagesMatchingQuery("label:unread category:primary");
                    for (Message message : unreadPrimaryMessages) {
                        try {
                            MimeMessage mimeMessage = GmailAPI.getMimeMessage(message.getId());
                            String messageId = mimeMessage.getMessageID();
                            String subject = mimeMessage.getSubject();
                            String threadId = message.getThreadId();
                            Address senderAddress = mimeMessage.getFrom()[0];
                            int caseId;

                            do {
                                caseId = Integer.parseInt(RandomStringUtils.randomNumeric(8));
                            } while (!CaseHome.isCaseIdValidity(caseId));

                            CaseHome.insertCase(caseId, senderAddress.toString(), messageId, threadId, subject);

                            GmailAPI.sendReply(GmailAPI.createEmailInReply(senderAddress, messageId, subject,
                                    "Thanks for your email - \nYour Case id is\n#" + caseId),
                                    threadId);
                            GmailAPI.modifyMessage(message.getId(), null, Collections.singletonList("UNREAD"));
                        } catch (MessagingException me) {
                            System.out.println(">>> ME: " + me.getMessage());
                        }
                    }
                } catch (IOException ioe) {
                    System.out.println(">>> IOE: " + ioe.getMessage());
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ie) {
                    System.out.println(">>> IE: " + ie.getMessage());
                }
            } while (true);
        });

        thread.start();
    }
}
