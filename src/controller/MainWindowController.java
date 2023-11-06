package controller;


import VESPAPS.*;
import model.Article;
import model.Facture;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import view.dialog.CustomDialog;
import view.window.WindowClient;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Objects;
import java.util.Properties;
import java.util.TimerTask;


public class MainWindowController implements ActionListener, MouseListener {

    private WindowClient mainWindow;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private SecretKey cleSession;

    public MainWindowController(WindowClient mainWindow) throws FileNotFoundException, KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        this.mainWindow = mainWindow;
        mainWindow.setPublicite("Ca y est");
        oos = null;
        ois = null;
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton)
        {
            JButton source = (JButton) e.getSource();

            if(source.getText().equals("Login"))
            {
                try {
                    Login();
                } catch (CertificateException ex) {
                    throw new RuntimeException(ex);
                } catch (KeyStoreException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }
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

            System.out.println("TextField");

            if(source.getToolTipText().equals("NumClient"))
            {
                GetFactures();
            }
        }

    }

    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse Clicked");
        JTable source = (JTable) e.getSource();
        if (source.getToolTipText().equals("Facture"))
        {
            int idFacture = (int) source.getValueAt(source.getSelectedRow(), 0);

            try {
                RequeteGetArticles requete = new RequeteGetArticles(idFacture, RecupereClePriveeClient());

                oos.writeObject(requete);
                ReponseGetArticles reponse = (ReponseGetArticles) ois.readObject();

                if(!reponse.getMessage(cleSession).equals("OK"))
                    mainWindow.dialogueErreur("GetArticles", reponse.getMessage(cleSession));

                mainWindow.videTableArticle();

                for (Article art : reponse.getArticles(cleSession))
                {
                    mainWindow.ajouteArticleTable(art.getIntitule(), art.getPrixUnitaire(), art.getQuantite());
                }

            }
            catch (IOException | ClassNotFoundException | KeyStoreException | UnrecoverableKeyException |
                   NoSuchAlgorithmException | CertificateException | NoSuchProviderException | InvalidKeyException |
                   SignatureException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex)
            {
                mainWindow.dialogueErreur("Erreur", "Error requete Article " + ex.getMessage());
            }

        }
    }

    private void Login() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        Properties prop = new Properties();

        try (FileInputStream fis = new FileInputStream("properties.properties")) {
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
            socket.setSoTimeout(2000);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());


            if(isNew) // Envois asymétriquement car avec digest on a pas de mdp
            {
                RequeteLoginNewEmploye requete = new RequeteLoginNewEmploye(login,password,RecupereClePubliqueServeur());
                oos.writeObject(requete);
            }
            else // Envois digest salé
            {
                RequeteLogin requete = new RequeteLogin(login,password);
                oos.writeObject(requete);
            }

            //Reception de la reponse
            ReponseLogin reponse = (ReponseLogin) ois.readObject();

            PrivateKey clePrivee = RecupereClePriveeClient();

            String message = reponse.getMessage(clePrivee);
            boolean valide = reponse.getValide(clePrivee);

            if (valide)
            {
                cleSession = reponse.getCleSession(clePrivee);
                mainWindow.LoginOK();
                mainWindow.dialogueMessage("LOGIN", message);
            }
            else
            {
                mainWindow.dialogueErreur("LOGIN", message);
                socket.close();
            }
        }
        catch (IOException | ClassNotFoundException | NoSuchProviderException | UnrecoverableKeyException |
               NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex)
        {
            mainWindow.dialogueErreur("LOGIN", "Problème de connexion" + ex.getMessage());
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

        CustomDialog Dialog = new CustomDialog(mainWindow, "Payer");
        CustomDialogController controller = new CustomDialogController(Dialog, this.oos, this.ois, mainWindow.getIndiceFactureSelectionne(), cleSession);
        Dialog.setController(controller);
        Dialog.setVisible(true);

        GetFactures();
        mainWindow.videTableArticle();
    }

    private void GetFactures()
    {
        try
        {
            RequeteGetFactures requete = new RequeteGetFactures(mainWindow.getNumClient(), RecupereClePriveeClient());
            oos.writeObject(requete);
            ReponseGetFactures reponse = (ReponseGetFactures) ois.readObject();

            if(!reponse.getMessage(cleSession).equals("OK"))
                mainWindow.dialogueErreur("GetFactures", reponse.getMessage(cleSession));

            mainWindow.videTableFacture();
            for (Facture fact : reponse.getTableauFactures(cleSession)) {
                mainWindow.ajouteFactureTable(fact.getIdFacture(), fact.getDate(), fact.getMontant(), fact.getPaye());
            }

        }
        catch (IOException | ClassNotFoundException | KeyStoreException | UnrecoverableKeyException |
               NoSuchAlgorithmException | CertificateException | InvalidKeyException | NoSuchProviderException |
               SignatureException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex)
        {
            mainWindow.dialogueErreur("Erreur", "Problème de connexion " + ex.getMessage());
        }
    }



    public static PrivateKey RecupereClePriveeClient() throws KeyStoreException, IOException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream("KeystoreClient.jks"),"client".toCharArray());
        PrivateKey cle = (PrivateKey) ks.getKey("Client","client".toCharArray());
        return cle;
    }

    public static PublicKey RecupereClePubliqueServeur() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream("KeystoreClient.jks"),"client".toCharArray());
        X509Certificate certif = (X509Certificate)ks.getCertificate("Serveur");
        PublicKey cle = certif.getPublicKey();
        return cle;
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