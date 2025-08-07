package Util;

import Model.Message;

public class MessageValidator {

    public boolean isValidMessage(Message message) {
        String message_body = message.getMessage_text();
        return message_body != null && !message_body.isBlank() && message_body.length() <= 255;
    }
}
