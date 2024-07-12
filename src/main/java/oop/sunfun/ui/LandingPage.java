package oop.sunfun.ui;

import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.behavior.CloseEvents;
import oop.sunfun.ui.forum.ForumPage;
import oop.sunfun.ui.layout.GenericPage;
import oop.sunfun.ui.layout.GridBagConstraintBuilder;

import javax.swing.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.IntStream;

public class LandingPage extends GenericPage {

    private static final String PAGE_NAME = "Landing Page";

    public LandingPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent);
        // Set to contain all the pages reachable by that user.
        final Set<GenericPage> reachablePages = new HashSet<>();
        // Add the pages that need to be shown
        // TODO: Make pages load dynamically
        // TODO: Add constraints depending on the account type
        reachablePages.add(new ForumPage(CloseEvents.EXIT_PROGRAM));
        // Calculate the sides of a rectangle for the button layout
        final double side = Math.sqrt(reachablePages.size());
        final int rows = (int) Math.floor(side);
        final int cols = (int) Math.ceil(side);
        // Add the buttons to the page
        final Iterator<GenericPage> it = reachablePages.iterator();
        IntStream.range(0, rows).forEach(r -> IntStream.range(0, cols).forEach(c -> {
            if (it.hasNext()) {
                final GenericPage page = it.next();
                final AbstractButton btnPage = new JButton(page.getTitle());
                btnPage.addActionListener(e -> this.switchPage(page));
                this.addPanelComponent(btnPage, new GridBagConstraintBuilder()
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
