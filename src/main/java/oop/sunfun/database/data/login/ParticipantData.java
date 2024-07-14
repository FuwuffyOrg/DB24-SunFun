package oop.sunfun.database.data.login;

import java.util.Date;
import java.util.Optional;

public record ParticipantData(String codiceFiscale, Optional<String> dieta, Optional<String> group, String name,
                              String surname, Date dateOfBirth) {
}
