package controller;

import view.dialog.CustomDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomDialogController implements ActionListener {

    private CustomDialog dialog;

    private String username;
    private int cardnumber;

    public CustomDialogController(CustomDialog dialog)
    {
        this.dialog = dialog;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        JButton source = (JButton) e.getSource();

        if (source.getText().equals("Confirmer")) {
            username = dialog.getUserName();
            cardnumber = dialog.getCardNumber();
        } else if (source.getText().equals("Annuler")) {
            dialog.setVisible(false);
        }
    }
}
