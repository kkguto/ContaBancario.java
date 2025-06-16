package br.com.descomplica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class GuiFX extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        FileManager.InitFile();

        Label title = new Label("Bank Application");

        Button createBtn = new Button("Create Account");
        Button withdrawBtn = new Button("Withdraw Amount");
        Button depositBtn = new Button("Deposit Amount");
        Button deleteBtn = new Button("Delete Account");
        Button showBtn = new Button("Show Account Information");

        
        createBtn.setOnAction(e -> handleCreate()); 
        withdrawBtn.setOnAction(e -> handleWithdraw()); 
        depositBtn.setOnAction(e -> handleDeposit()); 
        deleteBtn.setOnAction(e -> handleDelete()); 
        showBtn.setOnAction(e -> handleShow());

       VBox root = new VBox(20, title, createBtn, withdrawBtn, depositBtn, deleteBtn, showBtn);
        root.setId("root");

        Scene scene = new Scene(root, 400, 500);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setTitle("Bank Application - JavaFX");

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    

    private void handleCreate(){
        TextInputDialog cpfDialog = new TextInputDialog();
        cpfDialog.setHeaderText("Enter CPF (XXX.XXX.XXX-YY)");
        Optional<String> cpf = cpfDialog.showAndWait();

        if (cpf.isPresent()) {
            if (!Cpf.VerificationCpf(cpf.get())) {
                showAlert("Invalid CPF.");
                return;
            }
            if (Cpf.SearchCpf(cpf.get())) {
                showAlert("CPF already exists.");
                return;
            }
            TextInputDialog holderDialog = new TextInputDialog();
            holderDialog.setHeaderText("Enter account holder's name.");
            Optional<String> holder = holderDialog.showAndWait();

            TextInputDialog passwordDialog = new TextInputDialog();
            passwordDialog.setHeaderText("Enter password.");
            Optional<String> password = passwordDialog.showAndWait();

            if (holder.isPresent() && password.isPresent()) {
                Account newAccount = new Account(holder.get(), password.get(), cpf.get()); 
                FileManager.AddAccount(newAccount);
                showAlert("Account successfully created.");
            }
        }
    }
    

    private void handleWithdraw(){
        TextInputDialog cpfDialog = new TextInputDialog();
        cpfDialog.setHeaderText("Enter account's CPF.");
        Optional<String> cpf = cpfDialog.showAndWait();

        if (cpf.isPresent()) {
            if (validation(cpf.get())) {
                TextInputDialog amountDialog = new TextInputDialog();
                amountDialog.setHeaderText("How much to withdraw?");
                
                Optional<String> amount = amountDialog.showAndWait();

                if (amount.isPresent()) {
                    double withdraw = Double.parseDouble(amount.get()) * -1;
                    boolean success = FileManager.UpdateAccountAmount(withdraw, cpf.get());

                    showAlert(success ? "Withdraw successful!" : "Failed.");
                }
            }
        }
    }
    

    private void handleDeposit(){
        TextInputDialog cpfDialog = new TextInputDialog();
        cpfDialog.setHeaderText("Enter account's CPF.");
        Optional<String> cpf = cpfDialog.showAndWait();

        if (cpf.isPresent()) {
            if (validation(cpf.get())) {
                TextInputDialog amountDialog = new TextInputDialog();
                amountDialog.setHeaderText("How much to deposit?");
                
                Optional<String> amount = amountDialog.showAndWait();

                if (amount.isPresent()) {
                    double deposit = Double.parseDouble(amount.get()); 
                    boolean success = FileManager.UpdateAccountAmount(deposit, cpf.get());

                    showAlert(success ? "Deposit successful!" : "Failed.");
                }
            }
        }
    }
    

    private void handleDelete(){
        TextInputDialog cpfDialog = new TextInputDialog();
        cpfDialog.setHeaderText("Enter account's CPF.");
        Optional<String> cpf = cpfDialog.showAndWait();

        if (cpf.isPresent()) {
            if (validation(cpf.get())) {
                
                Alert confirmation = new Alert(AlertType.CONFIRMATION);
                confirmation.setHeaderText("Confirm deletion.");
                
                Optional<ButtonType> result = confirmation.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    boolean success = FileManager.DeleteAccount(cpf.get());

                    showAlert(success ? "Deleted successfully!" : "Failed.");
                }
            }
        }
    }
    

    private void handleShow(){
        TextInputDialog cpfDialog = new TextInputDialog();
        cpfDialog.setHeaderText("Enter account's CPF.");
        Optional<String> cpf = cpfDialog.showAndWait();

        if (cpf.isPresent()) {
            if (validation(cpf.get())) {
                
                try (BufferedReader br = new BufferedReader(new FileReader(FileManager.FILE_NAME))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(";");
                        if (Cpf.JustDigits(parts[0]).equals(Cpf.JustDigits(cpf.toString()))) {
                            showAlert("Account Information:\nCPF: " + parts[0] + 
                                      "\nName: " + parts[1] + 
                                      "\nBalance: " + parts[3]);
                            return;
                        }
                    }
                } catch (IOException ex) {
                    showAlert("Error accessing files.");
                }
            }
        }
    }
    

    private boolean validation(String cpf) {
        if (cpf == null) return false;

        String true_pass = Cpf.GetPasswordByCpf(cpf);
        if (true_pass == null) {
            showAlert("CPF not found.");
            return false;
        }
        TextInputDialog passwordDialog = new TextInputDialog();
        passwordDialog.setHeaderText("Enter password.");
        Optional<String> password = passwordDialog.showAndWait();

        if (password.isPresent()) {
            if (true_pass.equals(password.get())) {
                return true;
            }
        }
        showAlert("Invalid password.");
        return false;
    }
    

    private void showAlert(String message){
        Alert a = new Alert(AlertType.INFORMATION);
        a.setHeaderText(message);
        a.showAndWait();
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}
