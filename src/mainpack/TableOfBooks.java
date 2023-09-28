package mainpack;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.Set;

public class TableOfBooks implements TableModel {
    private static final String[] COLUMN_HEADERS = new String[]{"CODE", "ISBN", "TITLE", "AUTHORS", "YEAR"};
    private final Set<TableModelListener> modelListeners = new HashSet<>();

    public void insertObjectInNewRow () {
        int rowIndex = BookData.getBooks().size() - 1;
        fireTableModelEvent(rowIndex, TableModelEvent.INSERT);
    }

    public void changeObjectInRow (int index) {
        fireTableModelEvent(index, TableModelEvent.UPDATE);
    }

    public void deleteObjectInRow (int index) {
        BookData.getBooks().remove(index);
        fireTableModelEvent(index, TableModelEvent.DELETE);
    }

    private void fireTableModelEvent(int rowIndex, int eventType) {
        TableModelEvent tableModelEvent = new TableModelEvent(this, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, eventType);
        for (TableModelListener listener : modelListeners) {listener.tableChanged(tableModelEvent);}
    }

    @Override
    public int getColumnCount() {return COLUMN_HEADERS.length;}
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4: return String.class;
        }
        throw new IllegalArgumentException("unknown columnIndex");
    }
    @Override
    public String getColumnName(int columnIndex) {return COLUMN_HEADERS[columnIndex];}
    @Override
    public int getRowCount() {return BookData.getBooks().size();}
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[][] arrayData = new String[BookData.getBooks().size()][];
        int k = 0;
        for (Object i : BookData.getBooks().keySet()) {
            BookParam bookParam = new BookParam();
            String [] eachBookParameter = ((String)BookData.getBooks().get(i)).split("'");
            for (int j = 1; j < eachBookParameter.length; j = j + 2) {
                if      (j == 1) bookParam.setIsbn(eachBookParameter[j]);
                else if (j == 3) bookParam.setBookTitle(eachBookParameter[j]);
                else if (j == 5) bookParam.setAuthors(eachBookParameter[j]);
                else if (j == 7) bookParam.setYear(eachBookParameter[j]);
            }
            arrayData[k] = new String[]{(String) i, bookParam.getIsbn(), bookParam.getBookTitle(), bookParam.getAuthors(), bookParam.getYear()};
            k++;
        }
        switch (columnIndex) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4: return arrayData[rowIndex][columnIndex];
        }
        throw new IllegalArgumentException("unknown columnIndex");
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {return false;}
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {/* Nothing to do, since isCellEditable is always false. */}
    @Override
    public void addTableModelListener(TableModelListener listener) {modelListeners.add(listener);}
    @Override
    public void removeTableModelListener(TableModelListener listener) {modelListeners.remove(listener);}
}
