package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import persistence.HighScore;

public class HighScoreWindow extends JDialog {

    private final JTable table;
    /**
     * Sets up HighScoreWindow
     * @param highScores
     * @param parent 
     */
    public HighScoreWindow(ArrayList<HighScore> highScores, JFrame parent) {
        super(parent, true);
        setLayout(new BorderLayout());
        table = new JTable(new HighScoreTableModel(highScores));
        table.setFont(new Font("Serif", Font.PLAIN, 20));
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);

        /*SORT
        TableRowSorter<TableModel> sorter =
        new TableRowSorter<TableModel>(table.getModel());
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        table.setRowSorter(sorter);*/
        add(new JScrollPane(table), BorderLayout.NORTH);
        setSize(500, 500);
        setTitle("A 10 legjobb játékos");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
