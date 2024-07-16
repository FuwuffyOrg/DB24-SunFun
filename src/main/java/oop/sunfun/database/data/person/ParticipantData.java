package oop.sunfun.database.data.person;

import java.util.Date;
import java.util.Optional;

public record ParticipantData(String codiceFiscale, String accountEmail, Optional<String> dieta, Optional<String> group,
                              String name, String surname, Date dateOfBirth) {
}
