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
    private String requeteUpdateSoldeClient = "UPDATE clients SET solde = ? WHERE prenom = ?;";
    private String requeteUpdateItemClient = "UPDATE items SET client = ? WHERE num = ?;";

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
        if(getClient(nomClient) != null) return false;
        try(PreparedStatement pstmtCreateClient = dbConnection.getConnection().prepareStatement(requeteCreateClient)) {
            pstmtCreateClient.setString(1, nomClient);
            pstmtCreateClient.setInt(2, 100);
            pstmtCreateClient.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String achatItem(int idItem, String nomClient){
        createClient(nomClient);
        Client client = getClient(nomClient);
        List<Item> items = getItems();
        String msg = "";
        for (Item item : items){
            if (item.getNum() != idItem) continue;
            if (item.getClient().getPrenom() != null && !item.getClient().getPrenom().isEmpty()) {
                msg = "l'item appartient déjà à " + item.getClient().getPrenom();
                continue;
            }
            if (client.getSolde() >= item.getPrix()){
                item.setClient(client);
                updateItemClient(item.getNum(), client.getNum());
                client.setSolde(client.getSolde() - item.getPrix());
                updateSoldeClient(client.getPrenom(), client.getSolde());
                msg = "achat effectué";
            }
            else msg = "pas assé d'argent";
        }

        return msg;
    }

    public void updateItemClient(int idItem, int idClient) {
        try (PreparedStatement pstmtUpdateItemClient = dbConnection.getConnection().prepareStatement(requeteUpdateItemClient)) {
            pstmtUpdateItemClient.setInt(1, idClient);
            pstmtUpdateItemClient.setInt(2, idItem);
            pstmtUpdateItemClient.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateSoldeClient(String prenomClient, float newSolde){
        try(PreparedStatement pstmtUpdateSoldeClient = dbConnection.getConnection().prepareStatement(requeteUpdateSoldeClient)) {
            pstmtUpdateSoldeClient.setFloat(1, newSolde);
            pstmtUpdateSoldeClient.setString(2, prenomClient);
            pstmtUpdateSoldeClient.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Client getClient(String nomClient){
        try(PreparedStatement pstmtCheckClient = dbConnection.getConnection().prepareStatement(requeteGetClientByName)) {
            pstmtCheckClient.setString(1, nomClient);
            ResultSet rs = pstmtCheckClient.executeQuery();
            if (rs.next()){
                return new Client(rs.getInt(1), rs.getString(2), rs.getFloat(3));
            }
            else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
