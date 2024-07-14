package oop.sunfun.ui.forum;

import oop.sunfun.database.dao.ForumDAO;
import oop.sunfun.database.data.forum.CategoryData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.layout.GenericPage;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import java.awt.Component;

public final class CreateForumPage extends GenericPage {

    private static final String PAGE_NAME = "Create A Post";

    private final AccountData accountData;

    private final JTextComponent txtTitle;

    private final JComboBox<String> comboCategoryType;

    private final JTextComponent txtDescription;

    public CreateForumPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent);
        this.accountData = account;
        // Create the components needed for the page
        final Component lblTitle = new JLabel("Titolo: ");
        final Component lblCategoria = new JLabel("Categoria: ");
        this.txtTitle = new JTextField();
        this.txtDescription = new JTextArea();
        this.comboCategoryType = new JComboBox<>();
        final AbstractButton btnCreatePost = new JButton("Crea il post");
        final AbstractButton btnForum = new JButton("Torna al forum");
        // Add the categories
        ForumDAO.getAllCategories().forEach(c -> {
            this.comboCategoryType.addItem(c.name());
        });
        // Add the components
        this.add(lblTitle, new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(txtTitle, new GridBagConstraintBuilder()
                .setRow(0).setColumn(1)
                .setFillAll()
                .build()
        );
        this.add(lblCategoria, new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(comboCategoryType, new GridBagConstraintBuilder()
                .setRow(1).setColumn(1)
                .setFillAll()
                .build()
        );
        this.add(txtDescription, new GridBagConstraintBuilder()
                .setRow(2).setColumn(0)
                .setWidth(2).setHeight(2)
                .setWeightRow(0.2d)
                .setFillAll()
                .build()
        );
        this.add(btnCreatePost, new GridBagConstraintBuilder()
                .setRow(4).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(btnForum, new GridBagConstraintBuilder()
                .setRow(4).setColumn(1)
                .setFillAll()
                .build()
        );
        // Handle the events
        btnForum.addActionListener(e -> this.switchPage(new ForumPage(CloseEvents.EXIT_PROGRAM, this.accountData)));
        btnCreatePost.addActionListener(e -> {
            if (isDataValid()) {
                ForumDAO.addNewDiscussion(txtTitle.getText(), txtDescription.getText(),
                        new CategoryData((String) comboCategoryType.getSelectedItem()), accountData.email());
                this.switchPage(new ForumPage(CloseEvents.EXIT_PROGRAM, this.accountData));
            }
        });
        // Finalize the window
        this.buildWindow();
    }

    private boolean isDataValid() {
        final int minSize = 4;
        final int titleLengthLimit = 50;
        final int descriptionLengthLimit = 10000;
        this.resetHighlights();
        if (this.txtTitle.getText().length() > titleLengthLimit || this.txtTitle.getText().length() < minSize) {
            GenericPage.highlightTextComponent(this.txtTitle);
            return false;
        }
        if (this.txtDescription.getText().length() > descriptionLengthLimit
                || this.txtDescription.getText().length() < minSize) {
            GenericPage.highlightTextComponent(this.txtDescription);
            return false;
        }
        return true;
    }
}
