package oop.sunfun.database.data.login;

import oop.sunfun.database.data.enums.AccountType;

import java.util.Map;

public class AccountData {
    private final String email;
    private final AccountType tipologia;

    public AccountData(final Map<String, Object> sqlData) {
        this.email = (String) sqlData.get("email");
        this.tipologia = AccountType.getFromString((String) sqlData.get("tipologia"));
    }

    public String getEmail() {
        return this.email;
    }

    public AccountType getType() {
        return this.tipologia;
    }
}
