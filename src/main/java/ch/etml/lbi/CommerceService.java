package ch.etml.lbi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommerceService {

    //sql statement
    private String requeteGetItems = "SELECT items.num, items.description, items.prix, clients.num, clients.prenom, clients.solde FROM items LEFT JOIN clients ON items.client = clients.num;";
    private String requeteCreateClient = "INSERT INTO clients (num, prenom, solde) VALUES(null, ?, ?);";
    private String requeteGetClientByName = "SELECT * FROM clients WHERE prenom = ?;";
    private String requeteGetClientById = "SELECT * FROM clients WHERE num = ?;";

    private DBConnection dbConnection;

    public CommerceService(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<Item> getItems(){

        try(PreparedStatement pstmtGetItems = dbConnection.getConnection().prepareStatement(requeteGetItems)) {
            ResultSet rs = pstmtGetItems.executeQuery();

            List<Item> result = new ArrayList<>();

            while (rs.next()){
                Client client = new Client(0,"", (float) 0.0);
                if (rs.getInt(4) != 0) {
                    client.setNum(rs.getInt(4));
                    client.setPrenom(rs.getString(5));
                    client.setSolde(rs.getFloat(6));
                }
                result.add(new Item(rs.getInt(1), rs.getString(2), rs.getFloat(3), client));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean createClient(String nomClient){
        return checkClient(nomClient);
    }

    private boolean checkClient(String nomClient){
        boolean result = false;
        return result;
    }
}
