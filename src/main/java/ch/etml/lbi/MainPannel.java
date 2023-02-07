package ch.etml.lbi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

public class MainPannel extends JPanel {
    private CommerceService commerceService;
    JTextArea itemsDisplay = new JTextArea();

    public MainPannel(CommerceService commerceService) {
        this.commerceService = commerceService;

        setLayout(new BorderLayout());

        JPanel north = new JPanel();
        JPanel center = new JPanel();
        JPanel south = new JPanel();

        add(north, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        JTextField prenomField = new JTextField();
        JTextField itemField = new JTextField();

        JTextArea actionDisplay = new JTextArea();

        JButton btnAchete = new JButton("Achete");
        prenomField.setColumns(4);
        itemField.setColumns(1);

        north.add(new JLabel("item: "));
        north.add(itemField);
        north.add(new JLabel("Prenom: "));
        north.add(prenomField);
        north.add(btnAchete);

        center.add(itemsDisplay);
        south.add(actionDisplay);

        updateItemsDisplay();

        btnAchete.addActionListener((e) -> {
            String msg = commerceService.achatItem(Integer.parseInt(itemField.getText()), prenomField.getText());
            actionDisplay.setText(msg);
            updateItemsDisplay();
        });
    }

    private void updateItemsDisplay() {
        StringBuilder itemsDisplayBuilder = new StringBuilder();

        for (Item item : commerceService.getItems()) {
            itemsDisplayBuilder.append(Integer.toString(item.getNum()));
            itemsDisplayBuilder.append(", " + item.getDescription());
            itemsDisplayBuilder.append(", " + Float.toString(item.getPrix()));
            itemsDisplayBuilder.append((", " + item.getClient().getPrenom() + "\r\n"));
        }
        itemsDisplay.setText(itemsDisplayBuilder.toString());
    }
}
