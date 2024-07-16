package oop.sunfun.database.data.person;

import java.util.Optional;

public record EducatorData(String codFisc, String name, String surname, String accountEmail, String phoneNumber,
                           Optional<String> group) {
}
