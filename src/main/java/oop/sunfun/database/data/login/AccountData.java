package oop.sunfun.database.data.login;

public record AccountData(String email, AccountType type) {

    private static final String EMAIL_SQL = "email";
    private static final String TIPOLOGIA_SQL = "tipologia";
}
