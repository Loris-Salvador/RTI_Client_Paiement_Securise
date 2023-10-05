package view.window;

import controller.MainWindowController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WindowClient extends JFrame {
    private JPanel centralwidget;
    private JButton pushButtonLogout;
    private JButton pushButtonLogin;
    private JLabel label_2;
    private JLabel label;
    private JTextField lineEditMotDePasse;
    private JTextField lineEditNom;
    private JCheckBox checkBoxNouveauClient;
    private JPanel frame;
    private JLabel label_4;
    private JTextField lineEditNumClient;
    private JButton pushButtonPayer;
    private JLabel label_5;
    private JTable contour_Facture;
    private DefaultTableModel modelFacture;
    private JTable tableWidgetArticle;
    private JLabel label_8;
    private JPanel frame_2;
    private JTable tableWidgetFacture;
    private JTextField lineEditPublicite;
    private JMenuBar menubar;

    public WindowClient() {
        setTitle("Le Maraicher en ligne");
        setSize(770, 618);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        centralwidget = new JPanel();
        centralwidget.setLayout(null);

        pushButtonLogout = new JButton("Logout");
        pushButtonLogout.setEnabled(false);
        pushButtonLogout.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
        pushButtonLogout.setBackground(new Color(252, 175, 62));
        pushButtonLogout.setBounds(520, 10, 91, 25);

        pushButtonLogin = new JButton("Login");
        pushButtonLogin.setBounds(420, 10, 91, 25);
        pushButtonLogin.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
        pushButtonLogin.setBackground(new Color(138, 226, 52));

        label_2 = new JLabel("Mot de passe:");
        label_2.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
        label_2.setBounds(180, 10, 131, 21);

        label = new JLabel("Nom:");
        label.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
        label.setBounds(8, 10, 64, 21);

        lineEditMotDePasse = new JTextField();
        lineEditMotDePasse.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
        lineEditMotDePasse.setHorizontalAlignment(JTextField.CENTER);
        lineEditMotDePasse.setBounds(298, 10, 113, 25);

        lineEditNom = new JTextField();
        lineEditNom.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
        lineEditNom.setHorizontalAlignment(JTextField.CENTER);
        lineEditNom.setBounds(58, 10, 113, 25);

        checkBoxNouveauClient = new JCheckBox("Nouveau employe");
        checkBoxNouveauClient.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
        checkBoxNouveauClient.setBounds(620, 10, 151, 23);

        frame = new JPanel();
        frame.setLayout(null);
        frame.setBounds(10, 50, 731, 241);
        frame.setBorder(BorderFactory.createRaisedBevelBorder());



        label_4 = new JLabel("Numéro Client :");
        label_4.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
        label_4.setBounds(20, 30, 140, 21);

        lineEditNumClient = new JTextField();
        lineEditNumClient.setToolTipText("NumClient");
        lineEditNumClient.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
        lineEditNumClient.setHorizontalAlignment(JTextField.CENTER);
        lineEditNumClient.setBounds(120, 60, 100, 25);
        lineEditNumClient.setEnabled(false);


        pushButtonPayer = new JButton("Payer");
        pushButtonPayer.setEnabled(false);
        pushButtonPayer.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
        pushButtonPayer.setBackground(Color.lightGray);
        pushButtonPayer.setBounds(20, 196, 200, 25);

        label_5 = new JLabel("Facture :");
        label_5.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
        label_5.setBounds(245, 10, 82, 21);

        contour_Facture = new JTable();
        contour_Facture.setBackground(Color.LIGHT_GRAY);
        contour_Facture.setBounds(240, 35, 481, 195);

        modelFacture = new DefaultTableModel();

        modelFacture.addColumn("idFacture");
        modelFacture.addColumn("date");
        modelFacture.addColumn("montant");
        modelFacture.addColumn("paye");

        tableWidgetFacture = new JTable(modelFacture);
        tableWidgetFacture.setToolTipText("Facture");
        tableWidgetFacture.setBounds(245, 40, 471, 185);


        label_8 = new JLabel("Articles :");
        label_8.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
        label_8.setBounds(10, 345, 64, 17);

        frame_2 = new JPanel();
        frame_2.setLayout(null);
        frame_2.setBounds(10, 370, 731, 201);
        frame_2.setBorder(BorderFactory.createRaisedBevelBorder());


        DefaultTableModel modelArticle = new DefaultTableModel();
        modelArticle.addColumn("Article");
        modelArticle.addColumn("Prix à l'unité");
        modelArticle.addColumn("Quantité");

        tableWidgetArticle = new JTable(modelArticle);
        tableWidgetArticle.setBounds(10, 10, 711, 181);


        lineEditPublicite = new JTextField();
        lineEditPublicite.setEditable(false);
        lineEditPublicite.setFont(new Font("Courier 10 Pitch", Font.BOLD, 18));
        lineEditPublicite.setHorizontalAlignment(JTextField.CENTER);
        lineEditPublicite.setBackground(Color.YELLOW);
        lineEditPublicite.setForeground(Color.red);
        lineEditPublicite.setBounds(10, 300, 751, 41);

        menubar = new JMenuBar();
        menubar.setBounds(0, 0, 770, 22);


        // Add components to centralwidget
        centralwidget.add(pushButtonLogout);
        centralwidget.add(pushButtonLogin);
        centralwidget.add(label_2);
        centralwidget.add(label);
        centralwidget.add(lineEditMotDePasse);
        centralwidget.add(lineEditNom);
        centralwidget.add(checkBoxNouveauClient);
        centralwidget.add(frame);
        centralwidget.add(label_8);
        centralwidget.add(frame_2);
        centralwidget.add(lineEditPublicite);

        // Add components to frame
        frame.add(label_4);
        frame.add(lineEditNumClient);
        frame.add(pushButtonPayer);
        frame.add(label_5);
        frame.add(tableWidgetFacture);
        frame.add(contour_Facture);

        // Add components to frame_2
        frame_2.add(tableWidgetArticle);

        // Add centralwidget to the frame
        setContentPane(centralwidget);
        setVisible(true);
    }


    public void setController(MainWindowController mainWindowController)
    {
        pushButtonLogin.addActionListener(mainWindowController);
        pushButtonLogout.addActionListener(mainWindowController);
        pushButtonPayer.addActionListener(mainWindowController);
        lineEditNumClient.addActionListener(mainWindowController);
        tableWidgetFacture.addMouseListener((MouseListener) mainWindowController);
    }

    public void setNom(String nom)
    {
        lineEditNom.setText(nom);
    }
    public String getNom()
    {
        return lineEditNom.getText();
    }
    public void setMotDePasse(String motDePasse)
    {
        lineEditMotDePasse.setText(motDePasse);
    }
    public String getMotDePasse()
    {
        return lineEditMotDePasse.getText();
    }
    public void setPublicite(String text){
        lineEditPublicite.setText(text);
    }

    public int getNumClient()
    {
        return Integer.parseInt(lineEditNumClient.getText());
    }
    public boolean isNouveauEmployeChecked()
    {
        return checkBoxNouveauClient.isSelected();

    }

    public void LoginOK()
    {
        pushButtonLogin.setEnabled(false);
        pushButtonLogout.setEnabled(true);
        lineEditNom.setEnabled(false);
        lineEditMotDePasse.setEnabled(false);
        checkBoxNouveauClient.setEnabled(false);
        lineEditNumClient.setEnabled(true);

        pushButtonPayer.setEnabled(true);
        tableWidgetFacture.setEnabled(true);
        tableWidgetArticle.setEnabled(true);
    }

    public void LogoutOK()
    {

        pushButtonLogin.setEnabled(true);
        pushButtonLogout.setEnabled(false);
        lineEditNom.setEnabled(true);
        lineEditMotDePasse.setEnabled(true);
        checkBoxNouveauClient.setEnabled(true);

        pushButtonPayer.setEnabled(false);
        tableWidgetFacture.setEnabled(false);
        tableWidgetArticle.setEnabled(false);
        lineEditNumClient.setEnabled(false);


        setMotDePasse("");
        setNom("");

        if(checkBoxNouveauClient.isSelected())
            checkBoxNouveauClient.setSelected(false);

        videTableFacture();
        videTableArticle();
    }

    public void ajouteFactureTable(int idFacture, Date date, float montant, boolean paye) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); // Spécifiez le format de la date
        String dateEnString = format.format(date);

        String p;
        if(paye)
            p = "Paye";
        else
            p = "Non-Paye";


        DefaultTableModel model = (DefaultTableModel) tableWidgetFacture.getModel();
        model.addRow(new Object[]{idFacture, dateEnString, String.format("%.2f", montant)+"€",p});
    }

    public void videTableFacture() {
        DefaultTableModel model = (DefaultTableModel) tableWidgetFacture.getModel();
        model.setRowCount(0);
    }

    public int getIndiceFactureSelectionne()
    {
        int selectedRow = tableWidgetFacture.getSelectedRow();
        return Integer.parseInt(modelFacture.getValueAt(selectedRow, 0).toString());
    }

    public void ajouteArticleTable(String article, float prix, int quantite) {

        DefaultTableModel model = (DefaultTableModel) tableWidgetArticle.getModel();
        model.addRow(new Object[]{article, String.format("%.2f", prix)+"€", quantite});
    }



    public void videTableArticle() {
        DefaultTableModel model = (DefaultTableModel) tableWidgetArticle.getModel();
        model.setRowCount(0);
    }


    public void dialogueMessage(String titre, String message) {
        JOptionPane.showMessageDialog(null, message, titre, JOptionPane.INFORMATION_MESSAGE);
    }

    public void dialogueErreur(String titre, String message) {
        JOptionPane.showMessageDialog(null, message, titre, JOptionPane.ERROR_MESSAGE);
    }


}
