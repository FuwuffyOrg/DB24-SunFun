package oop.sunfun.database.data.enums;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;

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

    /**
     * Gets the string to use in database queries.
     * @param type The type of the parent in string type.
     * @return The textual value of this enum.
     */
    public static ParentType getFromString(final String type) {
        final Optional<ParentType> accType = EnumSet.allOf(ParentType.class)
                .stream()
                .filter(p -> Objects.equals(p.getTextValue(), type))
                .findFirst();
        if (accType.isEmpty()) {
            throw new IllegalStateException("The inserted parent type is not valid!");
        }
        return accType.get();
    }
}
