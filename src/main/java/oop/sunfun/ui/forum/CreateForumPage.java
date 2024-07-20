package oop.sunfun.ui.forum;

import oop.sunfun.database.dao.ForumDAO;
import oop.sunfun.database.data.forum.CategoryData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.util.Pair;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.pages.FormPage;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import java.awt.Component;
import java.util.LinkedHashMap;
import java.util.Map;

public final class CreateForumPage extends FormPage {
    /**
     * The title of the page.
     */
    private static final String PAGE_NAME = "Create A Post";

    /**
     * Components of the form used by the FormPage class.
     */
    private static final Map<Component, Pair<JComponent, Integer>> FORM_COMPONENTS;

    private static final JComponent TXT_TITLE;
    private static final JComponent TXT_DESCRIPTION;
    private static final JComboBox<String> COMBO_CATEGORY;

    static {
        FORM_COMPONENTS = new LinkedHashMap<>();
        TXT_TITLE = new JTextField();
        TXT_DESCRIPTION = new JTextArea();
        COMBO_CATEGORY = new JComboBox<>();
        FORM_COMPONENTS.put(new JLabel("Titolo del post:"), new Pair<>(TXT_TITLE, 50));
        FORM_COMPONENTS.put(new JLabel("Descrizione:"), new Pair<>(TXT_DESCRIPTION, 10000));
        FORM_COMPONENTS.put(new JLabel("Categoria:"), new Pair<>(COMBO_CATEGORY, 0));
    }

    public CreateForumPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent, FORM_COMPONENTS,
                () -> new ForumPage(CloseEvents.EXIT_PROGRAM, account),
                () -> ForumDAO.addNewDiscussion(((JTextComponent) TXT_TITLE).getText(),
                        ((JTextComponent) TXT_DESCRIPTION).getText(),
                        new CategoryData((String) COMBO_CATEGORY.getSelectedItem()), account.email()));
        // Reload categories just in case
        ForumDAO.getAllCategories().forEach(c -> {
            COMBO_CATEGORY.addItem(c.name());
        });
        // Finalize the window
        this.buildWindow();
    }
}
