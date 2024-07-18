package oop.sunfun.database.dao;

import oop.sunfun.database.data.person.ParentData;
import oop.sunfun.database.data.person.ParentType;
import oop.sunfun.database.data.person.ParticipantData;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ParentDAO extends AbstractDAO {
    private static final Logger LOGGER = Logger.getLogger(ParentDAO.class.getName());

    private static final String GET_ALL_PARENTS = "SELECT * FROM `parente`";

    private static final String CREATE_PARENTE = "INSERT INTO `parente`(`codice_fiscale`, `fk_account`, "
            + "`nome`, `cognome`, `cellulare`, `grado_di_parentela`) VALUES (?,?,?,?,?,?)";

    private static final String CREATE_PARTICIPANT = "INSERT INTO `partecipante`(`codice_fiscale`, `fk_account`, "
            + "`fk_dieta`, `fk_gruppo`, `nome`, `cognome`, `data_di_nascita`) VALUES (?,?,?,?,?,?,?)";

    private static final String GET_ALL_PARTICIPANTS_FROM_PARENT = "SELECT p.codice_fiscale, d.email, p.fk_dieta, "
            + "p.fk_gruppo, p.nome, p.cognome, p.data_di_nascita, p.fk_account FROM partecipante p JOIN account_data d "
            + "ON p.codice_fiscale = d.codice_fiscale JOIN ritiro r ON p.codice_fiscale = r.fk_partecipante WHERE "
            + "r.fk_parente=?";

    private static final String ADD_RITIRO_PARENTE = "INSERT INTO `ritiro`(`fk_parente`, `fk_partecipante`) "
            + "VALUES (?,?)";

    private static final String UPDATE_PARTICIPANT_DIET = "UPDATE `partecipante` SET `fk_dieta`=? WHERE "
            + "`partecipante`.`codice_fiscale`=?";

    public static Set<ParentData> getAllParents() {
        final Set<ParentData> parents = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_ALL_PARENTS);
            for (final Map<String, Object> parent : queryData) {
                final String codiceFiscale = (String) parent.get("codice_fiscale");
                final String name = (String) parent.get("nome");
                final String surname = (String) parent.get("cognome");
                final String phone = (String) parent.get("cellulare");
                final ParentType parentType = ParentType.getFromString((String) parent.get("grado_di_parentela"));
                final String email = (String) parent.get("fk_account");
                parents.add(new ParentData(codiceFiscale, email, name, surname, phone, parentType));
            }
            // YOUR MOM GAY
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't get all the parent info", err);
            DB_CONNECTION.closeConnection();
        }
        return parents;
    }

    public static void createParent(final String codiceFiscale, final String accountEmail, final String name,
                                    final String surname, final String phoneNumber, final ParentType parentType) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_PARENTE, codiceFiscale, accountEmail, name,
                    surname, phoneNumber, parentType.getTextValue());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't create the new parent", err);
            DB_CONNECTION.closeConnection();
        }
    }

    public static void createParticipant(final ParticipantData participant) {
        try {
            DB_CONNECTION.openConnection();
            final String group = participant.group().isPresent() ? participant.group().get() : null;
            final String diet = participant.dieta().isPresent() ? participant.dieta().get() : null;
            DB_CONNECTION.setQueryData(CREATE_PARTICIPANT, participant.codiceFiscale(), participant.accountEmail(),
                    diet, group, participant.name(), participant.surname(), participant.dateOfBirth());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't create the new participant", err);
            DB_CONNECTION.closeConnection();
        }
    }

    public static Set<ParticipantData> getAllParticipantsFromParent(final String parentCodiceFiscale) {
        final Set<ParticipantData> participants = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_ALL_PARTICIPANTS_FROM_PARENT,
                    parentCodiceFiscale);
            for (final Map<String, Object> participant : queryData) {
                final String codiceFiscale = (String) participant.get("codice_fiscale");
                final String dieta = (String) participant.get("fk_dieta");
                final String gruppo = (String) participant.get("fk_gruppo");
                final String name = (String) participant.get("nome");
                final String surname = (String) participant.get("cognome");
                final String email = (String) participant.get("fk_account");
                final Date dateOfBirth = (Date) participant.get("data_di_nascita");
                participants.add(new ParticipantData(codiceFiscale, email, Optional.ofNullable(dieta),
                        Optional.ofNullable(gruppo), name, surname, dateOfBirth));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch the posts for the parent " + parentCodiceFiscale,
                    err);
            DB_CONNECTION.closeConnection();
        }
        return participants;
    }

    public static void addRitiroParente(final String codFiscParente, final String codFiscPartecipante) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(ADD_RITIRO_PARENTE, codFiscParente, codFiscPartecipante);
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't add the ritiro for the participant " + codFiscPartecipante
                    + " and for the parent " + codFiscParente, err);
            DB_CONNECTION.closeConnection();
        }
    }

    public static void updateParticipantDiet(final String diet, final ParticipantData participantData) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(UPDATE_PARTICIPANT_DIET, diet, participantData.codiceFiscale());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't update the diet of " + participantData.name(), err);
            DB_CONNECTION.closeConnection();
        }
    }
}
