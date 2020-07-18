package database;

import java.sql.*;

public class Datasource {

    public static final String DATABASE_NAME = "LoadData";
    public static final String DATABASE_CONNECTION = "jdbc:sqlite:" + DATABASE_NAME;
    public static final String TABLE = "Contacts";
    public static final String TABLE_NAME = "Name";
    public static final String TABLE_PHONE = "Phone";
    public static final String TABLE_LOCATION = "Location";

    public static final String TABLE_CONNECTION = "CREATE TABLE IF NOT EXISTS " + TABLE +
            " ( \"" + TABLE_NAME + "\" TEXT COLLATE NOCASE, \"" +
            TABLE_PHONE + "\" INTEGER NOT NULL UNIQUE, \"" +
            TABLE_LOCATION + "\" TEXT ," +
            " PRIMARY KEY ( \"" + TABLE_PHONE + "\"))";

    public static final String SELECT = "SELECT * FROM " + TABLE;
    public static final String INSERT = "INSERT INTO " + TABLE +
            " VALUES (?, ?, ?)";
    public static final String DELETE_NAME = "DELETE FROM " + TABLE +
            " WHERE " + TABLE_NAME + " = ?";

    public static final String DELETE_PHONE = "DELETE FROM " + TABLE +
            " WHERE " + TABLE_PHONE + " = ?";

    public static final String CHECK_PHONE_NUMBER = "SELECT " + TABLE_PHONE +
            " FROM " + TABLE + " WHERE " + TABLE_PHONE + " = ?";

    public static final String CHECK_NAME = "SELECT " + TABLE_NAME +
            " FROM " + TABLE + " WHERE " + TABLE_NAME + " = ? COLLATE NOCASE";

    public static final String EDIT_CONTACT = "UPDATE " + TABLE + " SET ";

    private static Datasource instance = new Datasource();
    private Connection connection;
    private Statement statement;
    private PreparedStatement insert;
    private PreparedStatement deleteName;
    private PreparedStatement deletePhone;
    private PreparedStatement checkPhoneNumber;
    private PreparedStatement checkName;

    private Datasource() {
    }

    public static Datasource getInstance() {
        return instance;
    }

    public boolean open() {
        try {
            connection = DriverManager.getConnection(DATABASE_CONNECTION);
            statement = connection.createStatement();
            statement.execute(TABLE_CONNECTION);
            insert = connection.prepareStatement(INSERT);
            deleteName = connection.prepareStatement(DELETE_NAME);
            deletePhone = connection.prepareStatement(DELETE_PHONE);
            checkPhoneNumber = connection.prepareStatement(CHECK_PHONE_NUMBER);
            checkName = connection.prepareStatement(CHECK_NAME);
            return true;
        } catch (SQLException sqlException) {
            System.out.println("SQLException on open() : " + sqlException.getMessage());
        }
        return false;
    }

    public Statement getStatement() {
        if (connection != null) {
            try {
                return connection.createStatement();
            } catch (SQLException sqlException) {
                System.out.println("SQLException on getStatement() : " + sqlException.getMessage());
                return null;
            }
        }
        return null;
    }

    public PreparedStatement getInsert() {
        return insert;
    }

    public PreparedStatement getDeleteName() {
        return deleteName;
    }

    public PreparedStatement getDeletePhone() {
        return deletePhone;
    }

    public PreparedStatement getCheckPhoneNumber() {
        return checkPhoneNumber;
    }

    public PreparedStatement getCheckName() {
        return checkName;
    }

    public void close() {
        try {
            if (checkName != null) {
                checkName.close();
            }

            if (checkPhoneNumber != null) {
                checkPhoneNumber.close();
            }
            if (deletePhone != null) {
                deletePhone.close();
            }

            if (deleteName != null) {
                deleteName.close();
            }

            if (insert != null) {
                insert.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException sqlException) {
            System.out.println("SQLException on close() : " + sqlException.getMessage());
        }
    }
}
