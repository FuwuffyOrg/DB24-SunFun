package oop.sunfun.database.enums;

public enum AccountType {
    PARENTE("Parente"),
    PARTECIPANTE("Partecipante"),
    EDUCATORE("Educatore"),
    VOLONTARIO("Volontario");

    private final String textValue;

    AccountType(final String value) {
        this.textValue = value;
    }

    public String getTextValue() {
        return this.textValue;
    }
}
