package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO;
    
    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

// add account checks for reuirements of username before calling DAO to add account
    public Account addAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank())
            return null;
        if (account.getPassword() == null || account.getPassword().length() < 4)
            return null;
        if (accountDAO.usernameExists(account.getUsername()))
            return null;

        return this.accountDAO.insertAccount(account);
    }
}
