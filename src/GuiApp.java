import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class GuiApp extends JFrame{

    // DB config
    private static final String DB_URL  = "jdbc:mysql://138.49.184.123:3306/byrne8370_SONAR";
    private static final String DB_USER = "pieper.hans";
    private static final String DB_PASS = "DHB7E-RkrS3JrgNdT";

    // Card names — used to identify each form in the CardLayout
    private static final String CARD_MEMBERS = "MEMBERS";
    private static final String CARD_EVENTS = "EVENTS";
    private static final String CARD_SPEAKERS = "SPEAKERS";
    private static final String CARD_DUES = "DUES";
    private static final String CARD_ATTENDANCE = "ATTENDANCE";
    private static final String CARD_REPORTS = "REPORTS";

    // CardLayout + its container
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel  = new JPanel(cardLayout);

    // Toolbar nav buttons
    private final JButton navMembersBtn = new JButton("👤  Members");
    private final JButton navEventsBtn = new JButton("👤  Events");
    private final JButton navSpeakersBtn = new JButton("👤  Speakers");
    private final JButton navDuesBtn = new JButton("👤  Dues");
    private final JButton navAttendanceBtn = new JButton("👤  Attendance");
    private final JButton navReportsBtn = new JButton("👤  Reports");

    // Status bar (shared across all forms)
    private final JLabel statusLabel = new JLabel("Ready");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GuiApp().setVisible(true));
    }

    public GuiApp() {
        super("SONAR App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1400, 1000);
        setLocationRelativeTo(null);

        initDatabase();
        buildUI();
        wireNavListeners();   // ← toolbar navigation listeners
    }
    // DB setup
    private void initDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
        } catch (SQLException ex) {
            showError("DB init failed: " + ex.getMessage());
        }
    }
    //UI setup
    private void buildUI() {
        // Toolbar
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.setBackground(new Color(45, 45, 48));
        styleNavButton(navMembersBtn, true);   // starts active
        styleNavButton(navEventsBtn,   false);
        styleNavButton(navSpeakersBtn,   false);
        styleNavButton(navDuesBtn,   false);
        styleNavButton(navAttendanceBtn,   false);
        styleNavButton(navReportsBtn,   false);
        toolbar.add(navMembersBtn);
        toolbar.addSeparator(new Dimension(4, 0));
        toolbar.add(navEventsBtn);
        toolbar.addSeparator(new Dimension(4, 0));
        toolbar.add(navSpeakersBtn);
        toolbar.addSeparator(new Dimension(4, 0));
        toolbar.add(navDuesBtn);
        toolbar.addSeparator(new Dimension(4, 0));
        toolbar.add(navAttendanceBtn);
        toolbar.addSeparator(new Dimension(4, 0));
        toolbar.add(navReportsBtn);

        // Register each form as a named card
        cardPanel.add(new MembersPanel(), CARD_MEMBERS);
        cardPanel.add(new EventsPanel(), CARD_EVENTS);
        cardPanel.add(new SpeakersPanel(), CARD_SPEAKERS);
        cardPanel.add(new DuesPanel(), CARD_DUES);
        cardPanel.add(new AttendancePanel(), CARD_ATTENDANCE);
        cardPanel.add(new ReportsPanel(), CARD_REPORTS);

        // Status bar
        statusLabel.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
        statusLabel.setFont(statusLabel.getFont().deriveFont(Font.ITALIC));

        setLayout(new BorderLayout());
        add(toolbar,     BorderLayout.NORTH);
        add(cardPanel,   BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        // Show first card
        cardLayout.show(cardPanel, CARD_MEMBERS);
    }

    //Nav Listeners
    private void wireNavListeners() {

        navMembersBtn.addActionListener(e -> {
            cardLayout.show(cardPanel, CARD_MEMBERS);   // flip to Members card
            styleNavButton(navMembersBtn, true);
            styleNavButton(navEventsBtn,   false);
            styleNavButton(navSpeakersBtn,   false);
            styleNavButton(navDuesBtn,   false);
            styleNavButton(navAttendanceBtn,   false);
            styleNavButton(navReportsBtn,   false);
            setStatus("Members form");
        });

        navEventsBtn.addActionListener(e -> {
            cardLayout.show(cardPanel, CARD_EVENTS);   // flip to Members card
            styleNavButton(navEventsBtn, true);
            styleNavButton(navMembersBtn,   false);
            styleNavButton(navSpeakersBtn,   false);
            styleNavButton(navDuesBtn,   false);
            styleNavButton(navAttendanceBtn,   false);
            styleNavButton(navReportsBtn,   false);
            setStatus("Events form");
        });

        navSpeakersBtn.addActionListener(e -> {
            cardLayout.show(cardPanel, CARD_SPEAKERS);   // flip to Speakers card
            styleNavButton(navSpeakersBtn, true);
            styleNavButton(navEventsBtn,   false);
            styleNavButton(navMembersBtn,   false);
            styleNavButton(navDuesBtn,   false);
            styleNavButton(navAttendanceBtn,   false);
            styleNavButton(navReportsBtn,   false);
            setStatus("Speakers form");
        });

        navDuesBtn.addActionListener(e -> {
            cardLayout.show(cardPanel, CARD_DUES);   // flip to Dues card
            styleNavButton(navDuesBtn, true);
            styleNavButton(navEventsBtn,   false);
            styleNavButton(navSpeakersBtn,   false);
            styleNavButton(navMembersBtn,   false);
            styleNavButton(navAttendanceBtn,   false);
            styleNavButton(navReportsBtn,   false);
            setStatus("Dues form");
        });

        navAttendanceBtn.addActionListener(e -> {
            cardLayout.show(cardPanel, CARD_ATTENDANCE);     // flip to SQL Runner card
            styleNavButton(navAttendanceBtn,   true);
            styleNavButton(navMembersBtn, false);
            styleNavButton(navEventsBtn,   false);
            styleNavButton(navSpeakersBtn,   false);
            styleNavButton(navDuesBtn,   false);
            styleNavButton(navReportsBtn,   false);
            setStatus("Attendance");
        });

        navReportsBtn.addActionListener(e -> {
            cardLayout.show(cardPanel, CARD_REPORTS);     // flip to SQL Runner card
            styleNavButton(navReportsBtn,   true);
            styleNavButton(navMembersBtn, false);
            styleNavButton(navEventsBtn,   false);
            styleNavButton(navSpeakersBtn,   false);
            styleNavButton(navDuesBtn,   false);
            styleNavButton(navAttendanceBtn,   false);
            setStatus("Reports");
        });
    }
    //Toolbar button styling
    private void styleNavButton(JButton btn, boolean active) {
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setFont(btn.getFont().deriveFont(Font.BOLD, 13f));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setForeground(Color.WHITE);
        btn.setBackground(active ? new Color(0, 122, 204) : new Color(45, 45, 48));
    }

    //Helpers
    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
    void setStatus(String msg) { statusLabel.setText(msg); }
    void showError(String msg) { JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE); }
    void showWarning(String msg) { JOptionPane.showMessageDialog(this, msg, "Validation", JOptionPane.WARNING_MESSAGE); }


    class MembersPanel extends JPanel {

        private final JTextField memberIdField = new JTextField(5);
        private final JTextField mFNameField = new JTextField(10);
        private final JTextField mLNameField = new JTextField(10);
        private final JTextField mEmailField = new JTextField(10);
        private final JTextField majorField = new JTextField(10);
        private final JTextField statusField = new JTextField(10);
        private final JTextField mStreetField = new JTextField(10);
        private final JTextField mCityField = new JTextField(10);
        private final JTextField mStateField = new JTextField(10);
        private final JTextField mZipField = new JTextField(5);
        private final JTextField startDateField = new JTextField(10);
        private final JTextField endDateField = new JTextField(10);

        private final JButton insertBtn = new JButton("Insert");
        private final JButton searchBtn = new JButton("Search");
        private final JButton updateBtn = new JButton("Update");
        private final JButton deleteBtn = new JButton("Delete");
        private final JButton clearBtn = new JButton("Clear");
        private final JButton loadBtn = new JButton("Load All");

        private final DefaultTableModel tableModel = new DefaultTableModel(
                new String[]{"ID", "First Name", "Last Name", "Email", "Major", "Status", "Street Address", "City", "State", "Zip", "Start Date","End Date"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        private final JTable table = new JTable(tableModel);

        MembersPanel() {
            setLayout(new BorderLayout(0, 6));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            buildUI();
            wireListeners();
            loadAll();
        }

        private void buildUI() {
            // Form
            JPanel form = new JPanel(new GridBagLayout());
            form.setBorder(BorderFactory.createTitledBorder("Member Record"));
            GridBagConstraints g = new GridBagConstraints();
            g.insets = new Insets(4, 6, 4, 6);
            g.anchor = GridBagConstraints.WEST;
            addRow(form, g, 0, "ID (for search / update / delete):", memberIdField);
            addRow(form, g, 1, "First Name:", mFNameField);
            addRow(form, g, 2, "Last Name:", mLNameField);
            addRow(form, g, 3, "Email:", mEmailField);
            addRow(form, g, 4, "Major:", majorField);
            addRow(form, g, 5, "Status:", statusField);
            addRow(form, g, 6, "Street Address:", mStreetField);
            addRow(form, g, 7, "City:", mCityField);
            addRow(form, g, 8, "State:", mStateField);
            addRow(form, g, 9, "Zip:", mZipField);
            addRow(form, g, 10, "Start Date:", startDateField);
            addRow(form, g, 11, "End Date:", endDateField);

            // Buttons
            JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 4));
            for (JButton b : new JButton[]{insertBtn, searchBtn, updateBtn, deleteBtn, clearBtn, loadBtn})
                btns.add(b);

            JPanel top = new JPanel(new BorderLayout());
            top.add(form, BorderLayout.CENTER);
            top.add(btns, BorderLayout.SOUTH);

            // Table
            table.setRowHeight(24);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scroll = new JScrollPane(table);
            scroll.setBorder(BorderFactory.createTitledBorder("Results"));

            add(top, BorderLayout.NORTH);
            add(scroll, BorderLayout.CENTER);
        }

        // CRUD listeners
        private void wireListeners() {

            // INSERT
            insertBtn.addActionListener(e -> {
                String mFName = mFNameField.getText().trim();
                String mLName = mLNameField.getText().trim();
                String mEmail = mEmailField.getText().trim();
                String major = majorField.getText().trim();
                String status = statusField.getText().trim();
                String mStreet = mStreetField.getText().trim();
                String mCity = mCityField.getText().trim();
                String mState = mStateField.getText().trim();
                String mZip = mZipField.getText().trim();
                Date startDate = Date.valueOf(startDateField.getText());
                Date endDate = Date.valueOf(endDateField.getText());


                if (mFName.isEmpty() || mEmail.isEmpty()) {
                    showWarning("Name and Email are required.");
                    return;
                }
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "INSERT INTO Member (MFName, MLName, MEmail, Major, Status, MStreet, MCity, MState, MZip, StartDate, EndDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                    ps.setString(1, mFName);
                    ps.setString(2, mLName);
                    ps.setString(3, mEmail);
                    ps.setString(4, major);
                    ps.setString(5, status);
                    ps.setString(6, mStreet);
                    ps.setString(7, mCity);
                    ps.setString(8, mState);
                    ps.setString(9, mZip);
                    ps.setDate(10, startDate);
                    ps.setDate(11, endDate);
                    ps.executeUpdate();
                    setStatus("Member inserted.");
                    clearFields();
                    loadAll();
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // SEARCH
            searchBtn.addActionListener(e -> {
                String idText = memberIdField.getText().trim();
                if (idText.isEmpty()) { loadAll(); return; }
                if (!validId(idText))  return;
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "SELECT MemberId, MFName, MLName, MEmail, Major, Status, MStreet, MCity, MState, MZip, StartDate, EndDate FROM Member WHERE MemberId = ?")) {
                    ps.setInt(1, Integer.parseInt(idText));
                    ResultSet rs = ps.executeQuery();
                    tableModel.setRowCount(0);
                    int n = 0;
                    while (rs.next()) {
                        tableModel.addRow(new Object[]{rs.getInt("MemberId"), rs.getString("MFName"),
                                rs.getString("MLName"), rs.getString("MEmail"), rs.getString("Major"),
                                rs.getString("Status"), rs.getString("MStreet"), rs.getString("MCity"),
                                rs.getString("MState"), rs.getString("mZip"), rs.getDate("StartDate"),
                                rs.getDate("EndDate")});
                        n++;
                    }
                    setStatus(n > 0 ? "Found " + n + " record(s)." : "No record for ID " + idText);
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // UPDATE
            updateBtn.addActionListener(e -> {
                String idText = memberIdField.getText().trim();
                String mFName = mFNameField.getText().trim();
                String mLName = mLNameField.getText().trim();
                String mEmail = mEmailField.getText().trim();
                String major = majorField.getText().trim();
                String status = statusField.getText().trim();
                String mStreet = mStreetField.getText().trim();
                String mCity = mCityField.getText().trim();
                String mState = mStateField.getText().trim();
                String mZip = mZipField.getText().trim();
                Date startDate = Date.valueOf(startDateField.getText());
                Date endDate = Date.valueOf(endDateField.getText());

                if (idText.isEmpty()) { showWarning("Enter an ID to update."); return; }
                if (!validId(idText)) return;
                if (mFName.isEmpty() || mEmail.isEmpty()) { showWarning("First Name and Email required."); return; }
                if (confirm("Update record ID " + idText + "?") != JOptionPane.YES_OPTION) return;
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "UPDATE Member SET MFName=?, MLName=?, MEmail=?, Major=?, Status=?, MStreet=?, MCity=?, MState=?, MZip=?, StartDate=?, EndDate=? WHERE MemberId=?")) {
                    ps.setString(1, mFName);
                    ps.setString(2, mLName);
                    ps.setString(3, mEmail);
                    ps.setString(4, major);
                    ps.setString(5, status);
                    ps.setString(6, mStreet);
                    ps.setString(7, mCity);
                    ps.setString(8, mState);
                    ps.setString(9, mZip);
                    ps.setDate(10, startDate);
                    ps.setDate(11, endDate);
                    ps.setInt(12, Integer.parseInt(idText));
                    int rows = ps.executeUpdate();
                    setStatus(rows > 0 ? "Updated ID " + idText : "No record found.");
                    if (rows > 0) { clearFields(); loadAll(); }
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // DELETE
            deleteBtn.addActionListener(e -> {
                String idText = memberIdField.getText().trim();
                if (idText.isEmpty()) { showWarning("Enter an ID to delete."); return; }
                if (!validId(idText)) return;
                if (confirm("Delete record ID " + idText + "?") != JOptionPane.YES_OPTION) return;
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "DELETE FROM Member WHERE memberId=?")) {
                    ps.setInt(1, Integer.parseInt(idText));
                    int rows = ps.executeUpdate();
                    setStatus(rows > 0 ? "Deleted ID " + idText : "No record found.");
                    if (rows > 0) { clearFields(); loadAll(); }
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // CLEAR
            clearBtn.addActionListener(e -> { clearFields(); setStatus("Cleared."); });

            // LOAD ALL
            loadBtn.addActionListener(e -> { clearFields(); loadAll(); });

            // Row click → populate form
            table.getSelectionModel().addListSelectionListener(e -> {
                if (e.getValueIsAdjusting()) return;
                int row = table.getSelectedRow();
                if (row < 0) return;
                memberIdField.setText(tableModel.getValueAt(row, 0).toString());
                mFNameField.setText(tableModel.getValueAt(row, 1).toString());
                mLNameField.setText(tableModel.getValueAt(row, 2).toString());
                mEmailField.setText(tableModel.getValueAt(row, 3).toString());
                majorField.setText(tableModel.getValueAt(row, 4).toString());
                statusField.setText(tableModel.getValueAt(row, 5).toString());
                mStreetField.setText(tableModel.getValueAt(row, 6).toString());
                mCityField.setText(tableModel.getValueAt(row, 7).toString());
                mStateField.setText(tableModel.getValueAt(row, 8).toString());
                mZipField.setText(tableModel.getValueAt(row, 9).toString());
                startDateField.setText(tableModel.getValueAt(row, 10).toString());
                endDateField.setText(tableModel.getValueAt(row, 11).toString());
            });
        }

        private void loadAll() {
            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs   = stmt.executeQuery("SELECT MemberId, MFName, MLName, MEmail, Major, Status, MStreet, MCity, MState, MZip, StartDate, EndDate FROM Member ORDER BY MemberId")) {
                tableModel.setRowCount(0);
                int n = 0;
                while (rs.next()) {
                    tableModel.addRow(new Object[]{rs.getInt("MemberId"), rs.getString("MFName"),
                            rs.getString("MLName"), rs.getString("MEmail"), rs.getString("Major"),
                            rs.getString("Status"), rs.getString("MStreet"), rs.getString("MCity"),
                            rs.getString("MState"), rs.getString("MZip"), rs.getDate("StartDate"),
                            rs.getDate("EndDate")});
                    n++;
                }
                setStatus("Loaded " + n + " member(s).");
            } catch (SQLException ex) { showError(ex.getMessage()); }
        }

        private void clearFields() {
            memberIdField.setText(""); mFNameField.setText(""); mLNameField.setText("");
            mEmailField.setText(""); majorField.setText(""); statusField.setText("");
            mStreetField.setText(""); mCityField.setText(""); mStateField.setText("");
            mZipField.setText(""); startDateField.setText(""); endDateField.setText("");
            table.clearSelection();
        }

        private boolean validId(String t) {
            try { if (Integer.parseInt(t) > 0) return true; } catch (NumberFormatException ignored) {}
            showWarning("ID must be a positive integer.");
            return false;
        }

        private int confirm(String msg) {
            return JOptionPane.showConfirmDialog(GuiApp.this, msg, "Confirm", JOptionPane.YES_NO_OPTION);
        }

        private void addRow(JPanel p, GridBagConstraints g, int row, String label, JComponent field) {
            g.gridx = 0; g.gridy = row; g.weightx = 0; g.fill = GridBagConstraints.NONE;
            p.add(new JLabel(label), g);
            g.gridx = 1; g.weightx = 1; g.fill = GridBagConstraints.HORIZONTAL;
            p.add(field, g);
        }
    }

    class EventsPanel extends JPanel {

        private final JTextField eventIdField = new JTextField(5);
        private final JTextField eNameField = new JTextField(5);
        private final JTextField speakerIdField = new JTextField(10);
        private final JTextField eStreetField = new JTextField(10);
        private final JTextField eCityField = new JTextField(10);
        private final JTextField eStateField = new JTextField(10);
        private final JTextField eZipField = new JTextField(5);
        private final JTextField eDateField = new JTextField(10);
        private final JTextField costField = new JTextField(10);

        private final JButton insertBtn = new JButton("Insert");
        private final JButton searchBtn = new JButton("Search");
        private final JButton updateBtn = new JButton("Update");
        private final JButton deleteBtn = new JButton("Delete");
        private final JButton clearBtn = new JButton("Clear");
        private final JButton loadBtn = new JButton("Load All");

        private final DefaultTableModel tableModel = new DefaultTableModel(
                new String[]{"Event ID", "Event Name", "Speaker ID", "Street Address", "City", "State", "Zip", "Date", "Cost"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        private final JTable table = new JTable(tableModel);

        EventsPanel() {
            setLayout(new BorderLayout(0, 6));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            buildUI();
            wireListeners();
            loadAll();
        }

        private void buildUI() {
            // Form
            JPanel form = new JPanel(new GridBagLayout());
            form.setBorder(BorderFactory.createTitledBorder("Event Record"));
            GridBagConstraints g = new GridBagConstraints();
            g.insets = new Insets(4, 6, 4, 6);
            g.anchor = GridBagConstraints.WEST;
            addRow(form, g, 0, "Event ID (for search / update / delete):", eventIdField);
            addRow(form, g, 1, "Event Name:", eNameField);
            addRow(form, g, 2, "Speaker Id:", speakerIdField);
            addRow(form, g, 3, "Street Address:", eStreetField);
            addRow(form, g, 4, "City:", eCityField);
            addRow(form, g, 5, "State:", eStateField);
            addRow(form, g, 6, "Zip:", eZipField);
            addRow(form, g, 7, "Event Date:", eDateField);
            addRow(form, g, 8, "Cost:", costField);

            // Buttons
            JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 4));
            for (JButton b : new JButton[]{insertBtn, searchBtn, updateBtn, deleteBtn, clearBtn, loadBtn})
                btns.add(b);

            JPanel top = new JPanel(new BorderLayout());
            top.add(form, BorderLayout.CENTER);
            top.add(btns, BorderLayout.SOUTH);

            // Table
            table.setRowHeight(24);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scroll = new JScrollPane(table);
            scroll.setBorder(BorderFactory.createTitledBorder("Results"));

            add(top, BorderLayout.NORTH);
            add(scroll, BorderLayout.CENTER);
        }

        // CRUD listeners
        private void wireListeners() {

            // INSERT
            insertBtn.addActionListener(e -> {
                String eName = eNameField.getText().trim();
                String speakerId = speakerIdField.getText().trim();
                String eStreet = eStreetField.getText().trim();
                String eCity = eCityField.getText().trim();
                String eState = eStateField.getText().trim();
                String eZip  = eZipField.getText().trim();
                Date eDate = Date.valueOf(eDateField.getText());
                double cost = Double.parseDouble(costField.getText());

                if (eStreet.isEmpty() || eCity.isEmpty()) {
                    showWarning("Street and City are required.");
                    return;
                }
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "INSERT INTO Event (EName, SpeakerId, EStreet, ECity, EState, EZip, EDate, Cost) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
                    ps.setString(1, eName);
                    ps.setInt(2, Integer.parseInt(speakerId));
                    ps.setString(3, eStreet);
                    ps.setString(4, eCity);
                    ps.setString(5, eState);
                    ps.setString(6, eZip);
                    ps.setDate(7, eDate);
                    ps.setDouble(8, cost);
                    ps.executeUpdate();
                    setStatus("Event inserted.");
                    clearFields();
                    loadAll();
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // SEARCH
            searchBtn.addActionListener(e -> {
                String idText = eventIdField.getText().trim();
                if (idText.isEmpty()) { loadAll(); return; }
                if (!validId(idText))  return;
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "SELECT EventId, EName, SpeakerId, EStreet, ECity, EState, EZip, EDate, Cost FROM Event WHERE EventId = ?")) {
                    ps.setInt(1, Integer.parseInt(idText));
                    ResultSet rs = ps.executeQuery();
                    tableModel.setRowCount(0);
                    int n = 0;
                    while (rs.next()) {
                        tableModel.addRow(new Object[]{rs.getInt("EventId"),  rs.getString("EName"), rs.getInt("SpeakerId"),
                                rs.getString("EStreet"), rs.getString("ECity"), rs.getString("EState"),
                                rs.getString("EZip"), rs.getDate("EDate"), rs.getDouble("Cost")});
                        n++;
                    }
                    setStatus(n > 0 ? "Found " + n + " record(s)." : "No record for ID " + idText);
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // UPDATE
            updateBtn.addActionListener(e -> {
                String idText = eventIdField.getText().trim();
                String eName = eNameField.getText().trim();
                String speakerId = speakerIdField.getText().trim();
                String eStreet = eStreetField.getText().trim();
                String eCity = eCityField.getText().trim();
                String eState = eStateField.getText().trim();
                String eZip = eZipField.getText().trim();
                Date eventDate = Date.valueOf(eDateField.getText());
                double cost = Double.parseDouble(costField.getText());

                if (idText.isEmpty()) { showWarning("Enter an ID to update."); return; }
                if (!validId(idText)) return;
                if (eStreet.isEmpty() || eCity.isEmpty()) { showWarning("First Name and Email required."); return; }
                if (confirm("Update record ID " + idText + "?") != JOptionPane.YES_OPTION) return;
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "UPDATE Event SET EName=?, SpeakerId=?, EStreet=?, ECity=?, EState=?, EZip=?, EDate=?, Cost=? WHERE EventId=?")) {
                    ps.setString(1, eName);
                    ps.setInt(2, Integer.parseInt(speakerId));
                    ps.setString(3, eStreet);
                    ps.setString(4, eCity);
                    ps.setString(5, eState);
                    ps.setString(6, eZip);
                    ps.setDate(7, eventDate);
                    ps.setDouble(8, cost);
                    ps.setInt(9, Integer.parseInt(idText));
                    int rows = ps.executeUpdate();
                    setStatus(rows > 0 ? "Updated ID " + idText : "No record found.");
                    if (rows > 0) { clearFields(); loadAll(); }
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // DELETE
            deleteBtn.addActionListener(e -> {
                String idText = eventIdField.getText().trim();
                if (idText.isEmpty()) { showWarning("Enter an ID to delete."); return; }
                if (!validId(idText)) return;
                if (confirm("Delete record ID " + idText + "?") != JOptionPane.YES_OPTION) return;
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "DELETE FROM Event WHERE eventId=?")) {
                    ps.setInt(1, Integer.parseInt(idText));
                    int rows = ps.executeUpdate();
                    setStatus(rows > 0 ? "Deleted ID " + idText : "No record found.");
                    if (rows > 0) { clearFields(); loadAll(); }
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // CLEAR
            clearBtn.addActionListener(e -> { clearFields(); setStatus("Cleared."); });

            // LOAD ALL
            loadBtn.addActionListener(e -> { clearFields(); loadAll(); });

            // Row click → populate form
            table.getSelectionModel().addListSelectionListener(e -> {
                if (e.getValueIsAdjusting()) return;
                int row = table.getSelectedRow();
                if (row < 0) return;
                eventIdField.setText(tableModel.getValueAt(row, 0).toString());
                eNameField.setText(tableModel.getValueAt(row, 1).toString());
                speakerIdField.setText(tableModel.getValueAt(row, 2).toString());
                eStreetField.setText(tableModel.getValueAt(row, 3).toString());
                eCityField.setText(tableModel.getValueAt(row, 4).toString());
                eStateField.setText(tableModel.getValueAt(row, 5).toString());
                eZipField.setText(tableModel.getValueAt(row, 6).toString());
                eDateField.setText(tableModel.getValueAt(row, 7).toString());
                costField.setText(tableModel.getValueAt(row, 8).toString());
            });
        }

        private void loadAll() {
            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs   = stmt.executeQuery("SELECT EventId, EName, SpeakerId, EStreet, ECity, EState,  EZip, EDate, Cost FROM Event ORDER BY EventId")) {
                tableModel.setRowCount(0);
                int n = 0;
                while (rs.next()) {
                    tableModel.addRow(new Object[]{rs.getInt("EventId"), rs.getString("EName"),  rs.getString("SpeakerId"),
                            rs.getString("EStreet"), rs.getString("ECity"), rs.getString("EState"),
                            rs.getString("EZip"), rs.getDate("EDate"), rs.getDouble("Cost")});
                    n++;
                }
                setStatus("Loaded " + n + " event(s).");
            } catch (SQLException ex) { showError(ex.getMessage()); }
        }

        private void clearFields() {
            eventIdField.setText(""); eNameField.setText(""); speakerIdField.setText("");
            eStreetField.setText(""); eCityField.setText(""); eStateField.setText("");
            eZipField.setText(""); eDateField.setText(""); costField.setText("");
            table.clearSelection();
        }

        private boolean validId(String t) {
            try { if (Integer.parseInt(t) > 0) return true; } catch (NumberFormatException ignored) {}
            showWarning("ID must be a positive integer.");
            return false;
        }

        private int confirm(String msg) {
            return JOptionPane.showConfirmDialog(GuiApp.this, msg, "Confirm", JOptionPane.YES_NO_OPTION);
        }

        private void addRow(JPanel p, GridBagConstraints g, int row, String label, JComponent field) {
            g.gridx = 0; g.gridy = row; g.weightx = 0; g.fill = GridBagConstraints.NONE;
            p.add(new JLabel(label), g);
            g.gridx = 1; g.weightx = 1; g.fill = GridBagConstraints.HORIZONTAL;
            p.add(field, g);
        }
    }

    class SpeakersPanel extends JPanel {

        private final JTextField speakerIdField = new JTextField(5);
        private final JTextField sFNameField = new JTextField(10);
        private final JTextField sLNameField = new JTextField(10);
        private final JTextField sEmailField = new JTextField(10);
        private final JTextField titleField = new JTextField(10);
        private final JTextField industryField = new JTextField(10);
        private final JTextField sStreetField = new JTextField(10);
        private final JTextField sCityField = new JTextField(10);
        private final JTextField sStateField = new JTextField(10);
        private final JTextField sZipField = new JTextField(5);
        private final JTextField sCostField = new JTextField(10);

        private final JButton insertBtn = new JButton("Insert");
        private final JButton searchBtn = new JButton("Search");
        private final JButton updateBtn = new JButton("Update");
        private final JButton deleteBtn = new JButton("Delete");
        private final JButton clearBtn = new JButton("Clear");
        private final JButton loadBtn = new JButton("Load All");

        private final DefaultTableModel tableModel = new DefaultTableModel(
                new String[]{"SpeakerId", "First Name", "Last Name", "Email", "Title", "Industry", "Street Address", "City", "State", "Zip", "Cost"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        private final JTable table = new JTable(tableModel);

        SpeakersPanel() {
            setLayout(new BorderLayout(0, 6));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            buildUI();
            wireListeners();
            loadAll();
        }

        private void buildUI() {
            // Form
            JPanel form = new JPanel(new GridBagLayout());
            form.setBorder(BorderFactory.createTitledBorder("Speaker Record"));
            GridBagConstraints g = new GridBagConstraints();
            g.insets = new Insets(4, 6, 4, 6);
            g.anchor = GridBagConstraints.WEST;
            addRow(form, g, 0, "ID (for search / update / delete):", speakerIdField);
            addRow(form, g, 1, "First Name:", sFNameField);
            addRow(form, g, 2, "Last Name:", sLNameField);
            addRow(form, g, 3, "Email:", sEmailField);
            addRow(form, g, 4, "Title:", titleField);
            addRow(form, g, 5, "Industry:", industryField);
            addRow(form, g, 6, "Street Address:", sStreetField);
            addRow(form, g, 7, "City:", sCityField);
            addRow(form, g, 8, "State:", sStateField);
            addRow(form, g, 9, "Zip:", sZipField);
            addRow(form, g, 10, "Cost:", sCostField);

            // Buttons
            JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 4));
            for (JButton b : new JButton[]{insertBtn, searchBtn, updateBtn, deleteBtn, clearBtn, loadBtn})
                btns.add(b);

            JPanel top = new JPanel(new BorderLayout());
            top.add(form, BorderLayout.CENTER);
            top.add(btns, BorderLayout.SOUTH);

            // Table
            table.setRowHeight(24);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scroll = new JScrollPane(table);
            scroll.setBorder(BorderFactory.createTitledBorder("Results"));

            add(top, BorderLayout.NORTH);
            add(scroll, BorderLayout.CENTER);
        }

        // CRUD listeners
        private void wireListeners() {

            // INSERT
            insertBtn.addActionListener(e -> {
                String sFName = sFNameField.getText().trim();
                String sLName = sLNameField.getText().trim();
                String sEmail = sEmailField.getText().trim();
                String title = titleField.getText().trim();
                String industry = industryField.getText().trim();
                String sStreet = sStreetField.getText().trim();
                String sCity = sCityField.getText().trim();
                String sState = sStateField.getText().trim();
                String sZip = sZipField.getText().trim();
                double sCost = Double.parseDouble(sCostField.getText());

                if (sFName.isEmpty() || sEmail.isEmpty()) {
                    showWarning("Name and Email are required.");
                    return;
                }
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "INSERT INTO Speaker (SFName, SLName, SEmail, Title, Industry, SStreet, SCity, SState, SZip, SCost) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                    ps.setString(1, sFName);
                    ps.setString(2, sLName);
                    ps.setString(3, sEmail);
                    ps.setString(4, title);
                    ps.setString(5, industry);
                    ps.setString(6, sStreet);
                    ps.setString(7, sCity);
                    ps.setString(8, sState);
                    ps.setString(9, sZip);
                    ps.setDouble(10, sCost);
                    ps.executeUpdate();
                    setStatus("Speaker inserted.");
                    clearFields();
                    loadAll();
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // SEARCH
            searchBtn.addActionListener(e -> {
                String idText = speakerIdField.getText().trim();
                if (idText.isEmpty()) { loadAll(); return; }
                if (!validId(idText))  return;
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "SELECT SpeakerId, MFName, MLName, MEmail, Major, Status, MStreet, MCity, MState, MZip, StartDate, EndDate FROM Speaker WHERE SpeakerId = ?")) {
                    ps.setInt(1, Integer.parseInt(idText));
                    ResultSet rs = ps.executeQuery();
                    tableModel.setRowCount(0);
                    int n = 0;
                    while (rs.next()) {
                        tableModel.addRow(new Object[]{rs.getInt("SpeakerId"), rs.getString("SFName"),
                                rs.getString("SLName"), rs.getString("SEmail"), rs.getString("Title"),
                                rs.getString("Industry"), rs.getString("SStreet"), rs.getString("SCity"),
                                rs.getString("SState"), rs.getString("SZip"), rs.getDouble("SCost")});
                        n++;
                    }
                    setStatus(n > 0 ? "Found " + n + " record(s)." : "No record for ID " + idText);
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // UPDATE
            updateBtn.addActionListener(e -> {
                String idText = speakerIdField.getText().trim();
                String sFName = sFNameField.getText().trim();
                String sLName = sLNameField.getText().trim();
                String sEmail = sEmailField.getText().trim();
                String title = titleField.getText().trim();
                String industry = industryField.getText().trim();
                String sStreet = sStreetField.getText().trim();
                String sCity = sCityField.getText().trim();
                String sState = sStateField.getText().trim();
                String sZip = sZipField.getText().trim();
                double sCost = Double.parseDouble(sCostField.getText());

                if (idText.isEmpty()) { showWarning("Enter an ID to update."); return; }
                if (!validId(idText)) return;
                if (sFName.isEmpty() || sEmail.isEmpty()) { showWarning("First Name and Email required."); return; }
                if (confirm("Update record ID " + idText + "?") != JOptionPane.YES_OPTION) return;
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "UPDATE Speaker SET SFName=?, SLName=?, SEmail=?, Title=?, Industry=?, SStreet=?, SCity=?, SState=?, SZip=?, SCost=? WHERE SpeakerId=?")) {
                    ps.setString(1, sFName);
                    ps.setString(2, sLName);
                    ps.setString(3, sEmail);
                    ps.setString(4, title);
                    ps.setString(5, industry);
                    ps.setString(6, sStreet);
                    ps.setString(7, sCity);
                    ps.setString(8, sState);
                    ps.setString(9, sZip);
                    ps.setDouble(10, sCost);
                    ps.setInt(11, Integer.parseInt(idText));
                    int rows = ps.executeUpdate();
                    setStatus(rows > 0 ? "Updated ID " + idText : "No record found.");
                    if (rows > 0) { clearFields(); loadAll(); }
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // DELETE
            deleteBtn.addActionListener(e -> {
                String idText = speakerIdField.getText().trim();
                if (idText.isEmpty())  { showWarning("Enter an ID to delete."); return; }
                if (!validId(idText))   return;
                if (confirm("Delete record ID " + idText + "?") != JOptionPane.YES_OPTION) return;
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "DELETE FROM Speaker WHERE speakerId=?")) {
                    ps.setInt(1, Integer.parseInt(idText));
                    int rows = ps.executeUpdate();
                    setStatus(rows > 0 ? "Deleted ID " + idText : "No record found.");
                    if (rows > 0) { clearFields(); loadAll(); }
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // CLEAR
            clearBtn.addActionListener(e -> { clearFields(); setStatus("Cleared."); });

            // LOAD ALL
            loadBtn.addActionListener(e -> { clearFields(); loadAll(); });

            // Row click → populate form
            table.getSelectionModel().addListSelectionListener(e -> {
                if (e.getValueIsAdjusting()) return;
                int row = table.getSelectedRow();
                if (row < 0) return;
                speakerIdField.setText(tableModel.getValueAt(row, 0).toString());
                sFNameField.setText(tableModel.getValueAt(row, 1).toString());
                sLNameField.setText(tableModel.getValueAt(row, 2).toString());
                sEmailField.setText(tableModel.getValueAt(row, 3).toString());
                titleField.setText(tableModel.getValueAt(row, 4).toString());
                industryField.setText(tableModel.getValueAt(row, 5).toString());
                sStreetField.setText(tableModel.getValueAt(row, 6).toString());
                sCityField.setText(tableModel.getValueAt(row, 7).toString());
                sStateField.setText(tableModel.getValueAt(row, 8).toString());
                sZipField.setText(tableModel.getValueAt(row, 9).toString());
                sCostField.setText(tableModel.getValueAt(row, 10).toString());
            });
        }

        private void loadAll() {
            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT SpeakerId, SFName, SLName, SEmail, Title, Industry, SStreet, SCity, SState, SZip, SCost FROM Speaker ORDER BY SpeakerId")) {
                tableModel.setRowCount(0);
                int n = 0;
                while (rs.next()) {
                    tableModel.addRow(new Object[]{rs.getInt("SpeakerId"), rs.getString("SFName"),
                            rs.getString("SLName"), rs.getString("SEmail"), rs.getString("Title"),
                            rs.getString("Industry"), rs.getString("SStreet"), rs.getString("SCity"),
                            rs.getString("SState"), rs.getString("SZip"), rs.getDouble("SCost")});
                    n++;
                }
                setStatus("Loaded " + n + " Speaker(s).");
            } catch (SQLException ex) { showError(ex.getMessage()); }
        }

        private void clearFields() {
            speakerIdField.setText(""); sFNameField.setText(""); sLNameField.setText("");
            sEmailField.setText(""); titleField.setText(""); industryField.setText("");
            sStreetField.setText(""); sCityField.setText(""); sStateField.setText("");
            sZipField.setText(""); sCostField.setText("");
            table.clearSelection();
        }

        private boolean validId(String t) {
            try { if (Integer.parseInt(t) > 0) return true; } catch (NumberFormatException ignored) {}
            showWarning("ID must be a positive integer.");
            return false;
        }

        private int confirm(String msg) {
            return JOptionPane.showConfirmDialog(GuiApp.this, msg, "Confirm", JOptionPane.YES_NO_OPTION);
        }

        private void addRow(JPanel p, GridBagConstraints g, int row, String label, JComponent field) {
            g.gridx = 0; g.gridy = row; g.weightx = 0; g.fill = GridBagConstraints.NONE;
            p.add(new JLabel(label), g);
            g.gridx = 1; g.weightx = 1; g.fill = GridBagConstraints.HORIZONTAL;
            p.add(field, g);
        }
    }

    class DuesPanel extends JPanel {

        private final JTextField dueIdField = new JTextField(5);
        private final JTextField memberIdField = new JTextField(10);
        private final JTextField dDateField = new JTextField(10);
        private final JTextField amountField = new JTextField(10);

        private final JButton insertBtn = new JButton("Insert");
        private final JButton searchBtn = new JButton("Search");
        private final JButton updateBtn = new JButton("Update");
        private final JButton deleteBtn = new JButton("Delete");
        private final JButton clearBtn = new JButton("Clear");
        private final JButton loadBtn = new JButton("Load All");
        private final JButton majorContributionsBtn = new JButton("Contributions By Major");

        private final DefaultTableModel tableModel = new DefaultTableModel(
                new String[]{"DueId", "Member Id", "Date", "Amount"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        private final JTable table = new JTable(tableModel);

        DuesPanel() {
            setLayout(new BorderLayout(0, 6));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            buildUI();
            wireListeners();
            loadAll();
        }

        private void buildUI() {
            // Form
            JPanel form = new JPanel(new GridBagLayout());
            form.setBorder(BorderFactory.createTitledBorder("Due Record"));
            GridBagConstraints g = new GridBagConstraints();
            g.insets = new Insets(4, 6, 4, 6);
            g.anchor = GridBagConstraints.WEST;
            addRow(form, g, 0, "ID (for search / update / delete):", dueIdField);
            addRow(form, g, 1, "Member Id:", memberIdField);
            addRow(form, g, 2, "Date:", dDateField);
            addRow(form, g, 3, "Cost:", amountField);

            // Buttons
            JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 4));
            for (JButton b : new JButton[]{insertBtn, searchBtn, updateBtn, deleteBtn, clearBtn, loadBtn})
                btns.add(b);

            JPanel top = new JPanel(new BorderLayout());
            top.add(form, BorderLayout.CENTER);
            top.add(btns, BorderLayout.SOUTH);

            // Table
            table.setRowHeight(24);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scroll = new JScrollPane(table);
            scroll.setBorder(BorderFactory.createTitledBorder("Results"));

            add(top,    BorderLayout.NORTH);
            add(scroll, BorderLayout.CENTER);
        }

        // CRUD listeners
        private void wireListeners() {

            // INSERT
            insertBtn.addActionListener(e -> {
                String memberId = memberIdField.getText().trim();
                Date dDate = Date.valueOf(dDateField.getText());
                double amount = Double.parseDouble(amountField.getText());

                if (memberId.isEmpty()) {
                    showWarning("MemberId is required.");
                    return;
                }
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "INSERT INTO Dues (MemberId, DDate, Amount) VALUES (?, ?, ?)")) {
                    ps.setInt(1, Integer.parseInt(memberId));
                    ps.setDate(2, dDate);
                    ps.setDouble(3, amount);
                    ps.executeUpdate();
                    setStatus("Event inserted.");
                    clearFields();
                    loadAll();
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // SEARCH
            searchBtn.addActionListener(e -> {
                String idText = dueIdField.getText().trim();
                if (idText.isEmpty()) { loadAll(); return; }
                if (!validId(idText))  return;
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "SELECT DueId, MemberId, DDate, Amount FROM Dues WHERE DueId = ?")) {
                    ps.setInt(1, Integer.parseInt(idText));
                    ResultSet rs = ps.executeQuery();
                    tableModel.setRowCount(0);
                    int n = 0;
                    while (rs.next()) {
                        tableModel.addRow(new Object[]{rs.getInt("DueId"), rs.getInt("MemberId"),
                                rs.getDate("DDate"), rs.getDouble("Amount")});
                        n++;
                    }
                    setStatus(n > 0 ? "Found " + n + " record(s)." : "No record for ID " + idText);
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // UPDATE
            updateBtn.addActionListener(e -> {
                String idText = dueIdField.getText().trim();
                String memberId = memberIdField.getText().trim();
                Date dDate = Date.valueOf(dDateField.getText());
                double amount = Double.parseDouble(amountField.getText());

                if (idText.isEmpty()) { showWarning("Enter an ID to update."); return; }
                if (!validId(idText)) return;
                if (memberId.isEmpty() ) { showWarning("MemberId required."); return; }
                if (confirm("Update record ID " + idText + "?") != JOptionPane.YES_OPTION) return;
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "UPDATE Dues SET MemberId=?, DDate=?, Amount=? WHERE DueId=?")) {
                    ps.setString(1, memberId);
                    ps.setDate(2, dDate);
                    ps.setDouble(3, amount);
                    ps.setInt(4, Integer.parseInt(idText));
                    int rows = ps.executeUpdate();
                    setStatus(rows > 0 ? "Updated ID " + idText : "No record found.");
                    if (rows > 0) { clearFields(); loadAll(); }
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // DELETE
            deleteBtn.addActionListener(e -> {
                String idText = dueIdField.getText().trim();
                if (idText.isEmpty())  { showWarning("Enter an ID to delete."); return; }
                if (!validId(idText))   return;
                if (confirm("Delete record ID " + idText + "?") != JOptionPane.YES_OPTION) return;
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "DELETE FROM Dues WHERE DueId=?")) {
                    ps.setInt(1, Integer.parseInt(idText));
                    int rows = ps.executeUpdate();
                    setStatus(rows > 0 ? "Deleted ID " + idText : "No record found.");
                    if (rows > 0) { clearFields(); loadAll(); }
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // CLEAR
            clearBtn.addActionListener(e -> { clearFields(); setStatus("Cleared."); });

            // LOAD ALL
            loadBtn.addActionListener(e -> { clearFields(); loadAll(); });

            // CONTRIBUTIONS BY MAJOR
            majorContributionsBtn.addActionListener(e -> {
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "SELECT Major, SUM(Amount) AS Total FROM Member NATURAL JOIN Dues GROUP BY Major ORDER BY Total DESC;")) {
                    ResultSet rs = ps.executeQuery();
                    tableModel.setRowCount(0);
                    tableModel.setColumnCount(0);

                    // Build columns from ResultSet metadata
                    ResultSetMetaData meta = rs.getMetaData();
                    int colCount = meta.getColumnCount();
                    for (int i = 1; i <= colCount; i++)
                        tableModel.addColumn(meta.getColumnName(i));

                    // Populate rows
                    int rowCount = 0;
                    while (rs.next()) {
                        Object[] row = new Object[colCount];
                        for (int i = 1; i <= colCount; i++)
                            row[i - 1] = rs.getObject(i);
                        tableModel.addRow(row);
                        rowCount++;
                    }
                    setStatus("Contributions By Major");
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // Row click → populate form
            table.getSelectionModel().addListSelectionListener(e -> {
                if (e.getValueIsAdjusting()) return;
                int row = table.getSelectedRow();
                if (row < 0) return;
                dueIdField.setText(tableModel.getValueAt(row, 0).toString());
                memberIdField.setText(tableModel.getValueAt(row, 1).toString());
                dDateField.setText(tableModel.getValueAt(row, 2).toString());
                amountField.setText(tableModel.getValueAt(row, 3).toString());
            });
        }

        private void loadAll() {
            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT DueId, MemberId, DDate, Amount FROM Dues ORDER BY DueId")) {
                tableModel.setRowCount(0);
                int n = 0;
                while (rs.next()) {
                    tableModel.addRow(new Object[]{rs.getInt("DueId"), rs.getString("MemberId"),
                            rs.getDate("DDate"), rs.getDouble("Amount")});
                    n++;
                }
                setStatus("Loaded " + n + " due(s).");
            } catch (SQLException ex) { showError(ex.getMessage()); }
        }

        private void clearFields() {
            dueIdField.setText(""); memberIdField.setText("");
            dDateField.setText(""); amountField.setText("");
            table.clearSelection();
        }

        private boolean validId(String t) {
            try { if (Integer.parseInt(t) > 0) return true; } catch (NumberFormatException ignored) {}
            showWarning("ID must be a positive integer.");
            return false;
        }

        private int confirm(String msg) {
            return JOptionPane.showConfirmDialog(GuiApp.this, msg, "Confirm", JOptionPane.YES_NO_OPTION);
        }

        private void addRow(JPanel p, GridBagConstraints g, int row, String label, JComponent field) {
            g.gridx = 0; g.gridy = row; g.weightx = 0; g.fill = GridBagConstraints.NONE;
            p.add(new JLabel(label), g);
            g.gridx = 1; g.weightx = 1; g.fill = GridBagConstraints.HORIZONTAL;
            p.add(field, g);
        }
    }

    class AttendancePanel extends JPanel {

        private final JTextField eventIdField = new JTextField(5);
        private final JTextField memberIdField = new JTextField(10);

        private final JButton insertBtn = new JButton("Insert");
        private final JButton searchEventBtn = new JButton("Search Events");
        private final JButton searchMemberBtn = new JButton("Search Members");
        private final JButton deleteBtn = new JButton("Delete");
        private final JButton clearBtn = new JButton("Clear");
        private final JButton loadBtn = new JButton("Load All");
        private final JButton duesVsAttendanceBtn = new JButton("Dues Vs Attendance");
        private final JButton activeAttendanceBtn = new JButton("Active Attendance");
        private final JButton officersAttendanceBtn = new JButton("Officer Attendance");

        private final DefaultTableModel tableModel = new DefaultTableModel(
                new String[]{"Event Id","Event Name", "Member Id", "First Name", "Last Name"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        private final JTable table = new JTable(tableModel);

        AttendancePanel() {
            setLayout(new BorderLayout(0, 6));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            buildUI();
            wireListeners();
            loadAll();
        }

        private void buildUI() {
            // Form
            JPanel form = new JPanel(new GridBagLayout());
            form.setBorder(BorderFactory.createTitledBorder("Attendance Record"));
            GridBagConstraints g = new GridBagConstraints();
            g.insets = new Insets(4, 6, 4, 6);
            g.anchor = GridBagConstraints.WEST;
            addRow(form, g, 0, "Event Id:", eventIdField);
            addRow(form, g, 1, "Member Id:", memberIdField);

            // Buttons
            JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 4));
            for (JButton b : new JButton[]{insertBtn, searchMemberBtn, searchEventBtn, deleteBtn, clearBtn, loadBtn, duesVsAttendanceBtn, activeAttendanceBtn, officersAttendanceBtn})
                btns.add(b);

            JPanel top = new JPanel(new BorderLayout());
            top.add(form, BorderLayout.CENTER);
            top.add(btns, BorderLayout.SOUTH);

            // Table
            table.setRowHeight(24);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scroll = new JScrollPane(table);
            scroll.setBorder(BorderFactory.createTitledBorder("Results"));

            add(top, BorderLayout.NORTH);
            add(scroll, BorderLayout.CENTER);
        }

        // CRUD listeners
        private void wireListeners() {

            // INSERT
            insertBtn.addActionListener(e -> {
                String memberId = memberIdField.getText().trim();
                String eventId = eventIdField.getText().trim();

                if (memberId.isEmpty() || eventId.isEmpty()) {
                    showWarning("MemberId and EventId are required.");
                    return;
                }
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "INSERT INTO Attends (MemberId, EventId) VALUES (?, ?)")) {
                    ps.setInt(1, Integer.parseInt(memberId));
                    ps.setInt(2, Integer.parseInt(eventId));
                    ps.executeUpdate();
                    setStatus("Attendance inserted.");
                    clearFields();
                    loadAll();
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // SEARCH EVENTS
            searchEventBtn.addActionListener(e -> {
                String eventId = eventIdField.getText().trim();
                if (eventId.isEmpty()) { loadAll(); return; }
                if (!validId(eventId))  return;
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "SELECT EventId, EName, MemberId, MFName, MLName FROM Attends NATURAL JOIN Event NATURAL JOIN Member WHERE EventId = ?")) {
                    ps.setInt(1, Integer.parseInt(eventId));
                    ResultSet rs = ps.executeQuery();
                    tableModel.setRowCount(0);
                    int n = 0;
                    while (rs.next()) {
                        tableModel.addRow(new Object[]{rs.getInt("EventId"), rs.getString("EName"), rs.getInt("MemberId"), rs.getString("MFName"), rs.getString("MLName")});
                        n++;
                    }
                    setStatus(n > 0 ? "Found " + n + " record(s)." : "No record for ID " + eventId);
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // SEARCH MEMBERS
            searchMemberBtn.addActionListener(e -> {
                String memberId = memberIdField.getText().trim();
                if (memberId.isEmpty()) { loadAll(); return; }
                if (!validId(memberId))  return;
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "SELECT EventId, EName, MemberId, MFName, MLName FROM Attends NATURAL JOIN Event NATURAL JOIN Member WHERE MemberId = ?")) {
                    ps.setInt(1, Integer.parseInt(memberId));
                    ResultSet rs = ps.executeQuery();
                    tableModel.setRowCount(0);
                    int n = 0;
                    while (rs.next()) {
                        tableModel.addRow(new Object[]{rs.getInt("EventId"), rs.getString("EName"), rs.getInt("MemberId"), rs.getString("MFName"), rs.getString("MLName")});
                        n++;
                    }
                    setStatus(n > 0 ? "Found " + n + " record(s)." : "No record for ID " + memberId);
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // DELETE
            deleteBtn.addActionListener(e -> {
                String eventId = eventIdField.getText().trim();
                String memberId = memberIdField.getText().trim();
                if (eventId.isEmpty() || memberId.isEmpty())  { showWarning("Enter both EventId and MemberId to delete."); return; }
                if (!validId(eventId) || !validId(memberId))   return;
                if (confirm("Delete record ID " + eventId + " " + memberId + "?") != JOptionPane.YES_OPTION) return;
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "DELETE FROM Attends WHERE EventId=? AND MemberId=?")) {
                    ps.setInt(1, Integer.parseInt(eventId));
                    ps.setInt(2, Integer.parseInt(memberId));
                    int rows = ps.executeUpdate();
                    setStatus(rows > 0 ? "Deleted ID " + eventId + " " + memberId : "No record found.");
                    if (rows > 0) { clearFields(); loadAll(); }
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // CLEAR
            clearBtn.addActionListener(e -> { clearFields(); setStatus("Cleared."); });

            // LOAD ALL
            loadBtn.addActionListener(e -> { clearFields(); loadAll(); });

            // Dues vs Attendance
            duesVsAttendanceBtn.addActionListener(e -> {
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "SELECT M.MemberId, MFName, MLName, COUNT(A.EventId) AS 'Events Attended' FROM Member AS M LEFT OUTER JOIN Dues AS D ON M.MemberId = D.MemberId" +
                                     " LEFT OUTER JOIN Attends AS A ON M.MemberId = A.MemberId WHERE D.DueId IS NULL GROUP BY M.MemberId, M.MFName, M.MLName HAVING Count(A.EventId) > 1")) {
                    ResultSet rs = ps.executeQuery();
                    tableModel.setRowCount(0);
                    tableModel.setColumnCount(0);

                    // Build columns from ResultSet metadata
                    ResultSetMetaData meta = rs.getMetaData();
                    int colCount = meta.getColumnCount();
                    for (int i = 1; i <= colCount; i++)
                        tableModel.addColumn(meta.getColumnName(i));

                    // Populate rows
                    int rowCount = 0;
                    while (rs.next()) {
                        Object[] row = new Object[colCount];
                        for (int i = 1; i <= colCount; i++)
                            row[i - 1] = rs.getObject(i);
                        tableModel.addRow(row);
                        rowCount++;
                    }
                    setStatus("Attended without Dues");
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // Last Year Attendance
            activeAttendanceBtn.addActionListener(e -> {
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "SELECT MemberId, MFName, MLName FROM Member WHERE Status = 'Active' AND MemberId NOT IN  (SELECT A.MemberId FROM Attends AS A NATURAL JOIN Event WHERE EDate >= CURDATE() - INTERVAL 365 DAY)")) {
                    ResultSet rs = ps.executeQuery();
                    tableModel.setRowCount(0);
                    tableModel.setColumnCount(0);

                    // Build columns from ResultSet metadata
                    ResultSetMetaData meta = rs.getMetaData();
                    int colCount = meta.getColumnCount();
                    for (int i = 1; i <= colCount; i++)
                        tableModel.addColumn(meta.getColumnName(i));

                    // Populate rows
                    int rowCount = 0;
                    while (rs.next()) {
                        Object[] row = new Object[colCount];
                        for (int i = 1; i <= colCount; i++)
                            row[i - 1] = rs.getObject(i);
                        tableModel.addRow(row);
                        rowCount++;
                    }
                    setStatus("Attended without Dues");
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // OFFICER ATTENDANCE OF EVENTS
            officersAttendanceBtn.addActionListener(e -> {
                String eventId = eventIdField.getText().trim();
                if (eventId.isEmpty()) { loadAll(); return; }
                if (!validId(eventId))  return;
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "SELECT RoleName, MFName, MLName FROM Attends NATURAL JOIN Event NATURAL JOIN Member NATURAL JOIN Holds NATURAL JOIN Role WHERE EventId = ?")) {
                    ps.setInt(1, Integer.parseInt(eventId));
                    ResultSet rs = ps.executeQuery();
                    tableModel.setRowCount(0);
                    tableModel.setColumnCount(0);

                    // Build columns from ResultSet metadata
                    ResultSetMetaData meta = rs.getMetaData();
                    int colCount = meta.getColumnCount();
                    for (int i = 1; i <= colCount; i++)
                        tableModel.addColumn(meta.getColumnName(i));

                    // Populate rows
                    int rowCount = 0;
                    while (rs.next()) {
                        Object[] row = new Object[colCount];
                        for (int i = 1; i <= colCount; i++)
                            row[i - 1] = rs.getObject(i);
                        tableModel.addRow(row);
                        rowCount++;
                    }
                    setStatus("Attended");
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // Row click → populate form
            table.getSelectionModel().addListSelectionListener(e -> {
                if (e.getValueIsAdjusting()) return;
                int row = table.getSelectedRow();
                if (row < 0) return;
                eventIdField.setText(tableModel.getValueAt(row, 0).toString());
                memberIdField.setText(tableModel.getValueAt(row, 2).toString());
            });
        }

        private void loadAll() {
            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs   = stmt.executeQuery("SELECT EventId, EName, MemberId, MFName, MLName FROM Attends NATURAL JOIN Event NATURAL JOIN Member ORDER BY EventId")) {
                tableModel.setRowCount(0);
                int n = 0;
                while (rs.next()) {
                    tableModel.addRow(new Object[]{rs.getInt("EventId"),rs.getString("EName"), rs.getInt("MemberId"), rs.getString("MFName"), rs.getString("MLName")});
                    n++;
                }
                setStatus("Loaded " + n + " Attendance.");
            } catch (SQLException ex) { showError(ex.getMessage()); }
        }

        private void clearFields() {
            eventIdField.setText(""); memberIdField.setText("");
            table.clearSelection();
        }

        private boolean validId(String t) {
            try { if (Integer.parseInt(t) > 0) return true; } catch (NumberFormatException ignored) {}
            showWarning("ID must be a positive integer.");
            return false;
        }

        private int confirm(String msg) {
            return JOptionPane.showConfirmDialog(GuiApp.this, msg, "Confirm", JOptionPane.YES_NO_OPTION);
        }

        private void addRow(JPanel p, GridBagConstraints g, int row, String label, JComponent field) {
            g.gridx = 0; g.gridy = row; g.weightx = 0; g.fill = GridBagConstraints.NONE;
            p.add(new JLabel(label), g);
            g.gridx = 1; g.weightx = 1; g.fill = GridBagConstraints.HORIZONTAL;
            p.add(field, g);
        }
    }

    class ReportsPanel extends JPanel {

        private final JTextField eventIdField = new JTextField(5);
        private final JTextField memberIdField = new JTextField(10);
        private final JTextField speakerIdField = new JTextField(5);
        private final JTextField statusField = new JTextField(10);
        private final JTextField roleField = new JTextField(5);
        private final JTextField majorField = new JTextField(10);
        private final JTextField costField = new JTextField(10);
        private final JTextField startDateField = new JTextField(5);
        private final JTextField endDateField = new JTextField(10);

        private final JButton perfectAttendanceBtn = new JButton("Perfect Attendance");
        private final JButton officersAttendanceBtn = new JButton("Officer Attendance");
        private final JButton newSpeakersBtn = new JButton("New Speakers");
        private final JButton majorContributionsBtn = new JButton("Contributions by Major");
        private final JButton duesVsAttendanceBtn = new JButton("Dues Vs Attendance");
        private final JButton activeAttendanceBtn = new JButton("Active Attendance");

        private final DefaultTableModel tableModel = new DefaultTableModel();
        private final JTable table = new JTable(tableModel);

        ReportsPanel() {
            setLayout(new BorderLayout(0, 6));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            buildUI();
            wireListeners();
            loadAll();
        }

        private void buildUI() {
            // Form
            JPanel form = new JPanel(new GridBagLayout());
            form.setBorder(BorderFactory.createTitledBorder("Reports"));
            GridBagConstraints g = new GridBagConstraints();
            g.insets = new Insets(4, 6, 4, 6);
            g.anchor = GridBagConstraints.WEST;
            addRow(form, g, 0, "Event Id:", eventIdField);
            addRow(form, g, 1, "Member Id:", memberIdField);
            addRow(form, g, 2, "Speaker Id:", speakerIdField);
            addRow(form, g, 3, "Status:", statusField);
            addRow(form, g, 4, "Role:", roleField);
            addRow(form, g, 5, "Major:", majorField);
            addRow(form, g, 6, "Cost:", costField);
            addRow(form, g, 7, "Start Date:", startDateField);
            addRow(form, g, 8, "End Date:", endDateField);

            // Buttons
            JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 4));
            for (JButton b : new JButton[]{perfectAttendanceBtn, officersAttendanceBtn, newSpeakersBtn, majorContributionsBtn, duesVsAttendanceBtn, activeAttendanceBtn })
                btns.add(b);

            JPanel top = new JPanel(new BorderLayout());
            top.add(form, BorderLayout.CENTER);
            top.add(btns, BorderLayout.SOUTH);

            // Table
            table.setRowHeight(24);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scroll = new JScrollPane(table);
            scroll.setBorder(BorderFactory.createTitledBorder("Results"));

            add(top, BorderLayout.NORTH);
            add(scroll, BorderLayout.CENTER);
        }

        // CRUD listeners
        private void wireListeners() {

            // PERFECT ATTENDANCE
            perfectAttendanceBtn.addActionListener(e -> {
                Date startDate = Date.valueOf(startDateField.getText());
                Date endDate = Date.valueOf(endDateField.getText());

                /*if (memberId.isEmpty() || eventId.isEmpty()) {
                    showWarning("MemberId and EventId are required.");
                    return;
                }*/
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "SELECT MemberId, MFName, MLName FROM Member NATURAL JOIN Attends NATURAL JOIN (SELECT EventId FROM Event WHERE EDate BETWEEN ? AND ? ) AS Events GROUP BY MemberId, MFName, MLName HAVING count(*) = ((SELECT count(*) FROM Event WHERE EDate BETWEEN ? AND ? ))")) {

                    ps.setDate(1, startDate);
                    ps.setDate(2, endDate);
                    ps.setDate(3, startDate);
                    ps.setDate(4, endDate);
                    ResultSet rs = ps.executeQuery();
                    tableModel.setRowCount(0);
                    tableModel.setColumnCount(0);

                    // Build columns from ResultSet metadata
                    ResultSetMetaData meta = rs.getMetaData();
                    int colCount = meta.getColumnCount();
                    for (int i = 1; i <= colCount; i++)
                        tableModel.addColumn(meta.getColumnName(i));

                    // Populate rows
                    int rowCount = 0;
                    while (rs.next()) {
                        Object[] row = new Object[colCount];
                        for (int i = 1; i <= colCount; i++)
                            row[i - 1] = rs.getObject(i);
                        tableModel.addRow(row);
                        rowCount++;
                    }
                    setStatus("Had Perfect Attendance");

                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // OFFICER ATTENDANCE OF EVENTS
            officersAttendanceBtn.addActionListener(e -> {
                String eventId = eventIdField.getText().trim();
                if (eventId.isEmpty()) { loadAll(); return; }
                if (!validId(eventId))  return;
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "SELECT RoleName, MFName, MLName FROM Attends NATURAL JOIN Event NATURAL JOIN Member NATURAL JOIN Holds NATURAL JOIN Role WHERE EventId = ?")) {
                    ps.setInt(1, Integer.parseInt(eventId));
                    ResultSet rs = ps.executeQuery();
                    tableModel.setRowCount(0);
                    tableModel.setColumnCount(0);

                    // Build columns from ResultSet metadata
                    ResultSetMetaData meta = rs.getMetaData();
                    int colCount = meta.getColumnCount();
                    for (int i = 1; i <= colCount; i++)
                        tableModel.addColumn(meta.getColumnName(i));

                    // Populate rows
                    int rowCount = 0;
                    while (rs.next()) {
                        Object[] row = new Object[colCount];
                        for (int i = 1; i <= colCount; i++)
                            row[i - 1] = rs.getObject(i);
                        tableModel.addRow(row);
                        rowCount++;
                    }
                    setStatus("Attended");
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // NEW SPEAKERS TARGET COST
            newSpeakersBtn.addActionListener(e -> {
                String cost = costField.getText().trim();
                //if (!validId(speakerId))  return;
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "SELECT SpeakerId, SFName, SLName, SCost FROM Speaker WHERE SCost < ? AND SpeakerId NOT IN (SELECT DISTINCT SpeakerId FROM Event)")) {
                    ps.setDouble(1, Double.parseDouble(cost));
                    ResultSet rs = ps.executeQuery();
                    tableModel.setRowCount(0);
                    tableModel.setColumnCount(0);

                    // Build columns from ResultSet metadata
                    ResultSetMetaData meta = rs.getMetaData();
                    int colCount = meta.getColumnCount();
                    for (int i = 1; i <= colCount; i++)
                        tableModel.addColumn(meta.getColumnName(i));

                    // Populate rows
                    int rowCount = 0;
                    while (rs.next()) {
                        Object[] row = new Object[colCount];
                        for (int i = 1; i <= colCount; i++)
                            row[i - 1] = rs.getObject(i);
                        tableModel.addRow(row);
                        rowCount++;
                    }
                    setStatus("New Speakers");
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // CONTRIBUTIONS BY MAJOR
            majorContributionsBtn.addActionListener(e -> {
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "SELECT Major, SUM(Amount) AS Total FROM Member NATURAL JOIN Dues GROUP BY Major ORDER BY Total DESC;")) {
                    ResultSet rs = ps.executeQuery();
                    tableModel.setRowCount(0);
                    tableModel.setColumnCount(0);

                    // Build columns from ResultSet metadata
                    ResultSetMetaData meta = rs.getMetaData();
                    int colCount = meta.getColumnCount();
                    for (int i = 1; i <= colCount; i++)
                        tableModel.addColumn(meta.getColumnName(i));

                    // Populate rows
                    int rowCount = 0;
                    while (rs.next()) {
                        Object[] row = new Object[colCount];
                        for (int i = 1; i <= colCount; i++)
                            row[i - 1] = rs.getObject(i);
                        tableModel.addRow(row);
                        rowCount++;
                    }
                    setStatus("Contributions By Major");
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // Dues vs Attendance
            duesVsAttendanceBtn.addActionListener(e -> {
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "SELECT M.MemberId, MFName, MLName, COUNT(A.EventId) AS 'Events Attended' FROM Member AS M LEFT OUTER JOIN Dues AS D ON M.MemberId = D.MemberId" +
                             " LEFT OUTER JOIN Attends AS A ON M.MemberId = A.MemberId WHERE D.DueId IS NULL GROUP BY M.MemberId, M.MFName, M.MLName HAVING Count(A.EventId) > 1")) {
                    ResultSet rs = ps.executeQuery();
                    tableModel.setRowCount(0);
                    tableModel.setColumnCount(0);

                    // Build columns from ResultSet metadata
                    ResultSetMetaData meta = rs.getMetaData();
                    int colCount = meta.getColumnCount();
                    for (int i = 1; i <= colCount; i++)
                        tableModel.addColumn(meta.getColumnName(i));

                    // Populate rows
                    int rowCount = 0;
                    while (rs.next()) {
                        Object[] row = new Object[colCount];
                        for (int i = 1; i <= colCount; i++)
                            row[i - 1] = rs.getObject(i);
                        tableModel.addRow(row);
                        rowCount++;
                    }
                    setStatus("Attended without Dues");
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // Last Year Attendance
            activeAttendanceBtn.addActionListener(e -> {
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "SELECT MemberId, MFName, MLName FROM Member WHERE Status = 'Active' AND MemberId NOT IN  (SELECT A.MemberId FROM Attends AS A NATURAL JOIN Event WHERE EDate >= CURDATE() - INTERVAL 365 DAY)")) {
                    ResultSet rs = ps.executeQuery();
                    tableModel.setRowCount(0);
                    tableModel.setColumnCount(0);

                    // Build columns from ResultSet metadata
                    ResultSetMetaData meta = rs.getMetaData();
                    int colCount = meta.getColumnCount();
                    for (int i = 1; i <= colCount; i++)
                        tableModel.addColumn(meta.getColumnName(i));

                    // Populate rows
                    int rowCount = 0;
                    while (rs.next()) {
                        Object[] row = new Object[colCount];
                        for (int i = 1; i <= colCount; i++)
                            row[i - 1] = rs.getObject(i);
                        tableModel.addRow(row);
                        rowCount++;
                    }
                    setStatus("Attended without Dues");
                } catch (SQLException ex) { showError(ex.getMessage()); }
            });

            // Row click → populate form
            table.getSelectionModel().addListSelectionListener(e -> {
                if (e.getValueIsAdjusting()) return;
                int row = table.getSelectedRow();
                if (row < 0) return;
                eventIdField.setText(tableModel.getValueAt(row, 0).toString());
                memberIdField.setText(tableModel.getValueAt(row, 2).toString());
            });
        }

        private void loadAll() {
            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs   = stmt.executeQuery("SELECT EventId, EName, MemberId, MFName, MLName FROM Attends NATURAL JOIN Event NATURAL JOIN Member ORDER BY EventId")) {
                tableModel.setRowCount(0);
                int n = 0;
                while (rs.next()) {
                    tableModel.addRow(new Object[]{rs.getInt("EventId"),rs.getString("EName"), rs.getInt("MemberId"), rs.getString("MFName"), rs.getString("MLName")});
                    n++;
                }
                setStatus("Loaded " + n + " Attendance.");
            } catch (SQLException ex) { showError(ex.getMessage()); }
        }

        private void clearFields() {
            eventIdField.setText(""); memberIdField.setText("");
            table.clearSelection();
        }

        private boolean validId(String t) {
            try { if (Integer.parseInt(t) > 0) return true; } catch (NumberFormatException ignored) {}
            showWarning("ID must be a positive integer.");
            return false;
        }

        private int confirm(String msg) {
            return JOptionPane.showConfirmDialog(GuiApp.this, msg, "Confirm", JOptionPane.YES_NO_OPTION);
        }

        private void addRow(JPanel p, GridBagConstraints g, int row, String label, JComponent field) {
            g.gridx = 0; g.gridy = row; g.weightx = 0; g.fill = GridBagConstraints.NONE;
            p.add(new JLabel(label), g);
            g.gridx = 1; g.weightx = 1; g.fill = GridBagConstraints.HORIZONTAL;
            p.add(field, g);
        }
    }
}
