import java.sql.*;
import java.util.Date;


public class Database {

    /*
     * load SQL driver (JDBC: Java Database Connector/ODBC)
     * - add to build path
     *
     * set up our database (script)
     *
     * connect to the database
     *
     * insert/modify/delete data (Java)
     *
     * query data (Java)
     *
     * disconnect from the database
     *
     */

    /* SQLite connection to a local database */
//	private String url = "jdbc:sqlite:/Users/asauppe/Documents/teaching/cs364/Company.db";

    /* MySQL connection to a local database */
//	private String url = "jdbc:mysql://localhost/dbName?user=example&password=abc";

    /* MySQL connection to a remote database */
//	private String url = "jdbc:mysql://ipAddress:socket/dbName?user=example&password=abc";

    private String url = "jdbc:mysql://138.49.184.123:3306/";	// URL for server
    private String dbName = "byrne8370_SONAR";				// TODO: set to your dbName on the server
    private String username = "pieper.hans"; 						// TODO: your username on the server
    private String password; 									// your password on the server, set in constructor for privacy
    private Connection connection;								// connection object for running queries

    /**
     * Constructor for the Database class.
     * Set the password for the user in this function.
     */
    public Database() {
        password = "DHB7E-RkrS3JrgNdT"; //TODO: set this to your password
    }


    /**
     * Construct the url to connect to the database, and establish a connection.
     */
    public void connect() {
        try {
            url = url + dbName + "?";
            connection = DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {
            System.out.println("Cannot connect!");
            System.out.println(e);
        }
    }

    /**
     * Closes the connection with the database.
     */
    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Cannot disconnect!");
        }
    }

    /**
     * Runs a query with no parameters using prepared statements.
     * @param query : the query to run
     * @return the results set from the database
     * @throws SQLException
     */
    public ResultSet runQuery(String query) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet results = stmt.executeQuery();
        return results;
    }

    /**
     * Looks up a member based on their ssn. Assumes at most one tuple is returned.
     * @param memberId : the ssn of the member tuple you are interested in
     * @return a member object containing the data from the database, or null if no results were returned.
     * @throws SQLException
     */
    public Member memberLookup(int memberId) throws SQLException {
        String query = "SELECT * FROM Member WHERE MemberId = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, memberId);
        ResultSet results = stmt.executeQuery();
        Member m = null;
        // unpack the data into an object for use in Java
        if(results.next()) {
            // unpack in order the data came in
            int res_memberId = results.getInt("MemberID");
            String mFName = results.getString("MFName");
            String mLName = results.getString("MLName");
            String mEmail = results.getString("Email");
            String major = results.getString("Major");
            String status = results.getString("Status");
            int addressId = results.getInt("AddressId");
            Date startDate = results.getDate("StartDate");
            Date endDate = results.getDate("EndDate");

            m = new Member(res_memberId, mFName, mLName, mEmail, major, status, addressId, startDate, endDate);
        }
        return m;
    }

    /**
     * Inserts a new member into the database. SQL prepared statement is used to insert the tuple into the Member table.
     * @param m : a member object to add to the database
     * @throws SQLException
     */
    public void insertMember(Member m) throws SQLException {
        String sql = "INSERT INTO Member (MFName, MLName, Email, Major, Status, AddressID, StartDate, EndDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, m.getMFName());
        stmt.setString(2, m.getMLName());
        stmt.setString(3, m.getMEmail());
        stmt.setString(4, m.getMajor());
        stmt.setString(5, m.getStatus());
        stmt.setInt(6,m.getAddressId());
        stmt.setDate(7, (java.sql.Date)m.getStartDate());
        stmt.setDate(8, (java.sql.Date)m.getEndDate());

        int numRowsAffected = stmt.executeUpdate();
        System.out.println("Number of rows affected: " + numRowsAffected);
    }

    /**
     * Changes the major of a member in the database to the new value major. Both the database and the object will have the new major.
     * @param m : the member object that represents the tuple in the database
     * @param major : the new major
     * @throws SQLException
     */
    public void updateMemberMajor(Member m, String major) throws SQLException {
        String sql = "UPDATE Member SET Major = ? WHERE SSN = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, major);
        stmt.setInt(2, m.getMemberId());
        stmt.executeUpdate();
        m.setMajor(major);
    }

    /**
     * Changes the values of a member in the database to the new values. Both the database and the object will have the new values.
     * @param m : the member object that represents the tuple in the database
     * @param edit : the member object that represents the edited member object
     * @throws SQLException
     */
    public void updateMember(Member m, Member edit) throws SQLException {
        String sql = "UPDATE Member SET MFName = ?, MLName = ?, Email = ?, Major = ?, Status = ?, AddressID = ?, StartDate = ?, EndDate = ? WHERE MemberID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        if(m.getMFName().equals(edit.getMFName())){
            stmt.setString(1, m.getMFName());
        }else{
            stmt.setString(1, edit.getMFName());
        }
        if(m.getMLName().equals(edit.getMLName())){
            stmt.setString(2, m.getMLName());
        }else{
            stmt.setString(2, edit.getMLName());
        }
        if(m.getMEmail().equals(edit.getMEmail())){
            stmt.setString(3, m.getMEmail());
        }else{
            stmt.setString(3, edit.getMEmail());
        }
        if(m.getMajor().equals(edit.getMajor())){
            stmt.setString(4, m.getMajor());
        }else{
            stmt.setString(4, edit.getMajor());
        }
        if(m.getStatus().equals(edit.getStatus())){
            stmt.setString(5, m.getStatus());
        }else{
            stmt.setString(5, edit.getStatus());
        }
        if(m.getAddressId() == edit.getAddressId()){
            stmt.setInt(6, m.getAddressId());
        }else{
            stmt.setInt(6, edit.getAddressId());
        }
        if(m.getStartDate() == edit.getStartDate()){
            stmt.setDate(7, (java.sql.Date)m.getStartDate());
        }else{
            stmt.setDate(7, (java.sql.Date)edit.getStartDate());
        }
        if(m.getEndDate() == edit.getEndDate()){
            stmt.setDate(8, (java.sql.Date)m.getEndDate());
        }else{
            stmt.setDate(8, (java.sql.Date)edit.getEndDate());
        }

        stmt.setInt(9, m.getMemberId());
        stmt.executeUpdate();
        m = edit;
    }

    /**
     * Delete a member from the Member table. Object m is not deleted.
     * @param m : Member object representing the member to delete
     * @return how many rows were deleted from the table.
     * @throws SQLException
     */
    public boolean deleteMember(Member m) throws SQLException {
        String sql = "DELETE FROM Member WHERE MemberID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, m.getMemberId());
        int numRowsAffected = stmt.executeUpdate();
        return numRowsAffected > 0;
    }

}

