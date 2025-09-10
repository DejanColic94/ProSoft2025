/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import domain.Knjiga;
import domain.OpstiDomenskiObjekat;
import domain.Primerak;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import so.clan.SOupdateClan;

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
        String query = "INSERT INTO " + odo.getImeTabele()
                + " (" + odo.getInsertColumns() + ") VALUES (" + odo.getParametre() + ")";
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
        String sql = "UPDATE " + odo.getImeTabele() + " SET " + odo.getUpdate()
                + " WHERE " + odo.getPK() + " = ?";
        System.out.println("[SQL] " + sql.replace("?", String.valueOf(odo.getVrednostPK())));
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, odo.getVrednostPK());
            ps.executeUpdate();
        }
    }

    public void delete(OpstiDomenskiObjekat odo) throws SQLException {
        String sql = "DELETE FROM " + odo.getImeTabele()
                + " WHERE " + odo.getPK() + " = ?";
        System.out.println("[SQL] " + sql.replace("?", String.valueOf(odo.getVrednostPK())));
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, odo.getVrednostPK());
            ps.executeUpdate();
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

    public boolean clanHasZaduzenja(int clanID) throws SQLException {
        String query = "SELECT COUNT(*) FROM zaduzenje WHERE clanID=" + clanID;
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public int countPrimerciForKnjiga(int knjigaID) throws SQLException {
        String query = "SELECT COUNT(*) FROM primerak WHERE knjigaID=" + knjigaID;
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public int countAvailablePrimerciForKnjiga(int knjigaID) throws SQLException {
        String query
                = "SELECT COUNT(*) FROM primerak p "
                + "WHERE p.knjigaID=" + knjigaID + " "
                + "AND p.primerakID NOT IN ("
                + "   SELECT primerakID FROM stavkazaduzenja WHERE datumRazduzenja IS NULL)";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public boolean isPrimerakAvailable(int primerakID) throws SQLException {
        String query = "SELECT COUNT(*) FROM stavkazaduzenja "
                + "WHERE primerakID=" + primerakID + " AND datumRazduzenja IS NULL";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        }
        return true;
    }

    public List<Primerak> getAvailablePrimerciForKnjiga(int knjigaID) throws Exception {
        String sql
                = "SELECT p.primerakID, p.knjigaID, p.izdavac, p.godinaIzdanja "
                + "FROM primerak p "
                + "WHERE p.knjigaID=" + knjigaID + " "
                + "AND p.primerakID NOT IN ("
                + "  SELECT sz.primerakID FROM stavkazaduzenja sz WHERE sz.datumRazduzenja IS NULL"
                + ")";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<Primerak> list = new ArrayList<>();
        while (rs.next()) {
            Primerak p = new Primerak();
            p.setPrimerakID(rs.getInt("primerakID"));
            Knjiga k = new Knjiga();
            k.setKnjigaID(rs.getInt("knjigaID"));
            p.setKnjiga(k);
            p.setIzdavac(rs.getString("izdavac"));
            p.setGodinaIzdanja(rs.getInt("godinaIzdanja"));
            list.add(p);
        }
        rs.close();
        st.close();
        return list;
    }

    public List<OpstiDomenskiObjekat> getAllWithJoin(OpstiDomenskiObjekat odo, String selectClause, String joinClause) throws SQLException {
        String select = (selectClause == null || selectClause.trim().isEmpty()) ? "*" : selectClause.trim();
        String join = (joinClause == null || joinClause.trim().isEmpty()) ? "" : " " + joinClause.trim();

        String query = "SELECT " + select + " FROM " + odo.getImeTabele() + join;
        System.out.println("[SQL] " + query);

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(query)) {
            return odo.ResultSetIntoTable(rs);
        }
    }

    public int getNextStavkaIDForZaduzenje(int zaduzenjeID) throws Exception {
        String sql = "SELECT COALESCE(MAX(stavkaID),0)+1 AS next FROM stavkazaduzenja WHERE zaduzenjeID=" + zaduzenjeID;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        int next = 1;
        if (rs.next()) {
            next = rs.getInt("next");
        }
        rs.close();
        st.close();
        return next;
    }

    public void deleteStavkeForZaduzenje(int zaduzenjeID) throws Exception {
        String sql = "DELETE FROM stavkazaduzenja WHERE zaduzenjeID=" + zaduzenjeID;
        Statement st = connection.createStatement();
        st.executeUpdate(sql);
        st.close();
    }
}
