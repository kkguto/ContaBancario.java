package br.com.descomplica;

import java.io.*;
import java.util.Scanner;

public class FileManager{

    public static final String FILE_NAME = "accounts.csv";
    public static final String FILE_NAME_TEMP = "accounts_temp.csv";
    public static Scanner sc = new Scanner(System.in);
    public static Account account = new Account();

    public static void InitFile(){
        File f = new File(FILE_NAME);

        try{
            if(f.createNewFile()){
                System.out.println("File successfuly create");
            }else{
                System.out.println("File already exists");
            }
        }catch(IOException erro){
            System.out.println("[ERRO] Failed to inicialize the File: " + erro.getMessage());
        }
    }

    public static Account CreateAccount(){

        System.out.print("Type the CPF (XXX.XXX.XXX-YY): ");
        String cpf = sc.next();

        if(!Cpf.VerificationCpf(cpf)){
            System.out.println("[ERRO] Invalid CPF - CPF does not exist!");
            return null;
        }

        if(Cpf.SearchCpf(cpf)){
            System.out.println("[ERRO] This CPF already exists in DataBase");
            return null;
        }

        System.out.print("Type the name of Account Holder: ");
        String holder = sc.next();
        System.out.print("Type the PassWord: ");
        String password = sc.next();
        return new Account(holder, password, cpf);
    }

    public static void AddAccount(Account account){
        if(account != null){
            try(BufferedWriter bw = new BufferedWriter (new FileWriter(FILE_NAME, true))) {
    
                bw.write(account.getCPF() + ";" + account.getHolder() + ";" + account.getPassword() + ";" + account.getBalance());
                bw.newLine();

            } catch (IOException erro) {
                System.out.println("[ERRO] Failed to inicialize the File: " + erro.getMessage());
            }
        }
    }

    public static boolean  DeleteAccount(String cpf){
        File new_file = new File(FILE_NAME_TEMP);
        File old_file = new File(FILE_NAME);

        try (
            BufferedReader br = new BufferedReader(new FileReader(old_file));
            BufferedWriter bw = new BufferedWriter(new FileWriter(new_file))
        ){
        
            String linha = br.readLine(); // Read the first line

            while(linha != null){
                String[] ArrLine = linha.split(";");
                if(!(ArrLine[0].equals(cpf))){
                    bw.write(linha);
                    bw.newLine();
                }
                linha = br.readLine(); // Read the next line
            }

        } catch (IOException erro) {
            System.out.println("[ERRO] Failed to inicialize the File: " + erro.getMessage());
            return false;
        }

        if (!old_file.delete()) {
            System.out.println("[ERRO] Could not delete the old file.");
            return false;
        }

        if (!new_file.renameTo(old_file)) {
            System.out.println("[ERRO] Could not rename the new file.");
            return false;
        }
        
        return true;
    }    
    

    public static String ShowAccountInfo(){
        System.out.print("Type the CPF: ");
        String cpf = sc.next();

        String true_password = Cpf.GetPasswordByCpf(cpf);

        if(true_password != null){
            System.out.print("Type the PassWord: ");
            String password = sc.next();
    
            if(true_password.equals(password)){
                try(BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))){
                    String linha = br.readLine();
        
                    while(linha != null){
                        String []ArrLine = linha.split(";");
                        if(ArrLine[0].equals(cpf)){
                            return  "\nAccess allowed\n==========================\nAccount ID: " + ArrLine[0] + 
                                    "\nAccount Holder: " + ArrLine[1] +
                                    "\nAmount: R$ " + ArrLine[3] + 
                                    "\nPassword: " + ArrLine[2];  
                                    //Return the Datas
                        }
                        linha = br.readLine();
                    }
                }catch (IOException erro) {
                    System.out.println("[ERRO] Failed to inicialize the File: " + erro.getMessage());
                }   
            }
        }else{
            System.out.println("The CPF " + cpf + " does not exists!");
        }

        return "Access Denied!";
    }
    
    public static void DepositOrWithdraw(int operationType){
        double amount;
        boolean update = false;
        
        System.out.print("Type the Account's CPF: ");
        String cpf = sc.next();
        sc.nextLine();
        String true_password = Cpf.GetPasswordByCpf(cpf);

        if(true_password != null){
            System.out.print("Type the password: ");
            String try_password = sc.next();

            if(try_password.equals(true_password)){

                if(operationType == 1){
                    System.out.print("Type the amount to Withdraw: ");
                    amount = sc.nextDouble();

                    if(amount > 0){
                        update = FileManager.UpdateAccountAmount(-amount, cpf);    
                    }else {
                        System.out.println("[ERRO] Invalid amount. Amount to Withdraw must be greater than zero.");
                    }

                }else{
                    System.out.print("Type the amount to Deposit Money: ");
                    amount = sc.nextDouble();

                    if(amount > 0){
                        update = FileManager.UpdateAccountAmount(amount, cpf);    
                    }else {
                        System.out.println("[ERRO] Invalid amount. Deposit must be greater than zero.");
                    }
                
                }

                if(update){
                    if(operationType == 1){
                        System.out.println("Amount successfully WithDraw!");
                    }else{
                        System.out.println("Amount successfully deposited!");
                    }
                } else if (amount > 0) { 
                    System.out.println("[ERRO] Operation failed. Check balance or try again.");
                }
            }else{
                System.out.println("[ERRO] Wrong password! Try again later.");
            }
        }else{
            System.out.println("[ERRO] This CPF does not exist! Try again later.");
        }
    }

    public static boolean UpdateAccountAmount(double amount, String cpf){
        boolean updated = false;

        File new_file = new File(FILE_NAME_TEMP);
        File old_file = new File(FILE_NAME);

        double current_balance = Cpf.GetBalanceByCpf(cpf);
        double new_balance = current_balance + amount;

        if (new_balance < 0) {
            System.out.println("[ERRO] Insufficient funds.");
            return updated;
        }

        try (
            BufferedReader br = new BufferedReader(new FileReader(old_file));
            BufferedWriter bw = new BufferedWriter(new FileWriter(new_file))
        ) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] fields = line.split(";");
                if (fields.length < 4) continue;
    
                if (fields[0].equals(cpf)) {

                    String new_line = fields[0] + ";" + fields[1] + ";" + fields[2] + ";" + new_balance;
                    bw.write(new_line);
                    updated = true;
                } else {
                    bw.write(line);
                }
                bw.newLine();
            }
    
        } catch (IOException e) {
            System.out.println("[ERRO] Error during update: " + e.getMessage());
            return false;
        }

        // Agora, fecha garantidamente os arquivos e faz a substituição
        if (!old_file.delete()) {
            System.out.println("[ERRO] Could not delete old file.");
            return false;
        }
    
        if (!new_file.renameTo(old_file)) {
            System.out.println("[ERRO] Could not rename temporary file.");
            return false;
        }
    
        return updated;

    }

}