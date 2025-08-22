/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Dejan Colic
 */
public class DBBroker {

    private Connection connection;

    public DBBroker() {

    }

    public void connect() throws IOException, SQLException {
        String url = DatabaseUtil.getInstance().getURL();
        String user = DatabaseUtil.getInstance().getUser();
        String password = DatabaseUtil.getInstance().getPassword();
        connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);
    }

    public void disconnect() throws SQLException {
        connection.close();
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }
}
