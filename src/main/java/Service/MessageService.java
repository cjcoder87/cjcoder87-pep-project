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

    // public MessageService(MessageDAO messageDAO,
    //         AccountDAO accountDAO) {
    //     this.messageDAO = messageDAO;
    //     this.accountDAO = accountDAO;
    //     this.messageValidator = new MessageValidator();
    // }

    public Message addMessage(Message message) {
        if (this.messageValidator.isValidMessage(message) && !this.accountDAO.accountExists(message.getPosted_by()))
            return messageDAO.insertMessage(message);
        return null;
    }

   public List<Message> getAllMessages() {
        return this.messageDAO.getAllMessages();
    }

       public Message getMessageById(Message message) {
        return this.messageDAO.getMessageById(message.getMessage_id());
    }
}
