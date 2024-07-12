package oop.sunfun.database.data.enums;

public enum ParentType {
    /**
     * The Padre type within the database connection.
     */
    PADRE("Padre"),

    /**
     * The Madre type within the database connection.
     */
    MADRE("Madre"),

    /**
     * The Nonno type within the database connection.
     */
    NONNO("Nonno"),

    /**
     * The Nonna type within the database connection.
     */
    NONNA("Nonna"),

    /**
     * The Fratello type within the database connection.
     */
    FRATELLO("Fratello"),

    /**
     * The Sorella type within the database connection.
     */
    SORELLA("Sorella");

    /**
     * The string to use in database queries.
     */
    private final String textValue;

    ParentType(final String value) {
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
