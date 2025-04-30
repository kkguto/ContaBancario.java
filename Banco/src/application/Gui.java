package application;

import entities.Account;
import java.awt.*;
import java.util.Scanner;
import javax.swing.*;
import services.FileManager;

public class Gui {

    public static void main(String[] args) {
        FileManager.InitFile();

        Scanner sc = new Scanner(System.in);
        final Account[] account = new Account[1];

        JFrame frame = new JFrame();
        PersonalizeFrame(frame);

        JPanel panel_geral = new JPanel();
        JPanel panel1 = new JPanel();

        JButton create_button = new JButton("Create Account");
        create_button.addActionListener((actionEvent) -> {
            FileManager.AddAccount(FileManager.CreateAccount());
        });
        
        JButton withdraw_button = new JButton("Withdraw Amount");

        withdraw_button.addActionListener(actionEvent -> {
            if (account[0] != null) {
                // account[0].WithDrawMoney(sc);
            } else {
                JOptionPane.showMessageDialog(null, "No account created yet.");
            }
        });

        JButton depos_button = new JButton("Deposit Amount");
        JButton show_button = new JButton("Show Account Information");

        panel1.setLayout(new FlowLayout());
        panel_geral.setLayout(new BoxLayout(panel_geral, BoxLayout.Y_AXIS)); //Deixa na linha vertical
        panel_geral.setBorder(BorderFactory.createTitledBorder("Bank Options"));

        JButton [] buttons = {create_button, withdraw_button, depos_button, show_button};
        for(JButton i : buttons){ 
            PersonalizeButton(i);
        }
        panel_geral.add(panel1);

        for(JButton i : buttons){
            panel1.add(i);
        }

        frame.add(panel_geral, BorderLayout.CENTER);

        frame.setVisible(true); //Make the window visible
    }

    static void PersonalizeButton(JButton button){

        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(new Color(0, 123, 255)); // Azul
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        button.setPreferredSize(new Dimension(200, 40));
        // button.setBorder(BorderFactory.createTitledBorder("border"));
    }

    static void PersonalizeFrame(JFrame frame){
        frame.setTitle("Bank Application");
        frame.setSize(400, 500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    }
    

}
