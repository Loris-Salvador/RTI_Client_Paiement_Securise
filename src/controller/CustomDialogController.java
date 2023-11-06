package controller;

import VESPAPS.ReponsePayFacture;
import VESPAPS.RequetePayFacture;
import view.dialog.CustomDialog;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class CustomDialogController implements ActionListener {

    private CustomDialog dialog;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private String username;
    private int cardnumber;
    private int idFacture;
    private SecretKey cleSession;

    public CustomDialogController(CustomDialog dialog, ObjectOutputStream oos, ObjectInputStream ois, int idFacture, SecretKey cleSession)
    {
        this.dialog = dialog;
        this.oos = oos;
        this.ois = ois;
        this.idFacture = idFacture;
        this.cleSession = cleSession;
    }




    @Override
    public void actionPerformed(ActionEvent e) {

        JButton source = (JButton) e.getSource();

        if (source.getText().equals("Confirmer")) {
            username = dialog.getUserName();
            cardnumber = dialog.getCardNumber();

            System.out.println("Card nulber : " + cardnumber);

            try {

                RequetePayFacture requete = new RequetePayFacture(idFacture, username, cardnumber, cleSession);
                oos.writeObject(requete);
                ReponsePayFacture reponse = (ReponsePayFacture) ois.readObject();

                if(!reponse.VerifyAuthenticity(cleSession))
                    dialog.dialogueErreur("PAYER", "Error VerifyAuthenticity()");
                if (reponse.isValide()) {
                    dialog.dialogueMessage("PAYER", reponse.getMessage());
                    dialog.setVisible(false);

                } else {
                    dialog.dialogueErreur("PAYER", reponse.getMessage());
                }
            }
            catch (IOException | ClassNotFoundException | NoSuchPaddingException | IllegalBlockSizeException |
                   NoSuchAlgorithmException | BadPaddingException | NoSuchProviderException | InvalidKeyException ex)
            {
                System.out.println(ex.getMessage());
            }
        } else if (source.getText().equals("Annuler")) {
            dialog.setVisible(false);
        }

    }
}
