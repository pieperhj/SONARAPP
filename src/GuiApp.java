import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class GuiApp extends JFrame{
    // ── Card names — used to identify each form in the CardLayout
    private static final String CARD_MEMBERS = "MEMBERS";
    private static final String CARD_EVENTS = "EVENTS";
    private static final String CARD_SPEAKERS = "SPEAKERS";
    private static final String CARD_DUES = "DUES";
    private static final String CARD_ADDRESSES = "ADDRESSES";
    private static final String CARD_SQL   = "SQL";

    // ── CardLayout + its container
    private final CardLayout cardLayout   = new CardLayout();
    private final JPanel     cardPanel    = new JPanel(cardLayout);

    // ── Toolbar nav buttons
    private final JButton navMembersBtn = new JButton("👤  Members");
    private final JButton navEventsBtn = new JButton("👤  Events");
    private final JButton navSpeakersBtn = new JButton("👤  Speakers");
    private final JButton navDuesBtn = new JButton("👤  Dues");
    private final JButton navAddressesBtn = new JButton("👤  Addresses");
    private final JButton navSqlBtn   = new JButton("⌨  SQL Runner");

    // ── Status bar (shared across all forms)
    private final JLabel statusLabel = new JLabel("Ready");

}
