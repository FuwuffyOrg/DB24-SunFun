package oop.sunfun.database.data.enums;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;

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

    /**
     * Gets the string to use in database queries.
     * @return The textual value of this enum.
     */
    public static AccountType getFromString(final String type) {
        final Optional<AccountType> accType = EnumSet.allOf(AccountType.class)
                .stream()
                .filter(p -> Objects.equals(p.getTextValue(), type))
                .findFirst();
        if (accType.isEmpty()) {
            throw new IllegalStateException("The inserted account type is not valid!");
        }
        return accType.get();
    }
}
