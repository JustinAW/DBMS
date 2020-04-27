import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class Swing_GUI
{
    private JList characterList;
    private JButton updateBtn;
    private JTable characterTable;

    Swing_GUI()
    {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initCharList(window);
        initUpdateBtn(window);
        initCharTable(window);

        Object[][] data = {
            {"Adam", 0, 95, 20, 92, 100, 0}
        };

        updateCharTable(data);

        // TODO JDialog for confirmation of update
        // Just have update read from table???

        window.pack();
        window.setVisible(true);
    }

    private void initCharList (JFrame window)
    {
        JPanel charListPanel = new JPanel();
        charListPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        characterList = new JList();
        characterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        charListPanel.add(characterList);
        window.add(charListPanel, BorderLayout.NORTH);
    }

    private void initUpdateBtn (JFrame window)
    {
        JPanel btnPanel = new JPanel();
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        updateBtn = new JButton("Update");
        btnPanel.add(updateBtn);

        window.add(btnPanel, BorderLayout.WEST);
    }

    private void updateCharTable (Object[][] data)
    {
        DefaultTableModel model = (DefaultTableModel)characterTable.getModel();

        if (model.getRowCount() > 0) {
            model.removeRow(0);
        }

        model.addRow(data[0]);

        characterTable.repaint();
    }

    private void initCharTable (JFrame window)
    {
        Object[] columnNames = {
            "Name",
            "Character_ID",
            "Strength",
            "Stamina",
            "Cur_HP",
            "Max_HP",
            "Loc_ID"
        };

        Object[][] data = {};

        characterTable = new JTable(new DefaultTableModel(data, columnNames));
        characterTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        characterTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        characterTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        characterTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        characterTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        characterTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        characterTable.getColumnModel().getColumn(6).setPreferredWidth(100);

        JPanel tblPanel = new JPanel();
        tblPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        tblPanel.setLayout(new GridLayout(0, 1));

        tblPanel.add(characterTable.getTableHeader(), BorderLayout.NORTH);
        tblPanel.add(characterTable, BorderLayout.CENTER);

        window.add(tblPanel, BorderLayout.SOUTH);
    }

    public static void main(String args[])
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Swing_GUI();
            }
        });
    }

}
