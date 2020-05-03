import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class View2_GUI
{
    private JList characterList;
    private JButton updateBtn;
    private JTable charTable;

    View2_GUI()
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
        String[] leData = {"thing1", "thing2", "thing3", "thing4"};

        JPanel charListPanel = new JPanel();
        charListPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        DefaultListModel charListModel = new DefaultListModel();

        for (int i = 0; i < leData.length; i++) {
            charListModel.addElement(leData[i]);
        }

        characterList = new JList(charListModel);

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
        DefaultTableModel model = (DefaultTableModel)charTable.getModel();

        if (model.getRowCount() > 0) {
            model.removeRow(0);
        }

        model.addRow(data[0]);

        charTable.repaint();
    }

    private void initCharTable (JFrame window)
    {
        Object[] columnNames = {
            "Name",
            "Strength",
            "Stamina",
            "Cur HP",
            "Max HP",
            "Loc ID"
        };

        Object[][] data = {};

        charTable = new JTable(new DefaultTableModel(data, columnNames));
        for (int i = 0; i < charTable.getColumnModel().getColumnCount(); i++) {
            charTable.getColumnModel().getColumn(i).setPreferredWidth(100);
        }

        JPanel tblPanel = new JPanel();
        tblPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        tblPanel.setLayout(new GridLayout(0, 1));

        tblPanel.add(charTable.getTableHeader(), BorderLayout.NORTH);
        tblPanel.add(charTable, BorderLayout.CENTER);

        window.add(tblPanel, BorderLayout.SOUTH);
    }

    public static void main(String args[])
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new View2_GUI();
            }
        });
    }

}
