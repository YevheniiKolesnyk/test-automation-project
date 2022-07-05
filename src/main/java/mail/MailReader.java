package mail;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.testng.Assert;
import util.PropertyLoader;
import util.Retry;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

public class MailReader {

    protected static final Logger LOGGER = LogManager.getLogger();
    private static final String PROTOCOL = "imap";
    private static final String DEFAULT_HOST = "imap.gmail.com";
    private static final String INBOX = "inbox";
    private static final String PASSWORD = PropertyLoader.loadProperty("/credentials.properties", "gmail-test-password");
    private Store store;
    private Folder openedFolder;

    public static String findLoginLinkInTheEmail(String htmlFromEmail, String cssQuery) {
        Elements elements = Jsoup.parse(htmlFromEmail).select(cssQuery);
        String url = elements.attr("href");
        Assert.assertFalse(url.isEmpty(),
                "Login URL was not fount in the email.");
        return url;
    }

    @SneakyThrows
    public String getEmailBodyBySubject(String user, String subject) {
        Message message = findMessageBySubject(user, subject);
        disconnect();
        assert message != null;
        return getTextFromMessage(message);
    }

    @SneakyThrows
    public static String getTextFromMessage(Message message) {
        String result = "";
        try {
            if (message.isMimeType("text/plain")) {
                result = message.getContent().toString();
            } else if (message.isMimeType("multipart/*")) {
                result = getHtmlMessageBody(message);
            }
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
        return result;
    }

    @SneakyThrows
    public static String getHtmlMessageBody(Message msg) {
        if (msg.isMimeType("text/plain")) {
            return msg.getContent().toString();
        } else if (msg.isMimeType("multipart/*")) {
            String result = "";
            MimeMultipart mimeMultipart = (MimeMultipart) msg.getContent();
            int count = mimeMultipart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/html")) {
                    LOGGER.debug("Message body is html");
                    result = (String) bodyPart.getContent();
                    LOGGER.debug("Message body is parsed into String");
                }
            }
            return result;
        }
        return "";
    }

    @SneakyThrows
    private static Message findMessageBySubject(String user, String subject) {
        MailReader reader = null;
        Message message;
        try {
            reader = new MailReader().connect(user);
            reader.openInboxFolder();
            message = reader.waitTillMessageReceived(user, subject);
        } catch (MessagingException e) {
            LOGGER.debug("MessagingException was catch. Details: {}",
                    e.getMessage() + Arrays.toString(e.getStackTrace()));
            if (reader != null) {
                reader.disconnect();
            }
            reader = new MailReader().connect(user);
            reader.openInboxFolder();
            message = reader.waitTillMessageReceived(user, subject);
        }
        reader.markAllEmailsAsSeen();
        return message;
    }

    @SneakyThrows
    private Message waitTillMessageReceived(String user, String subject) {
        LOGGER.debug("Start waiting for email to be received. Email subject: {}", subject);
        Retry.retry(() -> Assert.assertNotNull(findMessage(user, subject),
                "Mail message with subject <" + subject + ">, for user <" + user + "> was not found."),
                50, 2);
        return findMessage(user, subject);
    }

    @SneakyThrows
    private Message findMessage(String user, String subject) {
        for (Message message : getUnseenMessages()) {
            if (message.getSubject().contains(subject) && Arrays.toString(message.getAllRecipients()).contains(user)) {
                return message;
            }
        }
        closeOpenedFolder();
        return null;
    }

    private Message[] getUnseenMessages() throws MessagingException {
        FlagTerm unseenFlagTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
        openedFolder = openInboxFolder();
        return openedFolder.search(unseenFlagTerm);
    }

    private MailReader connect(String user) throws MessagingException {
        Properties props = new Properties();
        props.setProperty("mail.imap.ssl.enable", "true");
        Session session = Session.getInstance(props);
        Store newStore = Objects.requireNonNull(session.getStore(PROTOCOL));
        try {
            newStore.connect(DEFAULT_HOST, user, PASSWORD);
        } catch (AuthenticationFailedException e) {
            e.printStackTrace();
            Assert.fail("Less secure app access in gmail setting set as OFF (Security > Less secure app access set ON) " +
                    "or Sign-in attempt was blocked or [AUTHENTICATIONFAILED] Invalid credentials (Failure)");
        }
        this.store = newStore;
        LOGGER.debug("Connected to the mailbox of the user: {}", user);
        return this;
    }

    public void disconnect() throws MessagingException {
        if (store != null) {
            store.close();
        }
    }

    private Folder openInboxFolder() throws MessagingException {
        Folder inbox = store.getFolder(INBOX);
        assert inbox != null;
        try {
            inbox.open(Folder.READ_WRITE);
        } catch (MessagingException e) {
            LOGGER.debug("Exception during opening of the folder: {}", e.getMessage());
            e.printStackTrace();
            inbox.open(Folder.READ_WRITE);
        }
        LOGGER.debug("Folder 'inbox' is opened in the mailbox");
        return inbox;
    }

    @SneakyThrows
    private void closeOpenedFolder() {
        if (openedFolder != null) {
            openedFolder.close();
            openedFolder = null;
            LOGGER.debug("Folder was closed.");
        }
    }

    @SneakyThrows
    private void markAllEmailsAsSeen() {
        Message[] unseenMessages = getUnseenMessages();
        int unseenCount = unseenMessages.length;
        openInboxFolder().setFlags(unseenMessages, new Flags(Flags.Flag.SEEN), true);
        LOGGER.debug("All unseen messages were marked as SEEN (count = {})", unseenCount);
    }

}