package mainpack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class BookDialogue extends JDialog {
    private final JTextField codeField;
    private final JTextField isbnField;
    private final JTextField titleField;
    private final JTextField authorsField;
    private final JTextField yearField;
    private boolean okPressed;

    TableOfBooks tableOfBooks;

    public BookDialogue(MainFrame owner) {
        super(owner, true);
        codeField = new JTextField(10);
        isbnField = new JTextField(10);
        titleField = new JTextField(80);
        authorsField = new JTextField(80);
        yearField = new JTextField(4);
        initLabelsAndFields();
        initOkCancelButtons();
        setResizable(false);
    }

    private void initLabelsAndFields() {
        JPanel controlsPane = new JPanel(null);
        controlsPane.setLayout(new BoxLayout(controlsPane, BoxLayout.Y_AXIS));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("Code: ");
        label.setLabelFor(codeField);
        panel.add(label);
        panel.add(codeField);
        controlsPane.add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel("ISBN: ");
        label.setLabelFor(isbnField);
        panel.add(label);
        panel.add(isbnField);
        controlsPane.add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel("Title: ");
        label.setLabelFor(titleField);
        panel.add(label);
        panel.add(titleField);
        controlsPane.add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel("Authors: ");
        label.setLabelFor(authorsField);
        panel.add(label);
        panel.add(authorsField);
        controlsPane.add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel("Year: ");
        label.setLabelFor(yearField);
        panel.add(label);
        panel.add(yearField);
        controlsPane.add(panel);

        add(controlsPane, BorderLayout.CENTER);
    }

    private void initOkCancelButtons() {
        JPanel panelForButtons = new JPanel();

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            okPressed = true;
            setVisible(false);
        });
        okButton.setDefaultCapable(true);
        panelForButtons.add(okButton);

        Action cancelDialogAction = new AbstractAction("Cancel") {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        };

        JButton cancelButton = new JButton(cancelDialogAction);
        panelForButtons.add(cancelButton);

        add(panelForButtons, BorderLayout.SOUTH);

        final String escape = "escape";
        getRootPane()
                .getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), escape);
        getRootPane()
                .getActionMap()
                .put(escape, cancelDialogAction);
    }

    public boolean showModal() {
        pack();
        setLocationRelativeTo(getOwner());
        if (codeField.isEnabled()) {
            codeField.requestFocusInWindow();
        } else {
            isbnField.requestFocusInWindow();
        }
        okPressed = false;
        setVisible(true);
        return okPressed;
    }

    public void prepareForAdd () {
        setTitle("New book registration");
        codeField.setText("");
        isbnField.setText("");
        titleField.setText("");
        authorsField.setText("");
        yearField.setText("");
        codeField.setEnabled(true);
    }

    public void prepareForChange (JTable table) {
        setTitle("Book parameters change");
        tableOfBooks = new TableOfBooks();
        codeField.setText(String.valueOf(tableOfBooks.getValueAt(table.getSelectedRow(), 0)));
        isbnField.setText(String.valueOf(tableOfBooks.getValueAt(table.getSelectedRow(), 1)));
        titleField.setText(String.valueOf(tableOfBooks.getValueAt(table.getSelectedRow(), 2)));
        authorsField.setText(String.valueOf(tableOfBooks.getValueAt(table.getSelectedRow(), 3)));
        yearField.setText(String.valueOf(tableOfBooks.getValueAt(table.getSelectedRow(), 4)));
        codeField.setEnabled(false);
    }

    public String getCodeFromTextField() {return codeField.getText();}
    public String getIsbnFromTextField() {return isbnField.getText();}
    public String getTitleFromTextField() {return titleField.getText();}
    public String getAuthorsFromTextField() {return authorsField.getText();}
    public String getYearFromTextField() {return yearField.getText();}
}
