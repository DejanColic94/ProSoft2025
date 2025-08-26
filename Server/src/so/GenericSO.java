/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import db.DBBroker;
import domain.OpstiDomenskiObjekat;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Dejan Colic
 */
public abstract class GenericSO {

    protected DBBroker dbb;

    public GenericSO() {
        this.dbb = new DBBroker();
    }

    public void execute(OpstiDomenskiObjekat odo) throws Exception {
        connect();
        try {
            executeSO(odo);
            commit();
        } catch (Exception ex) {
            rollback();
            System.out.println("Error in execution of SO");
            throw ex;
        } finally {
            disconnect();
        }
    }

    protected abstract void executeSO(OpstiDomenskiObjekat odo) throws Exception;

    private void connect() throws SQLException, IOException {
        dbb.connect();
    }

    private void disconnect() throws SQLException {
        dbb.disconnect();
    }

    private void commit() throws SQLException {
        dbb.commit();
    }

    private void rollback() throws SQLException {
        dbb.rollback();
    }
}
