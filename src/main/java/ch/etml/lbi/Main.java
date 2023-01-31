package ch.etml.lbi;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        DBConnection dbC = DBConnection.getInstance();
        ResultSet rs = null;

        try {
            Statement statement = dbC.getConnection().createStatement();
            rs = statement.executeQuery("SELECT * FROM clients");

            while (rs.next()) {
                int id = rs.getInt("num");
                String prenom = rs.getString("prenom");

                System.out.println( id + " " + prenom );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                rs.close();
                dbC.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}