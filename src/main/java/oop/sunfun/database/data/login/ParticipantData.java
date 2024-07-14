package oop.sunfun.database.data.login;

import java.util.Date;

public record ParticipantData(String codiceFiscale, String dieta, String group, String name,
                              String surname, Date dateOfBirth) {
}
