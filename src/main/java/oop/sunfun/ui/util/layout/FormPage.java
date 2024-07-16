package oop.sunfun.ui.util.layout;

import oop.sunfun.ui.util.Pair;
import oop.sunfun.ui.util.behavior.CloseEvents;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public abstract class FormPage extends GenericPage {

    private static final int MIN_INPUT_SIZE = 1;

    private final Map<JComponent, Integer> inputs;

    /**
     * Creates a page that is also a form.
     * @param title The title of the page.
     * @param closeEvent The event that happens when you click the X button.
     * @param rowOffset The offset in case you want to add more things to the form.
     * @param components The components to add to the page in the form <Label, Pair<Component, SizeLimit>>
     * @param returnPage The page that it redirects you to when you try to go back.
     * @param confirmEvent The event that happens when you confirm the form.
     */
    public FormPage(final String title, final CloseEvents closeEvent, final int rowOffset,
                    final Map<Component, Pair<JComponent, Integer>> components, final Supplier<GenericPage> returnPage,
                    final Runnable confirmEvent) {
        super(title, closeEvent);
        this.inputs = new HashMap<>();
        // Add the various inputs to the page
        final List<Entry<Component, Pair<JComponent, Integer>>> componentList = components.entrySet().stream().toList();
        IntStream.range(0, components.size()).forEach(i -> {
            this.inputs.put(componentList.get(i).getValue().x(), componentList.get(i).getValue().y());
            this.add(componentList.get(i).getKey(), new GridBagConstraintBuilder()
                    .setRow(i + rowOffset).setColumn(0)
                    .setFillAll()
                    .build()
            );
            this.add(componentList.get(i).getValue().x(), new GridBagConstraintBuilder()
                    .setRow(i + rowOffset).setColumn(1)
                    .setFillAll()
                    .build()
            );
        });
        // Create the buttons and their events
        final AbstractButton btnReturn = new JButton("Torna indietro");
        final AbstractButton btnConfirm = new JButton("Conferma form");
        btnReturn.addActionListener(e -> this.switchPage(returnPage.get()));
        btnConfirm.addActionListener(e -> {
            if (this.isDataValid()) {
                confirmEvent.run();
                this.switchPage(returnPage.get());
            }
        });
        // Add the buttons
        this.add(btnReturn, new GridBagConstraintBuilder()
                .setRow(components.size() + rowOffset).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(btnConfirm, new GridBagConstraintBuilder()
                .setRow(components.size() + rowOffset).setColumn(1)
                .setFillAll()
                .build()
        );
    }

    /**
     * Creates a page that is also a form.
     * @param title The title of the page.
     * @param closeEvent The event that happens when you click the X button.
     * @param components The components to add to the page in the form <Label, Pair<Component, SizeLimit>>
     * @param returnPage The page that it redirects you to when you try to go back.
     * @param confirmEvent The event that happens when you confirm the form.
     */
    public FormPage(final String title, final CloseEvents closeEvent,
                    final Map<Component, Pair<JComponent, Integer>> components, final Supplier<GenericPage> returnPage,
                    final Runnable confirmEvent) {
        this(title, closeEvent, 0, components, returnPage, confirmEvent);
    }

    private boolean isDataValid() {
        this.resetHighlights();
        for (final Entry<JComponent, Integer> input : this.inputs.entrySet()) {
            if (input.getKey() instanceof JTextComponent textInput) {
                if (textInput.getText().length() < MIN_INPUT_SIZE || textInput.getText().length() > input.getValue()) {
                    highlightTextComponent(textInput);
                    return false;
                }
            }
        }
        return true;
    }
}
