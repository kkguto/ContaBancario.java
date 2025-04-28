package services;

import entities.Account;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class FileManager {

    public static final String FILE_NAME = "accounts.csv";
    public static final String FILE_NAME_TEMP = "accounts_temp.csv";

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

    public static void AddAccount(Account account){

        Random rand = new Random();
        int id = rand.nextInt(1000);
        
        try {
            BufferedWriter bw = new BufferedWriter (new FileWriter(FILE_NAME, true));

            bw.write(id + ";" + account.getHolder() + ";" + account.getPassword() + ";" + account.getBalance());
            bw.newLine();

            bw.close();
        } catch (IOException erro) {
            System.out.println("[ERRO] Failed to inicialize the File: " + erro.getMessage());
        }
    }

    public static void DeleteAccount(Account account, Scanner sc){

        System.out.print("Type the ID to Delete: ");
        String id = sc.next();

        String true_password = VerifyID(account, id);

        if(true_password != null){
            System.out.print("Type the password: ");
            String password = sc.next();
            
            if(password.compareTo(true_password) == 0){
                String []ArrLine = new String[4];

                File new_file = new File(FILE_NAME_TEMP);
                File old_file = new File(FILE_NAME);

                try {
                    BufferedReader br = new BufferedReader(new FileReader(old_file));
                    BufferedWriter wr = new BufferedWriter(new FileWriter(new_file));
                    String linha = br.readLine(); // Read the first line
        
                    while(linha != null){
                        ArrLine = linha.split(";");
                        if(ArrLine[0].compareTo(id) != 0){
                            wr.write(linha);
                            wr.newLine();
                        }
                        linha = br.readLine(); // Read the next line
                    }
                    wr.close();
                    br.close();
        
                    if(!old_file.delete()){
                        System.out.println("[ERRO] Failed to delete the old main file!");
                    }
        
                    if(!new_file.renameTo(old_file)){
                        System.out.println("[ERRO] Failed to rename the new main file!");
                    }
        
                } catch (IOException erro) {
                    System.out.println("[ERRO] Failed to inicialize the File: " + erro.getMessage());
                } 
            } else {
                System.out.println("[ERRO] Wrong password! Try again later.");
            }
        } else {
            System.out.println("The ID " + id + " does not exists!");
        } 
    }

    public static String VerifyID(Account account, String Id_seach){
        String []ArrLine = new String[4];
        try{
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String linha = br.readLine();

            while(linha != null){
                ArrLine = linha.split(";");
                if(ArrLine[0].compareTo(Id_seach) == 0){
                    br.close();
                    return ArrLine[2]; //Return the password
                }
                linha = br.readLine(); //Search next line 
            }
            
            br.close();
        }catch (IOException erro) {
            System.out.println("[ERRO] Failed to inicialize the File: " + erro.getMessage());
        }
        return null;
    }

    public static String ShowAccountInfo(Account account, Scanner sc){
        String []ArrLine = new String[4];

        System.out.print("Type the ID to Delete: ");
        String id = sc.next();

        String true_password = VerifyID(account, id);
        if(true_password != null){
            System.out.print("Type the PassWord: ");
            String password = sc.next();
    
            if(true_password.compareTo(password) == 0){
                try{
                    BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
                    String linha = br.readLine();
        
                    while(linha != null){
                        ArrLine = linha.split(";");
                        if(ArrLine[0].compareTo(id) == 0){
                            br.close();
                            return  "\nAccess allowed\n==========================\nAccount ID: " + ArrLine[0] + 
                                    "\nAccount Holder: " + ArrLine[1] +
                                    "\nAmount: R$ " + ArrLine[3] + 
                                    "\nPassword: " + ArrLine[2];  
                                    //Return the Datas
                        }
                        linha = br.readLine();
                    }
                    
                    br.close();
                }catch (IOException erro) {
                    System.out.println("[ERRO] Failed to inicialize the File: " + erro.getMessage());
                }   
            }
        }else{
            System.out.println("The ID " + id + " does not exists!");
        }

        return "Access Denied!";
    }
    
    public static boolean UpdateAccountAmount(Account account, double amount, String id){
        String []ArrLine = new String[4];

        File new_file = new File(FILE_NAME_TEMP);
        File old_file = new File(FILE_NAME);

        boolean update = false;

        try {
            BufferedWriter wr = new BufferedWriter(new FileWriter(new_file)); 
            BufferedReader br = new BufferedReader(new FileReader(old_file));
            String linha = br.readLine(); // Read the first line

            while(linha != null){
                ArrLine = linha.split(";");

                if(ArrLine[0].compareTo(id) == 0){
                    double current_balance = Double.parseDouble(ArrLine[3]);
                    double new_balance =  current_balance + amount;

                    if(new_balance < 0){
                        wr.close();
                        new_file.delete(); //
                        return false;
                    }
                       
                    String new_line = ArrLine[0] + ";" + ArrLine[1] + ";" + ArrLine[2] + ";" + new_balance;
                    wr.write(new_line);

                    update = true;

                }else{
                    wr.write(linha);
                }

                wr.newLine();
                linha = br.readLine(); // Read the next line
            }
            wr.close();
            br.close();

            if(!old_file.delete()){
                System.out.println("[ERRO] Failed to delete the old main file!");
            }

            if(!new_file.renameTo(old_file)){
                System.out.println("[ERRO] Failed to rename the new main file!");
            }

        } catch (IOException erro) {
            System.out.println("[ERRO] Failed to inicialize the File: " + erro.getMessage());
        }

        return update;

    }
}
