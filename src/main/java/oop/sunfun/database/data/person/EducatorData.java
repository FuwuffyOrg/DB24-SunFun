package oop.sunfun.database.data.person;

import java.util.Date;
import java.util.Optional;

public record EducatorData(String codiceFiscale, String name, String surname, String accountEmail, Date dateOfBirth, String phoneNumber,
                           Optional<String> group) {
}
