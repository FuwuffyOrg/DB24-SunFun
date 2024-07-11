package oop.sunfun.database.enums;

public enum ParentType {
    PADRE("Padre"),
    MADRE("Madre"),
    NONNO("Nonno"),
    NONNA("Nonna"),
    ZIO("Zio"),
    ZIA("Zia"),
    FRATELLO("Fratello"),
    SORELLA("Sorella");

    private final String textValue;

    ParentType(final String value) {
        this.textValue = value;
    }

    public String getTextValue() {
        return this.textValue;
    }
}
