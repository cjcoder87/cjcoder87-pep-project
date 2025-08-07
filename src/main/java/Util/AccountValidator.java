package Util;

import Model.Account;

public class AccountValidator {
    public AccountValidator() {

    }

    public boolean isValidRegistration(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank())
            return false;
        if (account.getPassword() == null || account.getPassword().length() < 4)
            return false;

        return true;
    }

    public boolean isValidLogin(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank())
            return false;
        if (account.getPassword() == null || account.getPassword().isBlank())
            return false;

        return true;
    }
}
