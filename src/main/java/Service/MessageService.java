package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;
import Util.MessageValidator;
import java.util.List;

public class MessageService {
    MessageDAO messageDAO;
    AccountDAO accountDAO;
    MessageValidator messageValidator;

    public MessageService() {
        this.messageDAO = new MessageDAO();
        this.accountDAO = new AccountDAO();
        this.messageValidator = new MessageValidator();
    }

    public Message addMessage(Message message) {
        if (this.messageValidator.isValidMessage(message.getMessage_text()) && !this.accountDAO.accountExists(message.getPosted_by()))
            return messageDAO.insertMessage(message);
        return null;
    }

    public List<Message> getAllMessages() {
        return this.messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId) {
        return this.messageDAO.getMessageById(messageId);
    }

    public Message deleteMessageById(int messageId) {
        return this.messageDAO.deleteMessageById(messageId);
    }

    public Message updateMessageTextById(int messageID, String newMessageBody) {
        if (this.messageValidator.isValidMessage(newMessageBody)) {
            return null;
        }

        return this.messageDAO.updateMessageTextById(messageID, newMessageBody);
    }
}
