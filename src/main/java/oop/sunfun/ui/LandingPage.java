package oop.sunfun.ui;

import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.database.data.login.AccountType;
import oop.sunfun.ui.admin.PeriodPage;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.forum.ForumPage;
import oop.sunfun.ui.util.layout.GenericPage;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class LandingPage extends GenericPage {

    private static final String PAGE_NAME = "Landing Page";

    private final AccountData accountData;

    public LandingPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent);
        this.accountData = account;
        // Set to contain all the pages reachable by that user.
        final Map<String, Supplier<GenericPage>> reachablePages = new HashMap<>();
        // Add the pages that need to be shownp
        reachablePages.put("Forum", () -> new ForumPage(CloseEvents.EXIT_PROGRAM, this.accountData));
        // TODO: Add constraints depending on the account type
        switch (account.getType()) {
            case AccountType.PARENTE:
                break;
            case AccountType.EDUCATORE:
                reachablePages.put("Gestione Periodi", () -> new PeriodPage(CloseEvents.EXIT_PROGRAM, this.accountData));
                break;
            case AccountType.VOLONTARIO:
                break;
            case AccountType.PARTECIPANTE:
                break;
        }
        // Calculate the sides of a rectangle for the button layout
        final double side = Math.sqrt(reachablePages.size());
        final int rows = (int) Math.floor(side);
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
        // Finalize the window
        this.buildWindow();
    }
}
