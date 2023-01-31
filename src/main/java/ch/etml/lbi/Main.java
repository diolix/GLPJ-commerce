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
        frame.setVisible(true);
        frame.setSize(1000,1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MainPannel(commerceService));

        for (Item item : commerceService.getItems()) {
            System.out.println("id : " + item.getNum() + " descr : " + item.getDescription() + " prix : " + item.getPrix() + " client : " + item.getClient().getPrenom());
        }

    }
}