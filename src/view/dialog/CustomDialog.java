package view.dialog;

import controller.CustomDialogController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomDialog extends JDialog {
    private JPanel panel;
    private JLabel label_1;
    private JLabel label_2;
    private JTextField UserNameField;
    private JTextField CardNumberField;
    private JButton confirmButton;
    private JButton cancelButton;

    public CustomDialog(JFrame parent, String title) {
        super(parent, title, true);

        panel = new JPanel();
        panel.setLayout(null);

        label_1 = new JLabel("Username");
        label_1.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
        label_1.setBounds(10, 10, 131, 21);

        UserNameField = new JTextField(20);
        UserNameField.setBounds(150, 10, 200, 25);

        label_2 = new JLabel("Card Number");
        label_2.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
        label_2.setBounds(10, 40, 131, 21);

        CardNumberField = new JTextField(20);
        CardNumberField.setBounds(150, 40, 200, 25);

        confirmButton = new JButton("Confirmer");
        confirmButton.setBounds(50, 80, 100, 30);

        cancelButton = new JButton("Annuler");
        cancelButton.setBounds(200, 80, 100, 30);

        panel.add(label_1);
        panel.add(UserNameField);
        panel.add(label_2);
        panel.add(CardNumberField);
        panel.add(confirmButton);
        panel.add(cancelButton);
        add(panel);

        setSize(400, 160);
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public String getUserName() {
        return UserNameField.getText();
    }
    public int getCardNumber() {
        return Integer.parseInt(CardNumberField.getText());
    }

    public void setController(CustomDialogController controller)
    {
        cancelButton.addActionListener(controller);
        confirmButton.addActionListener(controller);
    }

    public void dialogueMessage(String titre, String message) {
        JOptionPane.showMessageDialog(null, message, titre, JOptionPane.INFORMATION_MESSAGE);
    }

    public void dialogueErreur(String titre, String message) {
        JOptionPane.showMessageDialog(null, message, titre, JOptionPane.ERROR_MESSAGE);
    }

}
