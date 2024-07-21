package oop.sunfun.ui;

import oop.sunfun.database.dao.GroupDAO;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.database.data.login.AccountType;
import oop.sunfun.ui.activity.ActivityPage;
import oop.sunfun.ui.activity.GroupActivityManagementPage;
import oop.sunfun.ui.activity.GroupActivityViewPage;
import oop.sunfun.ui.activity.GroupRollcallPage;
import oop.sunfun.ui.admin.AllergiesPage;
import oop.sunfun.ui.admin.EducatorPage;
import oop.sunfun.ui.admin.GroupManagementPage;
import oop.sunfun.ui.admin.GroupPage;
import oop.sunfun.ui.admin.PeriodPage;
import oop.sunfun.ui.admin.StatisticsPage;
import oop.sunfun.ui.parent.ManageParticipantPage;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.forum.ForumPage;
import oop.sunfun.ui.util.pages.GenericPage;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class LandingPage extends GenericPage {
    /**
     * The title of the page.
     */
    private static final String PAGE_NAME = "Landing Page";

    /**
     * The account data on the page.
     */
    private final AccountData accountData;

    /**
     * Creates a landing page with the different levels of permission based on the account.
     * @param closeEvent The event that happens when the page closes.
     * @param account The account that called the page.
     */
    public LandingPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent);
        this.accountData = account;
        // Combo box for the voluntary to choose the group
        final JComboBox<String> comboGroup = new JComboBox<>();
        GroupDAO.getAllGroups().forEach(g -> comboGroup.addItem(g.name()));
        // Set to contain all the pages reachable by that user.
        final Map<String, Supplier<GenericPage>> reachablePages = new HashMap<>();
        // Add the pages that need to be shown
        reachablePages.put("Forum", () -> new ForumPage(CloseEvents.EXIT_PROGRAM, this.accountData));
        switch (account.type()) {
            case AccountType.PARENTE:
                reachablePages.put("Gestione Partecipanti", () -> new ManageParticipantPage(CloseEvents.EXIT_PROGRAM,
                        this.accountData));
                break;
            case AccountType.EDUCATORE:
                final Optional<String> edGroup = GroupDAO.getEducatorGroup(account.codFisc());
                edGroup.ifPresent(g -> {
                    reachablePages.put("Appello del gruppo", () -> new GroupRollcallPage(CloseEvents.EXIT_PROGRAM,
                            this.accountData, g, Optional.empty()));
                    reachablePages.put("Attivita del gruppo", () -> new GroupActivityViewPage(CloseEvents.EXIT_PROGRAM,
                            this.accountData, g, Optional.empty()));
                });
                reachablePages.put("Statistiche", () -> new StatisticsPage(CloseEvents.EXIT_PROGRAM,
                        this.accountData));
                reachablePages.put("Gestione Periodi", () -> new PeriodPage(CloseEvents.EXIT_PROGRAM,
                        this.accountData));
                reachablePages.put("Gestione Allergie", () -> new AllergiesPage(CloseEvents.EXIT_PROGRAM,
                        this.accountData));
                reachablePages.put("Gestione Attivita", () -> new ActivityPage(CloseEvents.EXIT_PROGRAM,
                        this.accountData));
                reachablePages.put("Gestione Educatori", () -> new EducatorPage(CloseEvents.EXIT_PROGRAM,
                        this.accountData));
                reachablePages.put("Creazione Gruppi", () -> new GroupPage(CloseEvents.EXIT_PROGRAM, this.accountData));
                reachablePages.put("Gestione attivita gruppi", () -> new GroupActivityManagementPage(
                        CloseEvents.EXIT_PROGRAM, this.accountData, Optional.empty()));
                reachablePages.put("Popolazione Gruppi", () -> new GroupManagementPage(CloseEvents.EXIT_PROGRAM,
                        this.accountData));
                break;
            case AccountType.VOLONTARIO:
                reachablePages.put("Appello del gruppo", () -> new GroupRollcallPage(CloseEvents.EXIT_PROGRAM, account,
                        (String) comboGroup.getSelectedItem(), Optional.empty()));
                reachablePages.put("Attivita del gruppo", () -> new GroupActivityViewPage(CloseEvents.EXIT_PROGRAM,
                        account, (String) comboGroup.getSelectedItem(), Optional.empty()));
                reachablePages.put("Gestione Allergie", () -> new AllergiesPage(CloseEvents.EXIT_PROGRAM,
                        this.accountData));
                reachablePages.put("Gestione Attivita", () -> new ActivityPage(CloseEvents.EXIT_PROGRAM,
                        this.accountData));
                break;
            case AccountType.PARTECIPANTE:
                final Optional<String> partGroup = GroupDAO.getParticipantGroup(account.codFisc());
                partGroup.ifPresent(g -> reachablePages.put("Attivita del gruppo", () -> new GroupActivityViewPage(
                        CloseEvents.EXIT_PROGRAM, this.accountData, g, Optional.empty())));
                break;
            default:
                throw new IllegalStateException("The account type is not valid!");
        }
        // Calculate the sides of a rectangle for the button layout
        final double side = Math.sqrt(reachablePages.size());
        final int rows = (int) Math.ceil(side);
        final int cols = (int) Math.ceil(side);
        // Add the buttons to the page
        final Iterator<Entry<String, Supplier<GenericPage>>> it = reachablePages.entrySet().iterator();
        IntStream.range(0, rows).forEach(r -> IntStream.range(0, cols).forEach(c -> {
            if (it.hasNext()) {
                final Entry<String, Supplier<GenericPage>> page = it.next();
                final AbstractButton btnPage = new JButton(page.getKey());
                btnPage.addActionListener(e -> this.switchPage(page.getValue().get()));
                this.add(btnPage, new GridBagConstraintBuilder()
                                .setRow(r).setColumn(c)
                                .setMarginAll(8)
                                .setPadAll(4)
                                .setFillAll()
                                .build()
                );
            }
        }));
        if (account.type() == AccountType.VOLONTARIO) {
            this.add(comboGroup, new GridBagConstraintBuilder()
                    .setRow(rows).setColumn(0)
                    .setWidth(cols)
                    .setFillAll()
                    .build()
            );
        }
        // Finalize the window
        this.buildWindow();
    }
}
