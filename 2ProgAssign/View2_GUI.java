import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class View2_GUI
{
    private JList<Object> characterList;
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



        int n = JOptionPane.showConfirmDialog(
            window,
            "Are you sure you want to update the database?",
            "Confirm choice",
            JOptionPane.YES_NO_OPTION);



        // TODO JDialog for confirmation of update
        // Just have update read from table???

        window.pack();
        window.setVisible(true);
    }

    private void initCharList (JFrame window)
    {
        String[] leData = {
            "thing1",
            "thing2",
            "thing3",
            "thing4",
            "thing5",
            "thing6",
            "thing7",
            "thing8",
            "thing9",
        };

        DefaultListModel<Object> charListModel = new DefaultListModel<Object>();
        for (int i = 0; i < leData.length; i++) {
            charListModel.addElement(leData[i]);
        }

        characterList = new JList<Object>(charListModel);
        characterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(characterList);

        window.add(scrollPane, BorderLayout.NORTH);
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
            "name",
            "strength",
            "stamina",
            "curHP",
            "maxHP",
            "Loc_id"
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
