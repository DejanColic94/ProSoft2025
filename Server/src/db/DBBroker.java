/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import domain.OpstiDomenskiObjekat;
import java.io.IOException;
import java.sql.*;
import java.util.List;

/**
 *
 * @author Dejan Colic
 */
public class DBBroker {

    private Connection connection;

    public DBBroker() {

    }

    public void connect() throws IOException, SQLException {
        DatabaseUtil dbUtil = DatabaseUtil.getInstance();

        String baseUrl = dbUtil.getURL();
        String port = dbUtil.getPort();
        String dbName = dbUtil.getDatabaseName();
        String fullUrl = baseUrl + ":" + port + "/" + dbName;

        String user = dbUtil.getUsername();
        String password = dbUtil.getPassword();

        try {
            connection = DriverManager.getConnection(fullUrl, user, password);
        } catch (SQLException e) {
            System.out.println("Full url: " + fullUrl);
            e.printStackTrace();
            throw e;
        }
        connection.setAutoCommit(false);
    }

    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public void commit() throws SQLException {
        if (connection != null) {
            connection.commit();
        }
    }

    public void rollback() throws SQLException {
        if (connection != null) {
            connection.rollback();
        }
    }

    public void create(OpstiDomenskiObjekat odo) throws SQLException {
        String query = "INSERT INTO " + odo.getImeTabele() + " VALUES (" + odo.getParametre() + ")";
        System.out.println("[SQL] " + query);
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                odo.setVrednostPK(id);
            }
        }
    }

    public void update(OpstiDomenskiObjekat odo) throws SQLException {
        String query = "UPDATE " + odo.getImeTabele() + " SET " + odo.getUpdate() + " WHERE " + odo.getPK() + " = " + odo.getVrednostPK();
        System.out.println("[SQL] " + query);
        try (Statement st = connection.createStatement()) {
            st.executeUpdate(query);
        }
    }

    public void delete(OpstiDomenskiObjekat odo) throws SQLException {
        String query = "DELETE FROM " + odo.getImeTabele() + " WHERE " + odo.getPK() + " = " + odo.getVrednostPK();
        System.out.println("[SQL] " + query);
        try (Statement st = connection.createStatement()) {
            st.executeUpdate(query);
        }
    }

    public List<OpstiDomenskiObjekat> getAll(OpstiDomenskiObjekat odo) throws SQLException {
        String query = "SELECT * FROM " + odo.getImeTabele();
        System.out.println("[SQL] " + query);
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            return odo.ResultSetIntoTable(rs);
        }
    }

    public List<OpstiDomenskiObjekat> getWithCondition(OpstiDomenskiObjekat odo, String condition) throws SQLException {
        String query = "SELECT * FROM " + odo.getImeTabele();
        if (condition != null && !condition.trim().isEmpty()) {
            query += " WHERE " + condition;
        }

        System.out.println("[SQL] " + query); 

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            return odo.ResultSetIntoTable(rs);
        }
    }

    public int getNextID(OpstiDomenskiObjekat odo) throws SQLException {
        String query = "SELECT MAX(" + odo.getPK() + ") AS maxid FROM " + odo.getImeTabele();
        System.out.println("[SQL] " + query);
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("maxid") + 1;
            } else {
                return 1;
            }
        }
    }
}
