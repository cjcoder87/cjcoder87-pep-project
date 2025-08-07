package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;
import Util.MessageValidator;

public class MessageService {
    MessageDAO messageDAO;
    AccountDAO accountDAO;
    MessageValidator messageValidator;

    public MessageService() {
    }

    public MessageService(MessageDAO messageDAO,
            AccountDAO accountDAO) {
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
        this.messageValidator = new MessageValidator();
    }

    public Message addMessage(Message message) {
        if (this.messageValidator.isValidMessage(message) && !accountDAO.accountExists(message.getPosted_by()))
            return messageDAO.insertMessage(message);
        return null;
    }
}
