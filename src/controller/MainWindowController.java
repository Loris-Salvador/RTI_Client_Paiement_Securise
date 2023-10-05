package hepl.be.controller;


import hepl.be.VESPAP.*;
import hepl.be.model.Facture;
import hepl.be.view.window.CustomDialog;
import hepl.be.view.window.WindowClient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import java.awt.event.MouseListener;


public class MainWindowController implements ActionListener, MouseListener {

    private WindowClient mainWindow;
    private Socket socket;

    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private int CurrentIdArticle = 0;

    public MainWindowController(WindowClient mainWindow)
    {
        this.mainWindow = mainWindow;
        mainWindow.setPublicite("Loris le BOT");

        oos = null;
        ois = null;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton)
        {
            JButton source = (JButton) e.getSource();

            if(source.getText().equals("Login"))
            {
                Login();
            }
            else if(source.getText().equals("Logout"))
            {
                Logout();
            }
            else if(source.getText().equals("Payer"))
            {
                Payer();
            }
        }
        else if (e.getSource() instanceof JTextField) {
            JTextField source = (JTextField) e.getSource();

            if(source.getToolTipText().equals("NumClient"))
            {
                GetFactures();
            }
        }

    }

    public void mouseClicked(MouseEvent e) {
        JTable source = (JTable) e.getSource();
        if (source.getToolTipText().equals("Facture"))
        {

        }
    }

    private void Login()
    {
        Properties prop = new Properties();

        try (FileInputStream fis = new FileInputStream("src/main/resources/properties.properties")) {
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String ipServeur = prop.getProperty("Serveur");
        int portServeur = Integer.parseInt(prop.getProperty("Port"));
        String login = mainWindow.getNom();
        String password = mainWindow.getMotDePasse();
        boolean isNew = mainWindow.isNouveauEmployeChecked();

        try
        {
            socket = new Socket(ipServeur,portServeur);
            System.out.println("yo1");
            RequeteLogin requete = new RequeteLogin(login,password,isNew);
            System.out.println("yo2");
            oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("yo3");
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("yo4");
            oos.writeObject(requete);
            System.out.println("yo5");
            ReponseLogin reponse = (ReponseLogin) ois.readObject();
            System.out.println("yo6");
            if (reponse.isValide())
            {
                mainWindow.LoginOK();
            }
            else
            {
                mainWindow.dialogueErreur("LOGIN", reponse.getMessage());
                socket.close();
            }
        }
        catch (IOException | ClassNotFoundException ex)
        {
            mainWindow.dialogueErreur("LOGIN", "Problème de connexion");
        }

    }

    private void Logout()
    {
        try
        {
            RequeteLogout requete = new RequeteLogout(mainWindow.getNom()); //Je pense que le getnom sert à rien
            oos.writeObject(requete);
            oos.close();
            ois.close();
            socket.close();

            mainWindow.LogoutOK();
        }
        catch (IOException ex)
        {
            mainWindow.dialogueErreur("LOGOUT", "Problème de connexion");
        }

    }

    private void Payer()
    {
        try
        {
            CustomDialog Dialog = new CustomDialog(mainWindow, "Payer");
            Dialog.setVisible(true);

            RequetePayFacture requete = new RequetePayFacture(mainWindow.getIndiceFactureSelectionne(), Dialog.getUserName(), Dialog.getCardNumber());
            oos.writeObject(requete);
            ReponsePayFacture reponse = (ReponsePayFacture) ois.readObject();

            if(reponse.isValide())
            {
                mainWindow.dialogueMessage("PAYER", reponse.getMessage());
                GetFactures(); //Refresh de la table
            }
            else
            {
                mainWindow.dialogueErreur("PAYER", reponse.getMessage());
            }
        }
        catch (IOException | ClassNotFoundException ex)
        {
            mainWindow.dialogueErreur("LOGOUT", "Problème de connexion");
        }
    }

    private void GetFactures()
    {
        try
        {
            RequeteGetFactures requete = new RequeteGetFactures(mainWindow.getNumClient());
            oos.writeObject(requete);
            ReponseGetFactures reponse = (ReponseGetFactures) ois.readObject();

            mainWindow.videTableFacture();
            for (Facture fact : reponse.getTableauFactures()) {
                mainWindow.ajouteFactureTable(fact.getIdFacture(), fact.getDate(), fact.getMontant(), fact.getPaye());
            }

        }
        catch (IOException | ClassNotFoundException ex)
        {
            mainWindow.dialogueErreur("LOGOUT", "Problème de connexion");
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}