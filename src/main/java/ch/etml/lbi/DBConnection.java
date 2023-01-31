package ch.etml.lbi;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static volatile DBConnection instance = null;
    private Connection connection = null;

    // Get instance pour le singleton
    public static DBConnection getInstance(){
        if(instance == null){
            instance = new DBConnection();
        }
        return instance;
    }

    // Constructeur privé pour le singleton
    private DBConnection() {
        try {
            InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties");

            Properties prop = new Properties();
            prop.load(input);

            String url = prop.getProperty("url");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");

            System.out.println( "Données: " + url + username + password);

            connection = (Connection) DriverManager.getConnection(url, username, password);
            System.out.println("Connection ouverte !");

        } catch (SQLException | IOException e) {
            System.out.println("Problème de connexion" + e.getMessage());
        }

    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void close() throws SQLException {
        if ( connection != null ) {
            this.connection.close();
            System.out.println("Connexion fermée !");
        }
    }
}