package oop.sunfun.database.enums;

public enum AccountType {
    /**
     * The Parente type within the database connection.
     */
    PARENTE("Parente"),

    /**
     * The Partecipante type within the database connection.
     */
    PARTECIPANTE("Partecipante"),

    /**
     * The Educatore type within the database connection.
     */
    EDUCATORE("Educatore"),

    /**
     * The Volontario type within the database connection.
     */
    VOLONTARIO("Volontario");

    /**
     * The string to use in database queries.
     */
    private final String textValue;

    AccountType(final String value) {
        this.textValue = value;
    }

    /**
     * Gets the string to use in database queries.
     * @return The textual value of this enum.
     */
    public String getTextValue() {
        return this.textValue;
    }
}
