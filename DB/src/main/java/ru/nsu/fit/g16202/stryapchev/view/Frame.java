package main.java.ru.nsu.fit.g16202.stryapchev.view;

import main.java.ru.nsu.fit.g16202.stryapchev.controller.Database;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.table.*;

@SuppressWarnings("Duplicates")
public class Frame extends JFrame {

    private Connection connection;
    private Database db;
    private Frame THIS = this;
    private Dimension frame_d;
    private ArrayList<String> titles;
    private ArrayList<String> queries;
    private boolean updated = false;
    private int serialNum;
    private String caseName;

    private JPanel panel;
    private JPanel queriesPane;
    private JPanel insertPane;
    private JPanel updatePane;
    private JPanel addClientWithCasePane;
    private JPanel sortedFilteredPane;
    private JPanel firmsPane;
    private JPanel lawyersPane;
    private JPanel casesPane;
    private JPanel clientsPane;
    private JPanel clients_casesPane;
    private JPanel bankAccountsPane;
    private JPanel transactionsPane;

    public Frame() {
        super();

        fillSQL();
        frame_d = new Dimension(900, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(frame_d);
        setMinimumSize(new Dimension(700, 450));
        setBackground(Color.white);
        setTitle("DB");
        this.setBackground(Color.white);
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.white);
        add(panel);

        db = new Database();
        db.setConnection();
        connection = db.getConnection();

        configureMenu();
        configureWorkingArea();
        configureQueriesTab();
        configureInsertTab();
        configureUpdateTab();
        configureAddTab();
        configureSfTab();
        configureFirmsTab();
        configureLawyersTab();
        configureCasesTab();
        configureClientsTab();
        configureClientsCasesTab();
        configureBankAccountsTab();
        configureTransactionsTab();

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void configureMenu() {
        JMenuBar jMenuBar = new JMenuBar();
        this.setJMenuBar(jMenuBar);
        JMenu fileJMenu = new JMenu("File");
        jMenuBar.add(fileJMenu);
        JMenu aboutJMenu = new JMenu("About");
        jMenuBar.add(aboutJMenu);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileJMenu.add(exitMenuItem);

        JMenuItem aboutMenuItem = new JMenuItem("About Author");
        aboutMenuItem.addActionListener(e -> openAboutDialog());
        aboutJMenu.add(aboutMenuItem);
    }

    private void configureWorkingArea() {
        final JTabbedPane tabbedPane = new JTabbedPane();
        queriesPane = new JPanel();
        insertPane = new JPanel();
        updatePane = new JPanel();
        addClientWithCasePane = new JPanel();
        sortedFilteredPane = new JPanel();
        firmsPane = new JPanel();
        lawyersPane = new JPanel();
        casesPane = new JPanel();
        clientsPane = new JPanel();
        clients_casesPane = new JPanel();
        bankAccountsPane = new JPanel();
        transactionsPane = new JPanel();
        tabbedPane.addTab("Queries", queriesPane);
        tabbedPane.addTab("Insert", insertPane);
        tabbedPane.addTab("Update", updatePane);
        tabbedPane.addTab("Add Client with Case", addClientWithCasePane);
        tabbedPane.addTab("S-F", sortedFilteredPane);
        tabbedPane.addTab("Firms", firmsPane);
        tabbedPane.addTab("Lawyers", lawyersPane);
        tabbedPane.addTab("Cases", casesPane);
        tabbedPane.addTab("Clients", clientsPane);
        tabbedPane.addTab("Clients-Cases", clients_casesPane);
        tabbedPane.addTab("Bank Accounts", bankAccountsPane);
        tabbedPane.addTab("Transactions", transactionsPane);
        panel.add(tabbedPane);
    }

    private void configureQueriesTab() {
        queriesPane.setLayout(new BorderLayout());
        JPanel verticalPane = new JPanel();
        verticalPane.setLayout(new BoxLayout(verticalPane, BoxLayout.Y_AXIS));

        JPanel lawyersNameQueryPane = new JPanel();
        lawyersNameQueryPane.setMaximumSize(new Dimension(500, 25));
        lawyersNameQueryPane.setLayout(new BoxLayout(lawyersNameQueryPane, BoxLayout.X_AXIS));
        lawyersNameQueryPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        JLabel lawyersNameLabel = new JLabel("Show Lawyer's Name for specific Case by case_id:");
        JTextField lawyersNameField = new JTextField("1", 3);
        JButton lawyersNameButton = new JButton("Perform");
        lawyersNameQueryPane.add(lawyersNameLabel);
        lawyersNameQueryPane.add(Box.createRigidArea(new Dimension(10, 0)));
        lawyersNameQueryPane.add(lawyersNameField);
        lawyersNameQueryPane.add(lawyersNameButton);

        JPanel casesCountForLawyersQueryPane = new JPanel();
        casesCountForLawyersQueryPane.setLayout(new BoxLayout(casesCountForLawyersQueryPane, BoxLayout.X_AXIS));
        casesCountForLawyersQueryPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        JLabel casesCountForLawyersLabel = new JLabel("Show How Many Cases Each Lawyer Leads:");
        JButton casesCountForLawyersButton = new JButton("Perform");
        casesCountForLawyersQueryPane.add(casesCountForLawyersLabel);
        casesCountForLawyersQueryPane.add(Box.createRigidArea(new Dimension(10, 0)));
        casesCountForLawyersQueryPane.add(casesCountForLawyersButton);

        JPanel clientsInCasesQueryPane = new JPanel();
        clientsInCasesQueryPane.setLayout(new BoxLayout(clientsInCasesQueryPane, BoxLayout.X_AXIS));
        clientsInCasesQueryPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        JLabel clientsInCasesLabel = new JLabel("Show How Many Participants In Each Case:");
        JButton clientsInCasesButton = new JButton("Perform");
        clientsInCasesQueryPane.add(clientsInCasesLabel);
        clientsInCasesQueryPane.add(Box.createRigidArea(new Dimension(10, 0)));
        clientsInCasesQueryPane.add(clientsInCasesButton);

        JPanel casesForFirmQueryPane = new JPanel();
        casesForFirmQueryPane.setLayout(new BoxLayout(casesForFirmQueryPane, BoxLayout.X_AXIS));
        casesForFirmQueryPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        JLabel casesForFirmLabel = new JLabel("Show How Many Cases Each Firm Leads:");
        JButton casesForFirmButton = new JButton("Perform");
        casesForFirmQueryPane.add(casesForFirmLabel);
        casesForFirmQueryPane.add(Box.createRigidArea(new Dimension(10, 0)));
        casesForFirmQueryPane.add(casesForFirmButton);

        JPanel seniorCitizensQueryPane = new JPanel();
        seniorCitizensQueryPane.setLayout(new BoxLayout(seniorCitizensQueryPane, BoxLayout.X_AXIS));
        seniorCitizensQueryPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        JLabel seniorCitizensLabel = new JLabel("Show How Senior Citizens Clients:");
        JButton seniorCitizensButton = new JButton("Perform");
        seniorCitizensQueryPane.add(seniorCitizensLabel);
        seniorCitizensQueryPane.add(Box.createRigidArea(new Dimension(10, 0)));
        seniorCitizensQueryPane.add(seniorCitizensButton);

        JPanel chairmanNamesQueryPane = new JPanel();
        chairmanNamesQueryPane.setLayout(new BoxLayout(chairmanNamesQueryPane, BoxLayout.X_AXIS));
        chairmanNamesQueryPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        JLabel chairmanNamesLabel = new JLabel("Show Chairman Names For All Firms:");
        JButton chairmanNamesButton = new JButton("Perform");
        chairmanNamesQueryPane.add(chairmanNamesLabel);
        chairmanNamesQueryPane.add(Box.createRigidArea(new Dimension(10, 0)));
        chairmanNamesQueryPane.add(chairmanNamesButton);

        JPanel balanceInBankQueryPane = new JPanel();
        balanceInBankQueryPane.setLayout(new BoxLayout(balanceInBankQueryPane, BoxLayout.X_AXIS));
        balanceInBankQueryPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        JLabel balanceInBankLabel = new JLabel("Show Bank Account Balance For All Firms:");
        JButton balanceInBankButton = new JButton("Perform");
        balanceInBankQueryPane.add(balanceInBankLabel);
        balanceInBankQueryPane.add(Box.createRigidArea(new Dimension(10, 0)));
        balanceInBankQueryPane.add(balanceInBankButton);

        JPanel unpaidCasesQueryPane = new JPanel();
        unpaidCasesQueryPane.setLayout(new BoxLayout(unpaidCasesQueryPane, BoxLayout.X_AXIS));
        unpaidCasesQueryPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        JLabel unpaidCasesLabel = new JLabel("Show All Unpaid Cases:");
        JButton unpaidCasesButton = new JButton("Perform");
        unpaidCasesQueryPane.add(unpaidCasesLabel);
        unpaidCasesQueryPane.add(Box.createRigidArea(new Dimension(10, 0)));
        unpaidCasesQueryPane.add(unpaidCasesButton);

        JPanel paidSumForClientsQueryPane = new JPanel();
        paidSumForClientsQueryPane.setLayout(new BoxLayout(paidSumForClientsQueryPane, BoxLayout.X_AXIS));
        paidSumForClientsQueryPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        JLabel paidSumForClientsLabel = new JLabel("Show Paid Sum For Each Client:");
        JButton paidSumForClientsButton = new JButton("Perform");
        paidSumForClientsQueryPane.add(paidSumForClientsLabel);
        paidSumForClientsQueryPane.add(Box.createRigidArea(new Dimension(10, 0)));
        paidSumForClientsQueryPane.add(paidSumForClientsButton);

        JPanel transCountOnBankQueryPane = new JPanel();
        transCountOnBankQueryPane.setLayout(new BoxLayout(transCountOnBankQueryPane, BoxLayout.X_AXIS));
        transCountOnBankQueryPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        JLabel transCountOnBankLabel = new JLabel("Show Transactions Count Sent To Bank Account:");
        JButton transCountOnBankButton = new JButton("Perform");
        transCountOnBankQueryPane.add(transCountOnBankLabel);
        transCountOnBankQueryPane.add(Box.createRigidArea(new Dimension(10, 0)));
        transCountOnBankQueryPane.add(transCountOnBankButton);

        verticalPane.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPane.add(lawyersNameQueryPane, BorderLayout.WEST);
        verticalPane.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPane.add(casesCountForLawyersQueryPane, BorderLayout.WEST);
        verticalPane.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPane.add(clientsInCasesQueryPane, BorderLayout.WEST);
        verticalPane.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPane.add(casesForFirmQueryPane, BorderLayout.WEST);
        verticalPane.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPane.add(seniorCitizensQueryPane, BorderLayout.WEST);
        verticalPane.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPane.add(chairmanNamesQueryPane, BorderLayout.WEST);
        verticalPane.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPane.add(balanceInBankQueryPane, BorderLayout.WEST);
        verticalPane.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPane.add(unpaidCasesQueryPane, BorderLayout.WEST);
        verticalPane.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPane.add(paidSumForClientsQueryPane, BorderLayout.WEST);
        verticalPane.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPane.add(transCountOnBankQueryPane, BorderLayout.WEST);
        queriesPane.add(verticalPane, BorderLayout.WEST);


        lawyersNameButton.addActionListener(e -> {
            int id;
            try {
                id = Integer.valueOf(lawyersNameField.getText());
                if (id < 0)
                    id = 1;
            }
            catch (NumberFormatException ex) {
                id = 1;
            }
            openLawyersNameDialog(id);
        });
        casesCountForLawyersButton.addActionListener(e -> openQueryDialog(1));
        clientsInCasesButton.addActionListener(e -> openQueryDialog(2));
        casesForFirmButton.addActionListener(e -> openQueryDialog(3));
        seniorCitizensButton.addActionListener(e -> openQueryDialog(4));
        chairmanNamesButton.addActionListener(e -> openQueryDialog(5));
        balanceInBankButton.addActionListener(e -> openQueryDialog(6));
        unpaidCasesButton.addActionListener(e -> openQueryDialog(7));
        paidSumForClientsButton.addActionListener(e -> openQueryDialog(8));
        transCountOnBankButton.addActionListener(e -> openQueryDialog(9));
    }

    private DefaultTableModel getSelectTable(String table_name) {
        Vector<Object> columnNames = new Vector<>();
        Vector<Object> data = new Vector<>();

        try {
            //  Read data from a table

            String sql = "Select * from " + table_name;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery( sql );
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            //  Get column names

            for (int i = 1; i <= columns; i++) {
                columnNames.addElement( md.getColumnName(i) );
            }

            //  Get row data

            while (rs.next()) {
                Vector<Object> row = new Vector<>(columns);

                for (int i = 1; i <= columns; i++) {
                    row.addElement( rs.getObject(i) );
                }
                data.addElement(row);
            }

            rs.close();
            stmt.close();
        }
        catch(Exception e) {
            //System.out.println(e);
            e.printStackTrace();
        }

        //  Create table with database data

        return new DefaultTableModel(data, columnNames)
        {
            @Override
            public Class getColumnClass(int column) {
                for (int row = 0; row < getRowCount(); row++) {
                    Object o = getValueAt(row, column);

                    if (o != null) {
                        return o.getClass();
                    }
                }
                return Object.class;
            }
        };
    }

    private DefaultTableModel getQueryTable(String query) {
        Vector<Object> columnNames = new Vector<>();
        Vector<Object> data = new Vector<>();

        try {
            //  Read data from a table

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            //  Get column names

            for (int i = 1; i <= columns; i++) {
                columnNames.addElement( md.getColumnName(i) );
            }

            //  Get row data

            while (rs.next()) {
                Vector<Object> row = new Vector<>(columns);

                for (int i = 1; i <= columns; i++) {
                    row.addElement( rs.getObject(i) );
                }
                data.addElement(row);
            }

            rs.close();
            stmt.close();
        }
        catch(Exception e) {
            //System.out.println(e);
            e.printStackTrace();
        }

        //  Create table with database data

        return new DefaultTableModel(data, columnNames)
        {
            @Override
            public Class getColumnClass(int column) {
                for (int row = 0; row < getRowCount(); row++) {
                    Object o = getValueAt(row, column);

                    if (o != null) {
                        return o.getClass();
                    }
                }
                return Object.class;
            }
        };
    }

    private boolean executeQuery(String query) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            return true;
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "SQL error", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
    }

    private int getCaseIdByName (String name) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT case_id FROM cases WHERE case_name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int id = rs.getInt("case_id");
            ps.close();
            return id;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void configureInsertTab() {
        JPanel inputPane = new JPanel();
        JButton firmsButton = new JButton("Firms");
        JButton lawyersButton = new JButton("Lawyers");
        JButton casesButton = new JButton("Cases");
        JButton clientsButton = new JButton("Clients");
        JButton clients_casesButton = new JButton("Clients-Cases");
        JButton bankAccountsButton = new JButton("Bank Accounts");
        JButton transactionsButton = new JButton("Transactions");

        firmsButton.addActionListener(e -> openInsertDialog("firms"));
        lawyersButton.addActionListener(e -> openInsertDialog("lawyers"));
        casesButton.addActionListener(e -> openInsertDialog("cases"));
        clientsButton.addActionListener(e -> openInsertDialog("clients"));
        clients_casesButton.addActionListener(e -> openInsertDialog("clients-cases"));
        bankAccountsButton.addActionListener(e -> openInsertDialog("bank_accounts"));
        transactionsButton.addActionListener(e -> openInsertDialog("transactions"));

        inputPane.add(firmsButton);
        inputPane.add(lawyersButton);
        inputPane.add(casesButton);
        inputPane.add(clientsButton);
        inputPane.add(clients_casesButton);
        inputPane.add(bankAccountsButton);
        inputPane.add(transactionsButton);

        insertPane.add(Box.createRigidArea(new Dimension(100, 150)));
        insertPane.add(inputPane);
    }

    private void configureUpdateTab() {
        JPanel inputPane = new JPanel();
        JButton firmsButton = new JButton("Firms");
        JButton lawyersButton = new JButton("Lawyers");
        JButton casesButton = new JButton("Cases");
        JButton clientsButton = new JButton("Clients");
        JButton clients_casesButton = new JButton("Clients-Cases");
        JButton bankAccountsButton = new JButton("Bank Accounts");
        JButton transactionsButton = new JButton("Transactions");

        firmsButton.addActionListener(e -> openInsertDialog("firms"));
        lawyersButton.addActionListener(e -> openInsertDialog("lawyers"));
        casesButton.addActionListener(e -> openInsertDialog("cases"));
        clientsButton.addActionListener(e -> openInsertDialog("clients"));
        clients_casesButton.addActionListener(e -> openInsertDialog("clients-cases"));
        bankAccountsButton.addActionListener(e -> openInsertDialog("bank_accounts"));
        transactionsButton.addActionListener(e -> openInsertDialog("transactions"));

        inputPane.add(firmsButton);
        inputPane.add(lawyersButton);
        inputPane.add(casesButton);
        inputPane.add(clientsButton);
        inputPane.add(clients_casesButton);
        inputPane.add(bankAccountsButton);
        inputPane.add(transactionsButton);

        updatePane.add(Box.createRigidArea(new Dimension(100, 150)));
        updatePane.add(inputPane);
    }

    class DemoModelItem {
        public String objectName;
        public DemoModelItem(String objectName){
            this.objectName = objectName;
        }

        public String toString(){
            return objectName;
        }
    }

    private DefaultComboBoxModel buildComboBoxModel() {
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        String SQL = "SELECT case_name from cases";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                comboBoxModel.addElement(new DemoModelItem(rs.getString("case_name")));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comboBoxModel;
    }

    //а ты тэйбл там адд в панели она видно там да
    private void configureAddTab() {
        JPanel inputPane = new JPanel();
        JLabel nameLabel = new JLabel("Client's Name:");
        JTextField nameField = new JTextField("",3);
        JLabel ageLabel = new JLabel("Client's Age:");
        JTextField ageField = new JTextField("", 3);
        JLabel caseLabel = new JLabel("Select Case:");
        JComboBox caseBox = new JComboBox(buildComboBoxModel());
        caseBox.setPreferredSize(new Dimension(88, 30));
        ((JLabel) caseBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        JButton insertButton = new JButton("Create");

        nameField.addActionListener(e -> {
            if (!nameField.getText().matches("^[ A-Za-z]+$"))
                nameField.setText("");
        });

        ageField.addActionListener(e -> {
            try {
                Integer.valueOf(ageField.getText());
            }
            catch (NumberFormatException ex) {
                ageField.setText("");
            }
        });

        insertButton.addActionListener(e -> {
            int age;
            try {
                if (!nameField.getText().matches("^[ A-Za-z]+$")) {
                    JOptionPane.showMessageDialog(this, "Incorrect Name",
                            "Input Error", JOptionPane.INFORMATION_MESSAGE);
                    nameField.setText("");
                }
                else {
                    age = Integer.valueOf(ageField.getText());
                    if (age < 18) {
                        JOptionPane.showMessageDialog(this, "Incorrect Age",
                                "Input Error", JOptionPane.INFORMATION_MESSAGE);
                        ageField.setText("");

                    }
                    else {
                        if (caseBox.getSelectedItem() != null)
                        {
                            String first = "INSERT INTO clients (client_id, client_name, client_age) VALUES (nextval('clientsCT'), ?, ?);";
                            String second = "INSERT INTO clients_cases (client_id, case_id) VALUES (currval('clientsCT'), ?);";
                            connection.setAutoCommit(false);
                            PreparedStatement ps1 = connection.prepareStatement(first);
                            PreparedStatement ps2 = connection.prepareStatement(second);
                            ps1.setString(1, nameField.getText());
                            ps1.setInt(2, age);
                            ps1.executeUpdate();
                            ps2.setInt(1, getCaseIdByName(caseBox.getSelectedItem().toString()));
                            ps2.executeUpdate();
                            connection.commit();
                            ps1.close();
                            ps2.close();
                        }
                    }
                }
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Incorrect Age",
                        "Input Error", JOptionPane.INFORMATION_MESSAGE);
                ageField.setText("");
            }
            catch (NullPointerException eex) {
                JOptionPane.showMessageDialog(this, "You Should Select Case ID",
                        "Input Error", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "SQL error", JOptionPane.INFORMATION_MESSAGE);
                try {
                    connection.rollback();
                } catch (SQLException exc) {
                    exc.printStackTrace();
                    System.out.println("Rollback Error");
                }
            }
            finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println("Setting AutoCommit Error");
                }
            }
        });

        caseBox.setSelectedItem(null);
        inputPane.add(nameLabel);
        inputPane.add(nameField);
        inputPane.add(ageLabel);
        inputPane.add(ageField);
        inputPane.add(caseLabel);
        inputPane.add(caseBox);
        inputPane.add(insertButton);

        JPanel addCasePane = new JPanel();
        JButton addCaseButton = new JButton("Add Case");
        addCasePane.add(Box.createRigidArea(new Dimension(240, 30)));
        addCasePane.add(addCaseButton);

        addCaseButton.addActionListener(e -> {
            openAddCaseDialog();
            if (updated) {
                //caseBox.addItem(String.valueOf(serialNum));
                caseBox.addItem(caseName);
                updated = false;
            }
        });

        addClientWithCasePane.setLayout(new GridLayout(10,2));
        addClientWithCasePane.add(inputPane, BorderLayout.CENTER);
        addClientWithCasePane.add(addCasePane, BorderLayout.LINE_END);
    }

    private void configureSfTab() {
        sortedFilteredPane.setLayout(new BorderLayout());
        JPanel inputPane = new JPanel();
        inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.X_AXIS));
        JLabel phraseLabel = new JLabel("Choose way to see table");
        JButton showTableButton = new JButton("Show Table");
        JLabel limitLabel = new JLabel("Limit:");
        JLabel offsetLabel = new JLabel("Offset:");
        JTextField limitField = new JTextField("100");
        JTextField offsetField = new JTextField("0");
        JButton showPagedTableButton = new JButton("Show Table with pagination");

        JLabel entityLabel = new JLabel("Select entity: ");
        JRadioButton nameRadioButton = new JRadioButton("Name");
        JRadioButton ageRadioButton = new JRadioButton("Age");
        ButtonGroup bg = new ButtonGroup();
        bg.add(nameRadioButton);
        bg.add(ageRadioButton);
        nameRadioButton.setSelected(true);
        ageRadioButton.setSelected(false);

        JLabel nameOrderLabel = new JLabel("Select order: ");
        JRadioButton nameOrderRadioButton = new JRadioButton("A-Z");
        JRadioButton descNameOrderRadioButton = new JRadioButton("Z-A");
        ButtonGroup name_bg = new ButtonGroup();
        name_bg.add(nameOrderRadioButton);
        name_bg.add(descNameOrderRadioButton);
        nameOrderRadioButton.setSelected(true);
        descNameOrderRadioButton.setSelected(false);

        JLabel ageOrderLabel = new JLabel("Select order: ");
        JRadioButton ageOrderRadioButton = new JRadioButton("Younger-Older");
        JRadioButton descAgeOrderRadioButton = new JRadioButton("Older-Younger");
        ButtonGroup age_bg = new ButtonGroup();
        age_bg.add(ageOrderRadioButton);
        age_bg.add(descAgeOrderRadioButton);
        ageOrderRadioButton.setSelected(true);
        descAgeOrderRadioButton.setSelected(false);

        JLabel filterLabel = new JLabel("Enter filter: ");
        JTextField filterField = new JTextField("%", 10);
        filterField.setHorizontalAlignment(JTextField.CENTER);

        JPanel sfPane = new JPanel();
        sfPane.add(entityLabel);
        sfPane.add(nameRadioButton);
        sfPane.add(ageRadioButton);
        //sfPane.add(new JSeparator(/*SwingConstants.VERTICAL*/));
        sfPane.add(nameOrderLabel);
        sfPane.add(nameOrderRadioButton);
        sfPane.add(descNameOrderRadioButton);
        sfPane.add(ageOrderLabel);
        sfPane.add(ageOrderRadioButton);
        sfPane.add(descAgeOrderRadioButton);
        sfPane.add(filterLabel);
        sfPane.add(filterField);

        inputPane.add(phraseLabel);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(showTableButton);
        inputPane.add(Box.createRigidArea(new Dimension(20, 0)));
        inputPane.add(limitLabel);
        inputPane.add(limitField);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(offsetLabel);
        inputPane.add(offsetField);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(showPagedTableButton);


        final JScrollPane[] scrollPane = new JScrollPane[1];
        showTableButton.addActionListener(e -> {
            String filter = filterField.getText();
            String sort;
            if (nameRadioButton.isSelected()) {
                filter = "WHERE client_name LIKE '" + filter + "' ";
                if (nameOrderRadioButton.isSelected())
                    sort = "ORDER BY client_name ";
                else sort = "ORDER BY client_name DESC ";
            }
            else {
                //filter = "WHERE client_age LIKE '" + filter + "' ";
                filter = "";
                if (ageOrderRadioButton.isSelected())
                    sort = "ORDER BY client_age ";
                else sort = "ORDER BY client_age DESC ";
            }
            //System.out.println("clients " + filter + sort);
            JTable table = new JTable(getSelectTable("clients " + filter
                    + sort));
            if (sortedFilteredPane.getComponentCount() != 2)
                sortedFilteredPane.remove(scrollPane[0]);
            scrollPane[0] = new JScrollPane(table);
            sortedFilteredPane.add(scrollPane[0], BorderLayout.CENTER);
            sortedFilteredPane.revalidate();

        });
        showPagedTableButton.addActionListener(e -> {
            int limit, offset;
            try {
                limit = Integer.valueOf(limitField.getText());
                offset = Integer.valueOf(offsetField.getText());

                if (limit < 0 || limit > 10000)
                    limit = 100;
                if (offset < 0 || offset > 10000)
                    offset = 0;
            } catch (NumberFormatException ex) {
                limit = 100;
                offset = 0;
            }
            String filter = filterField.getText();
            String sort;
            if (nameRadioButton.isSelected()) {
                filter = "WHERE client_name LIKE '" + filter + "' ";
                if (nameOrderRadioButton.isSelected())
                    sort = "ORDER BY client_name ";
                else sort = "ORDER BY client_name DESC ";
            }
            else {
                //filter = "WHERE client_age LIKE '" + filter + "' ";
                filter = "";
                if (ageOrderRadioButton.isSelected())
                    sort = "ORDER BY client_age ";
                else sort = "ORDER BY client_age DESC ";
            }
            JTable table = new JTable(getSelectTable("clients " + filter
                     + sort + "LIMIT " +  limit + " OFFSET " + offset));
            if (sortedFilteredPane.getComponentCount() != 2)
                sortedFilteredPane.remove(scrollPane[0]);
            scrollPane[0] = new JScrollPane(table);
            sortedFilteredPane.add(scrollPane[0], BorderLayout.CENTER);
            sortedFilteredPane.revalidate();

        });
        sortedFilteredPane.add(inputPane, BorderLayout.PAGE_START);
        sortedFilteredPane.add(sfPane, BorderLayout.AFTER_LAST_LINE);
    }

    private void configureFirmsTab() {
        firmsPane.setLayout(new BorderLayout());
        JPanel inputPane = new JPanel();
        inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.X_AXIS));
        JLabel phraseLabel = new JLabel("Choose way to see table");
        JButton showTableButton = new JButton("Show Table");
        JLabel limitLabel = new JLabel("Limit:");
        JLabel offsetLabel = new JLabel("Offset:");
        JTextField limitField = new JTextField("100");
        JTextField offsetField = new JTextField("0");
        JButton showPagedTableButton = new JButton("Show Table with pagination");

        inputPane.add(phraseLabel);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(showTableButton);
        inputPane.add(Box.createRigidArea(new Dimension(20, 0)));
        inputPane.add(limitLabel);
        inputPane.add(limitField);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(offsetLabel);
        inputPane.add(offsetField);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(showPagedTableButton);


        final JScrollPane[] scrollPane = new JScrollPane[1];
        showTableButton.addActionListener(e -> {
                JTable table = new JTable(getSelectTable("firms"));
                if (firmsPane.getComponentCount() != 1)
                    firmsPane.remove(scrollPane[0]);
                scrollPane[0] = new JScrollPane(table);
                firmsPane.add(scrollPane[0], BorderLayout.CENTER);
                firmsPane.revalidate();
        });
        showPagedTableButton.addActionListener(e -> {
                int limit, offset;
                try {
                    limit = Integer.valueOf(limitField.getText());
                    offset = Integer.valueOf(offsetField.getText());

                    if (limit < 0 || limit > 10000)
                        limit = 100;
                    if (offset < 0 || offset > 10000)
                        offset = 0;
                } catch (NumberFormatException ex) {
                    limit = 100;
                    offset = 0;
                }
                JTable table = new JTable(getSelectTable("firms " + "LIMIT " +  limit + " OFFSET " + offset));
                if (firmsPane.getComponentCount() != 1)
                    firmsPane.remove(scrollPane[0]);
                scrollPane[0] = new JScrollPane(table);
                firmsPane.add(scrollPane[0], BorderLayout.CENTER);
                firmsPane.revalidate();

        });
        firmsPane.add(inputPane, BorderLayout.NORTH);
    }

    private void configureLawyersTab() {
        lawyersPane.setLayout(new BorderLayout());
        JPanel inputPane = new JPanel();
        inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.X_AXIS));
        JLabel phraseLabel = new JLabel("Choose way to see table");
        JButton showTableButton = new JButton("Show Table");
        JLabel limitLabel = new JLabel("Limit:");
        JLabel offsetLabel = new JLabel("Offset:");
        JTextField limitField = new JTextField("100");
        JTextField offsetField = new JTextField("0");
        JButton showPagedTableButton = new JButton("Show Table with pagination");

        inputPane.add(phraseLabel);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(showTableButton);
        inputPane.add(Box.createRigidArea(new Dimension(20, 0)));
        inputPane.add(limitLabel);
        inputPane.add(limitField);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(offsetLabel);
        inputPane.add(offsetField);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(showPagedTableButton);


        final JScrollPane[] scrollPane = new JScrollPane[1];
        showTableButton.addActionListener(e -> {
                JTable table = new JTable(getSelectTable("lawyers"));
                if (lawyersPane.getComponentCount() != 1)
                    lawyersPane.remove(scrollPane[0]);
                scrollPane[0] = new JScrollPane(table);
                lawyersPane.add(scrollPane[0], BorderLayout.CENTER);
                lawyersPane.revalidate();

        });
        showPagedTableButton.addActionListener(e -> {
                int limit, offset;
                try {
                    limit = Integer.valueOf(limitField.getText());
                    offset = Integer.valueOf(offsetField.getText());

                    if (limit < 0 || limit > 10000)
                        limit = 100;
                    if (offset < 0 || offset > 10000)
                        offset = 0;
                } catch (NumberFormatException ex) {
                    limit = 100;
                    offset = 0;
                }
                JTable table = new JTable(getSelectTable("lawyers " + "LIMIT " +  limit + " OFFSET " + offset));
                if (lawyersPane.getComponentCount() != 1)
                    lawyersPane.remove(scrollPane[0]);
                scrollPane[0] = new JScrollPane(table);
                lawyersPane.add(scrollPane[0], BorderLayout.CENTER);
                lawyersPane.revalidate();

        });
        lawyersPane.add(inputPane, BorderLayout.NORTH);
    }

    private void configureCasesTab() {
        casesPane.setLayout(new BorderLayout());
        JPanel inputPane = new JPanel();
        inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.X_AXIS));
        JLabel phraseLabel = new JLabel("Choose way to see table");
        JButton showTableButton = new JButton("Show Table");
        JLabel limitLabel = new JLabel("Limit:");
        JLabel offsetLabel = new JLabel("Offset:");
        JTextField limitField = new JTextField("100");
        JTextField offsetField = new JTextField("0");
        JButton showPagedTableButton = new JButton("Show Table with pagination");

        inputPane.add(phraseLabel);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(showTableButton);
        inputPane.add(Box.createRigidArea(new Dimension(20, 0)));
        inputPane.add(limitLabel);
        inputPane.add(limitField);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(offsetLabel);
        inputPane.add(offsetField);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(showPagedTableButton);


        final JScrollPane[] scrollPane = new JScrollPane[1];
        showTableButton.addActionListener(e -> {
                JTable table = new JTable(getSelectTable("cases"));
                if (casesPane.getComponentCount() != 1)
                    casesPane.remove(scrollPane[0]);
                scrollPane[0] = new JScrollPane(table);
                casesPane.add(scrollPane[0], BorderLayout.CENTER);
                casesPane.revalidate();

        });
        showPagedTableButton.addActionListener(e -> {
                int limit, offset;
                try {
                    limit = Integer.valueOf(limitField.getText());
                    offset = Integer.valueOf(offsetField.getText());

                    if (limit < 0 || limit > 10000)
                        limit = 100;
                    if (offset < 0 || offset > 10000)
                        offset = 0;
                } catch (NumberFormatException ex) {
                    limit = 100;
                    offset = 0;
                }
                JTable table = new JTable(getSelectTable("cases " + "LIMIT " +  limit + " OFFSET " + offset));
                if (casesPane.getComponentCount() != 1)
                    casesPane.remove(scrollPane[0]);
                scrollPane[0] = new JScrollPane(table);
                casesPane.add(scrollPane[0], BorderLayout.CENTER);
                casesPane.revalidate();

        });
        casesPane.add(inputPane, BorderLayout.NORTH);
    }

    private void configureClientsTab() {
        clientsPane.setLayout(new BorderLayout());
        JPanel inputPane = new JPanel();
        inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.X_AXIS));
        JLabel phraseLabel = new JLabel("Choose way to see table");
        JButton showTableButton = new JButton("Show Table");
        JLabel limitLabel = new JLabel("Limit:");
        JLabel offsetLabel = new JLabel("Offset:");
        JTextField limitField = new JTextField("100");
        JTextField offsetField = new JTextField("0");
        JButton showPagedTableButton = new JButton("Show Table with pagination");

        inputPane.add(phraseLabel);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(showTableButton);
        inputPane.add(Box.createRigidArea(new Dimension(20, 0)));
        inputPane.add(limitLabel);
        inputPane.add(limitField);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(offsetLabel);
        inputPane.add(offsetField);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(showPagedTableButton);


        final JScrollPane[] scrollPane = new JScrollPane[1];
        showTableButton.addActionListener(e -> {
                JTable table = new JTable(getSelectTable("clients"));
                if (clientsPane.getComponentCount() != 1)
                    clientsPane.remove(scrollPane[0]);
                scrollPane[0] = new JScrollPane(table);
                clientsPane.add(scrollPane[0], BorderLayout.CENTER);
                clientsPane.revalidate();

        });
        showPagedTableButton.addActionListener(e -> {
                int limit, offset;
                try {
                    limit = Integer.valueOf(limitField.getText());
                    offset = Integer.valueOf(offsetField.getText());

                    if (limit < 0 || limit > 10000)
                        limit = 100;
                    if (offset < 0 || offset > 10000)
                        offset = 0;
                } catch (NumberFormatException ex) {
                    limit = 100;
                    offset = 0;
                }
                JTable table = new JTable(getSelectTable("clients " + "LIMIT " +  limit + " OFFSET " + offset));
                if (clientsPane.getComponentCount() != 1)
                    clientsPane.remove(scrollPane[0]);
                scrollPane[0] = new JScrollPane(table);
                clientsPane.add(scrollPane[0], BorderLayout.CENTER);
                clientsPane.revalidate();

        });
        clientsPane.add(inputPane, BorderLayout.NORTH);
    }

    private void configureClientsCasesTab() {
        clients_casesPane.setLayout(new BorderLayout());
        JPanel inputPane = new JPanel();
        inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.X_AXIS));
        JLabel phraseLabel = new JLabel("Choose way to see table");
        JButton showTableButton = new JButton("Show Table");
        JLabel limitLabel = new JLabel("Limit:");
        JLabel offsetLabel = new JLabel("Offset:");
        JTextField limitField = new JTextField("100");
        JTextField offsetField = new JTextField("0");
        JButton showPagedTableButton = new JButton("Show Table with pagination");

        inputPane.add(phraseLabel);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(showTableButton);
        inputPane.add(Box.createRigidArea(new Dimension(20, 0)));
        inputPane.add(limitLabel);
        inputPane.add(limitField);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(offsetLabel);
        inputPane.add(offsetField);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(showPagedTableButton);


        final JScrollPane[] scrollPane = new JScrollPane[1];
        showTableButton.addActionListener(e -> {
            JTable table = new JTable(getSelectTable("clients_cases"));
            if (clients_casesPane.getComponentCount() != 1)
                clients_casesPane.remove(scrollPane[0]);
            scrollPane[0] = new JScrollPane(table);
            clients_casesPane.add(scrollPane[0], BorderLayout.CENTER);
            clients_casesPane.revalidate();

        });
        showPagedTableButton.addActionListener(e -> {
            int limit, offset;
            try {
                limit = Integer.valueOf(limitField.getText());
                offset = Integer.valueOf(offsetField.getText());

                if (limit < 0 || limit > 10000)
                    limit = 100;
                if (offset < 0 || offset > 10000)
                    offset = 0;
            } catch (NumberFormatException ex) {
                limit = 100;
                offset = 0;
            }
            JTable table = new JTable(getSelectTable("clients_cases " + "LIMIT " +  limit + " OFFSET " + offset));
            if (clients_casesPane.getComponentCount() != 1)
                clients_casesPane.remove(scrollPane[0]);
            scrollPane[0] = new JScrollPane(table);
            clients_casesPane.add(scrollPane[0], BorderLayout.CENTER);
            clients_casesPane.revalidate();

        });
        clients_casesPane.add(inputPane, BorderLayout.NORTH);
    }

    private void configureBankAccountsTab() {
        bankAccountsPane.setLayout(new BorderLayout());
        JPanel inputPane = new JPanel();
        inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.X_AXIS));
        JLabel phraseLabel = new JLabel("Choose way to see table");
        JButton showTableButton = new JButton("Show Table");
        JLabel limitLabel = new JLabel("Limit:");
        JLabel offsetLabel = new JLabel("Offset:");
        JTextField limitField = new JTextField("100");
        JTextField offsetField = new JTextField("0");
        JButton showPagedTableButton = new JButton("Show Table with pagination");

        inputPane.add(phraseLabel);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(showTableButton);
        inputPane.add(Box.createRigidArea(new Dimension(20, 0)));
        inputPane.add(limitLabel);
        inputPane.add(limitField);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(offsetLabel);
        inputPane.add(offsetField);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(showPagedTableButton);


        final JScrollPane[] scrollPane = new JScrollPane[1];
        showTableButton.addActionListener(e -> {
                JTable table = new JTable(getSelectTable("bank_accounts"));
                if (bankAccountsPane.getComponentCount() != 1)
                    bankAccountsPane.remove(scrollPane[0]);
                scrollPane[0] = new JScrollPane(table);
                bankAccountsPane.add(scrollPane[0], BorderLayout.CENTER);
                bankAccountsPane.revalidate();

        });
        showPagedTableButton.addActionListener(e -> {
                int limit, offset;
                try {
                    limit = Integer.valueOf(limitField.getText());
                    offset = Integer.valueOf(offsetField.getText());

                    if (limit < 0 || limit > 10000)
                        limit = 100;
                    if (offset < 0 || offset > 10000)
                        offset = 0;
                } catch (NumberFormatException ex) {
                    limit = 100;
                    offset = 0;
                }
                JTable table = new JTable(getSelectTable("bank_accounts " + "LIMIT " +  limit + " OFFSET " + offset));
                if (bankAccountsPane.getComponentCount() != 1)
                    bankAccountsPane.remove(scrollPane[0]);
                scrollPane[0] = new JScrollPane(table);
                bankAccountsPane.add(scrollPane[0], BorderLayout.CENTER);
                bankAccountsPane.revalidate();

        });
        bankAccountsPane.add(inputPane, BorderLayout.NORTH);
    }

    private void configureTransactionsTab() {
        transactionsPane.setLayout(new BorderLayout());
        JPanel inputPane = new JPanel();
        inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.X_AXIS));
        JLabel phraseLabel = new JLabel("Choose way to see table");
        JButton showTableButton = new JButton("Show Table");
        JLabel limitLabel = new JLabel("Limit:");
        JLabel offsetLabel = new JLabel("Offset:");
        JTextField limitField = new JTextField("100");
        JTextField offsetField = new JTextField("0");
        JButton showPagedTableButton = new JButton("Show Table with pagination");

        inputPane.add(phraseLabel);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(showTableButton);
        inputPane.add(Box.createRigidArea(new Dimension(20, 0)));
        inputPane.add(limitLabel);
        inputPane.add(limitField);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(offsetLabel);
        inputPane.add(offsetField);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(showPagedTableButton);


        final JScrollPane[] scrollPane = new JScrollPane[1];
        showTableButton.addActionListener(e -> {
                JTable table = new JTable(getSelectTable("transactions"));
                if (transactionsPane.getComponentCount() != 1)
                    transactionsPane.remove(scrollPane[0]);
                scrollPane[0] = new JScrollPane(table);
                transactionsPane.add(scrollPane[0], BorderLayout.CENTER);
                transactionsPane.revalidate();

        });
        showPagedTableButton.addActionListener(e -> {
                int limit, offset;
                try {
                    limit = Integer.valueOf(limitField.getText());
                    offset = Integer.valueOf(offsetField.getText());

                    if (limit < 0 || limit > 10000)
                        limit = 100;
                    if (offset < 0 || offset > 10000)
                        offset = 0;
                } catch (NumberFormatException ex) {
                    limit = 100;
                    offset = 0;
                }
                JTable table = new JTable(getSelectTable("transactions " + "LIMIT " +  limit + " OFFSET " + offset));
                if (transactionsPane.getComponentCount() != 1)
                    transactionsPane.remove(scrollPane[0]);
                scrollPane[0] = new JScrollPane(table);
                transactionsPane.add(scrollPane[0], BorderLayout.CENTER);
                transactionsPane.revalidate();

        });
        transactionsPane.add(inputPane, BorderLayout.NORTH);
    }

    private void openAboutDialog() {
        JOptionPane.showMessageDialog(this,
                "This is " +
                        "Database application\n" +
                        "Done as a task for Database Course at\nNovosibirsk State University\n" +
                        "by FIT 16202 Student\nArtyom Stryapchev",
                "About",
                JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ClassLoader.getSystemResource("bored_me.jpg")));
    }

    private void openLawyersNameDialog(int id) {
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            String query = "SELECT case_id, lawyer_name FROM cases " +
                    "JOIN lawyers ON case_lawyer_id = lawyer_id WHERE case_id = " + id;
            rs = statement.executeQuery(query);
            /*if (rs.isLast()) {
                JOptionPane.showMessageDialog(this, "Wrong argument",
                        "Error", JOptionPane.INFORMATION_MESSAGE);
            }*/
            while (rs.next()) {
                int case_id = rs.getInt("case_id");
                String name = rs.getString("lawyer_name");
                JOptionPane.showMessageDialog(this, "Case ID: " + case_id + "\nName: " + name,
                        "Lawyer's Name For Case", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Wrong argument",
                    "Error", JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
        }
        finally {
                try {
                    assert statement != null;
                    statement.close();
                    assert rs != null;
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    private void openQueryDialog(int index) {
        JDialog queryDialog = new JDialog();
        queryDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        queryDialog.setTitle(titles.get(index));
        queryDialog.setModal(true);
        queryDialog.setPreferredSize(new Dimension(600, 300));

        JPanel queryPane = new JPanel();
        queryPane.setLayout(new BorderLayout());
        JPanel inputPane = new JPanel();
        inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.X_AXIS));
        JLabel phraseLabel = new JLabel("Choose way to see table");
        JButton showTableButton = new JButton("Show Table");
        JLabel limitLabel = new JLabel("Limit:");
        JLabel offsetLabel = new JLabel("Offset:");
        JTextField limitField = new JTextField("100", 3);
        JTextField offsetField = new JTextField("0", 3);
        JButton showPagedTableButton = new JButton("Show Table with pagination");

        inputPane.add(phraseLabel);
        inputPane.add(Box.createRigidArea(new Dimension(5, 0)));
        inputPane.add(showTableButton);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(limitLabel);
        inputPane.add(limitField);
        inputPane.add(Box.createRigidArea(new Dimension(5, 0)));
        inputPane.add(offsetLabel);
        inputPane.add(offsetField);
        inputPane.add(Box.createRigidArea(new Dimension(5, 0)));
        inputPane.add(showPagedTableButton);


        final JScrollPane[] scrollPane = new JScrollPane[1];
        showTableButton.addActionListener(e -> {
                JTable table = new JTable(getQueryTable(queries.get(index)));
                if (queryPane.getComponentCount() != 1)
                    queryPane.remove(scrollPane[0]);
                scrollPane[0] = new JScrollPane(table);
                queryPane.add(scrollPane[0], BorderLayout.CENTER);
                queryPane.revalidate();

        });
        showPagedTableButton.addActionListener(e -> {
                int limit, offset;
                try {
                    limit = Integer.valueOf(limitField.getText());
                    offset = Integer.valueOf(offsetField.getText());

                    if (limit < 0 || limit > 10000)
                        limit = 100;
                    if (offset < 0 || offset > 10000)
                        offset = 0;
                } catch (NumberFormatException ex) {
                    limit = 100;
                    offset = 0;
                }
                JTable table = new JTable(getQueryTable(queries.get(index) + " LIMIT " +  limit + " OFFSET " + offset));
                if (queryPane.getComponentCount() != 1)
                    queryPane.remove(scrollPane[0]);
                scrollPane[0] = new JScrollPane(table);
                queryPane.add(scrollPane[0], BorderLayout.CENTER);
                queryPane.revalidate();

        });
        queryPane.add(inputPane, BorderLayout.NORTH);
        queryDialog.add(queryPane);
        queryDialog.pack();
        queryDialog.setLocationRelativeTo(this);
        queryDialog.setVisible(true);
    }

    private void openAddCaseDialog() {
        JDialog addCaseDialog = new JDialog();
        addCaseDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addCaseDialog.setTitle("Add New Case");
        addCaseDialog.setModal(true);
        addCaseDialog.setPreferredSize(new Dimension(600, 200));

        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BorderLayout());
        JPanel inputPane = new JPanel();
        inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.X_AXIS));

        ArrayList<JLabel> labels = new ArrayList<>();
        ArrayList<JTextField> textFields = new ArrayList<>();
        Vector<Object> columnNames = new Vector<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM cases LIMIT 0");
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            for (int i = 1; i <= columns; i++) {
                columnNames.addElement(md.getColumnName(i));
            }

            rs.close();
            stmt.close();
        }
        catch(Exception e) {
            //System.out.println(e);
            e.printStackTrace();
        }

        for (int i = 1; i < columnNames.size(); i++) {
            labels.add(new JLabel(columnNames.get(i).toString() + ":"));
            inputPane.add(labels.get(i - 1));
            textFields.add(new JTextField(""));
            inputPane.add(textFields.get(i - 1));
        }

        textFields.get(3).setText("true");

        JButton insertButton = new JButton("Add");

        insertButton.addActionListener(e -> {
            try {
                String query = "INSERT INTO cases (";
                for (int i = 0; i < columnNames.size() - 1; i++) {
                    query = query.concat(columnNames.get(i).toString() + ",");
                }
                query = query.concat(columnNames.get(columnNames.size() - 1).toString() + ") VALUES (nextval('casesCT'),");
                for (int i = 0; i < textFields.size() - 1; i++) {
                    query = query.concat("'" + textFields.get(i).getText() + "',");
                }
                query = query.concat("'" + textFields.get(textFields.size() - 1).getText() + "')");

                if (executeQuery(query))
                {
                    updated = true;
                    try {
                        Statement numStmt = connection.createStatement();
                        String num = "SELECT currval('casesCT')";
                        ResultSet res = numStmt.executeQuery(num);
                        if (res.next()) {
                            serialNum = res.getInt(1);
                            caseName = textFields.get(0).getText();
                            //System.out.println("serialNum = " + serialNum);
                        }
                        numStmt.close();
                    }
                    catch (SQLException exep) {
                        exep.printStackTrace();
                    }
                }
                //System.out.println(query);
                addCaseDialog.dispose();
            }
            catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        });

        inputPane.add(insertButton);

        mainPane.add(inputPane, BorderLayout.NORTH);
        addCaseDialog.add(mainPane);
        addCaseDialog.pack();
        addCaseDialog.setLocationRelativeTo(this);
        addCaseDialog.setVisible(true);
    }

    private void openInsertDialog(String table_name) {
        JDialog insertDialog = new JDialog();
        insertDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        insertDialog.setTitle("Insert");
        insertDialog.setModal(true);
        insertDialog.setPreferredSize(new Dimension(800, 200));

        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BorderLayout());
        JPanel inputPane = new JPanel();
        inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.X_AXIS));

        ArrayList<JLabel> labels = new ArrayList<>();
        ArrayList<JTextField> textFields = new ArrayList<>();
        Vector<Object> columnNames = new Vector<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table_name + " LIMIT 0");
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            for (int i = 1; i <= columns; i++) {
                columnNames.addElement(md.getColumnName(i));
            }

            rs.close();
            stmt.close();
        }
        catch(Exception e) {
            //System.out.println(e);
            e.printStackTrace();
        }

        for (int i = 0; i < columnNames.size(); i++) {
            labels.add(new JLabel(columnNames.get(i).toString() + ":"));
            inputPane.add(labels.get(i));
            textFields.add(new JTextField(""));
            inputPane.add(textFields.get(i));
        }

        JButton insertButton = new JButton("Insert");
        //JButton updateButton = new JButton("Update");
        //JButton deleteButton = new JButton("Delete");

        insertButton.addActionListener(e -> {
            try {
                String query = "INSERT INTO " + table_name + " (";
                for (int i = 0; i < columnNames.size() - 1; i++) {
                    query = query.concat(columnNames.get(i).toString() + ",");
                }
                query = query.concat(columnNames.get(columnNames.size() - 1).toString() + ") VALUES (");
                for (int i = 0; i < textFields.size() - 1; i++) {
                    query = query.concat("'" + textFields.get(i).getText() + "',");
                }
                query = query.concat("'" + textFields.get(textFields.size() - 1).getText() + "')");

                if (executeQuery(query))
                    insertDialog.dispose();
            }
            catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        });

        inputPane.add(insertButton);
        //inputPane.add(updateButton);
        //inputPane.add(deleteButton);

        mainPane.add(inputPane, BorderLayout.NORTH);
        insertDialog.add(mainPane);
        insertDialog.pack();
        insertDialog.setLocationRelativeTo(this);
        insertDialog.setVisible(true);
    }

    /*private void openUpdateFirmsDialog() {
        JDialog updateFirmsDialog = new JDialog();
        updateFirmsDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        updateFirmsDialog.setTitle("Insert");
        updateFirmsDialog.setModal(true);
        updateFirmsDialog.setPreferredSize(new Dimension(800, 200));

        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BorderLayout());
        JPanel inputPane = new JPanel();
        inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.X_AXIS));

        ArrayList<JLabel> labels = new ArrayList<>();
        ArrayList<JTextField> textFields = new ArrayList<>();
        Vector<Object> columnNames = new Vector<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table_name + " LIMIT 0");
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            for (int i = 1; i <= columns; i++) {
                columnNames.addElement(md.getColumnName(i));
            }

            rs.close();
            stmt.close();
        }
        catch(Exception e) {
            //System.out.println(e);
            e.printStackTrace();
        }

        for (int i = 0; i < columnNames.size(); i++) {
            labels.add(new JLabel(columnNames.get(i).toString() + ":"));
            inputPane.add(labels.get(i));
            textFields.add(new JTextField(""));
            inputPane.add(textFields.get(i));
        }

        JButton updateButton = new JButton("Update");

        updateButton.addActionListener(e -> {
            try {
                String query = "INSERT INTO " + table_name + " (";
                for (int i = 0; i < columnNames.size() - 1; i++) {
                    query = query.concat(columnNames.get(i).toString() + ",");
                }
                query = query.concat(columnNames.get(columnNames.size() - 1).toString() + ") VALUES (");
                for (int i = 0; i < textFields.size() - 1; i++) {
                    query = query.concat("'" + textFields.get(i).getText() + "',");
                }
                query = query.concat("'" + textFields.get(textFields.size() - 1).getText() + "')");

                if (executeQuery(query))
                    updateFirmsDialog.dispose();
            }
            catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        });

        inputPane.add(updateButton);

        mainPane.add(inputPane, BorderLayout.NORTH);
        updateFirmsDialog.add(mainPane);
        updateFirmsDialog.pack();
        updateFirmsDialog.setLocationRelativeTo(this);
        updateFirmsDialog.setVisible(true);
    }*/

    private void fillSQL() {
        titles = new ArrayList<>();
        queries = new ArrayList<>();
        titles.add("Lawyer's Name for specific Case");
        titles.add("How Many Cases Each Lawyer Leads");
        titles.add("How Many Participants In Each Case");
        titles.add("How Many Cases Each Firm Leads");
        titles.add("How Senior Citizens Clients");
        titles.add("Chairman Names For All Firms");
        titles.add("Bank Account Balance For All Firms");
        titles.add("All Unpaid Cases");
        titles.add("Paid Sum For Each Client");
        titles.add("Transactions Count Sent To Bank Account");
        queries.add("SELECT case_id, lawyer_name " +
                "FROM cases JOIN lawyers ON case_lawyer_id = lawyer_id " +
                "WHERE case_id = 1");
        queries.add("SELECT lawyers.lawyer_name, COUNT(case_lawyer_id) as cases " +
                "FROM (cases JOIN lawyers ON case_lawyer_id = lawyer_id) " +
                "GROUP BY lawyers.lawyer_name " +
                "ORDER BY cases DESC");
        queries.add("SELECT case_id, COUNT(client_id) as participants " +
                "FROM clients_cases " +
                "GROUP BY  case_id " +
                "ORDER BY participants DESC");
        queries.add("SELECT firm_name, COUNT(case_id) " +
                "FROM (cases JOIN lawyers ON case_lawyer_id = lawyer_id JOIN firms ON lawyer_firm_id = firm_id) " +
                "GROUP BY firm_name");
        queries.add("SELECT client_name, client_age " +
                "FROM clients " +
                "WHERE client_age > 65 " +
                "ORDER BY client_age DESC");
        queries.add("SELECT firm_name, lawyer_name " +
                "FROM (firms JOIN lawyers ON firm_chairman_id = lawyer_id) " +
                "ORDER BY lawyer_age DESC");
        queries.add("SELECT firm_name, bank_account_balance " +
                "FROM (firms INNER JOIN bank_accounts ON firm_bank_account_id = bank_account_id) " +
                "ORDER BY bank_account_balance DESC");
        queries.add("SELECT lawyer_name, cases.case_id, case_cost, SUM(amount) as Payed " +
                "FROM (transactions JOIN cases ON cases.case_id = transactions.case_id JOIN lawyers ON case_lawyer_id = lawyer_id) " +
                "GROUP BY lawyer_name, cases.case_id, case_cost " +
                "HAVING case_cost - SUM(amount) > 0");
        queries.add("SELECT client_name, SUM(amount) " +
                "FROM (transactions JOIN clients ON transactions.client_id = clients.client_id) " +
                "GROUP BY clients.client_id");
        queries.add("SELECT bank_accounts.bank_account_id, COUNT(transaction_id) " +
                "FROM transactions JOIN bank_accounts ON transactions.bank_account_id = bank_accounts.bank_account_id " +
                "GROUP BY bank_accounts.bank_account_id");
    }
}

/*
                            String selectedCase = caseBox.getSelectedItem().toString();
                            String query = "BEGIN;\n" +
                                    "INSERT INTO clients (client_id, client_name, client_age) VALUES\n" +
          *//*prepared statement*//*                          "(nextval('clientsCT'), '" + nameField.getText() +"', " + age +");\n" +
                                    "INSERT INTO clients_cases (client_id, case_id) VALUES (currval('clientsCT'), "
                                    + selectedCase +");\n" +
                                    "COMMIT;";
                            executeQuery(query);*/