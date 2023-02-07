package ch.etml.lbi;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        DBConnection dbC = DBConnection.getInstance();
        CommerceService commerceService = new CommerceService(dbC);

        JFrame frame = new JFrame("commerce");
        frame.setSize(300,350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MainPannel(commerceService));
        frame.setVisible(true);

        for (Item item : commerceService.getItems()) {
            System.out.println("id : " + item.getNum() + " descr : " + item.getDescription() + " prix : " + item.getPrix() + " client : " + item.getClient().getPrenom());
        }

        commerceService.createClient("test");
        Client clientTest = commerceService.getClient("test");
        System.out.println(clientTest.getNum() + " " + clientTest.getPrenom() + " " + clientTest.getSolde());
    }
}