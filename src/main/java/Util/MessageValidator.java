package Util;

public class MessageValidator {

    public boolean isValidMessage(String messageText) {
        String message_body = messageText;
        return message_body != null && !message_body.isBlank() && message_body.length() <= 255;
    }
}
