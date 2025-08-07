package Service;

import DAO.AccountDAO;
import Model.Account;
import Util.AccountValidator;

public class AccountService {
    AccountDAO accountDAO;
    AccountValidator accountValidator;

    public AccountService() {
        this.accountDAO = new AccountDAO();
        this.accountValidator = new AccountValidator();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
        this.accountValidator = new AccountValidator();
    }

    // add account checks for reuirements of username before calling DAO to add
    // account
    public Account addAccount(Account account) {
        if (this.accountValidator.isValidRegistration(account) && !accountDAO.usernameExists(account.getUsername()))
            return accountDAO.insertAccount(account);
        return null;
    }

    public Account loginAccount(Account account) {
        if (!this.accountValidator.isValidLogin(account))
            return null;

        return accountDAO.loginAccount(account);
    }

}
