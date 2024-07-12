package oop.sunfun.database.data.login;

import oop.sunfun.database.data.enums.AccountType;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class AccountData {

    private static final String EMAIL_SQL = "email";
    private static final String TIPOLOGIA_SQL = "tipologia";
    private static final Set<String> SQL_COLUMNS;

    static {
        SQL_COLUMNS = new HashSet<>();
        SQL_COLUMNS.add(EMAIL_SQL);
        SQL_COLUMNS.add(TIPOLOGIA_SQL);
    }

    private final String email;
    private final AccountType tipologia;

    public AccountData(final Map<String, Object> sqlData) {
        if (SQL_COLUMNS.stream().anyMatch(q -> !sqlData.containsKey(q))) {
            throw new IllegalStateException("The passed sql data is not made for AccountData!");
        }
        this.email = (String) sqlData.get(EMAIL_SQL);
        this.tipologia = AccountType.getFromString((String) sqlData.get(TIPOLOGIA_SQL));
    }

    public String getEmail() {
        return this.email;
    }

    public AccountType getType() {
        return this.tipologia;
    }
}
