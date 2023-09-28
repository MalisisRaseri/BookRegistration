package mainpack;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainFrame extends JFrame implements WindowListener {
        private final BookDialogue bookDialogue = new BookDialogue(this);
        private final TableOfBooks tableOfBooks = new TableOfBooks();
        private final JTable table = new JTable(tableOfBooks);
        private final BookParam bookParameters = new BookParam();
        private final BookData bookData = new BookData();

        public MainFrame() {
            bookData.loadData();
            setTitle("Books accounting");
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            setSize(800, 600);
            setResizable(true);
            setLocationRelativeTo(null);
            addWindowListener(this);
            initTable();
            initMenu();
        }

        private void initTable () {
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane);
        }

        private void initMenu() {
            JMenuBar menuBar = new JMenuBar();
            JMenu operations = new JMenu("Operations");
            operations.setMnemonic('O');
            menuBar.add(operations);
            addMenuItemTo(operations, "Add", 'A',
                    KeyStroke.getKeyStroke('A', InputEvent.ALT_DOWN_MASK),
                    e -> algorithmIfAddBookButtonIsPushed());
            addMenuItemTo(operations, "Change", 'C',
                    KeyStroke.getKeyStroke('C', InputEvent.ALT_DOWN_MASK),
                    e -> algorithmIfChangeBookButtonIsPushed());
            addMenuItemTo(operations, "Delete", 'D',
                    KeyStroke.getKeyStroke('D', InputEvent.ALT_DOWN_MASK),
                    e -> algorithmIfRemoveBookButtonIsPushed());
            setJMenuBar(menuBar);
        }

        private void addMenuItemTo(JMenu parent, String text, char mnemonic, KeyStroke accelerator, ActionListener al) {
            JMenuItem menuItem = new JMenuItem(text, mnemonic);
            menuItem.setAccelerator(accelerator);
            menuItem.addActionListener(al);
            parent.add(menuItem);
        }

        private void algorithmIfAddBookButtonIsPushed() {
            bookDialogue.prepareForAdd();
            if (bookDialogue.showModal()) {
                setBookParameters();
                if (!BookData.getBooks().containsKey(bookDialogue.getCodeFromTextField())) {
                    BookData.getBooks().setProperty(bookDialogue.getCodeFromTextField(), String.valueOf(bookParameters));
                    tableOfBooks.insertObjectInNewRow();
                } else {
                    JOptionPane.showMessageDialog(null, "That code has another book", "WARNING", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        private void algorithmIfChangeBookButtonIsPushed() {
            if (table.getSelectedRow() == -1) return;
            bookDialogue.prepareForChange(table);
            if (bookDialogue.showModal()) {
                setBookParameters();
                BookData.getBooks().setProperty(bookDialogue.getCodeFromTextField(), String.valueOf(bookParameters));
                tableOfBooks.changeObjectInRow(table.getSelectedRow());
            }
        }

        private void setBookParameters() {
            bookDialogue.getCodeFromTextField();
            bookParameters.setIsbn(bookDialogue.getIsbnFromTextField().replaceAll("'", ""));
            bookParameters.setBookTitle(bookDialogue.getTitleFromTextField().replaceAll("'", ""));
            bookParameters.setAuthors(bookDialogue.getAuthorsFromTextField().replaceAll("'", ""));
            bookParameters.setYear(bookDialogue.getYearFromTextField().replaceAll("'", ""));
        }

        private void algorithmIfRemoveBookButtonIsPushed() {
            if (table.getSelectedRow() == -1) return;
            if (JOptionPane.showConfirmDialog(this,"Are you sure you want to delete book\n" + "with code " + tableOfBooks.getValueAt(table.getSelectedRow(), 0) + "?",
                    "Delete confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                BookData.getBooks().remove(tableOfBooks.getValueAt(table.getSelectedRow(), 0));
                tableOfBooks.deleteObjectInRow(table.getSelectedRow());
            }
        }

        @Override
        public void windowOpened(WindowEvent e) {}
        @Override
        public void windowClosing(WindowEvent e) {
            int res=JOptionPane.showConfirmDialog(null,
                    "Вы действительно хотите закрыть приложение?");
            if(res==JOptionPane.YES_OPTION){
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                bookData.storeData();
                System.exit(1);
            }
        }
        @Override
        public void windowClosed(WindowEvent e) {}
        @Override
        public void windowIconified(WindowEvent e) {}
        @Override
        public void windowDeiconified(WindowEvent e) {}
        @Override
        public void windowActivated(WindowEvent e) {}
        @Override
        public void windowDeactivated(WindowEvent e) {}
    }

