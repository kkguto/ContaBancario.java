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
                File f_temp = new File(FILE_NAME_TEMP);
                try {
                    BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
                    BufferedWriter wr = new BufferedWriter(new FileWriter(f_temp));
                    String linha = br.readLine(); // Read the first line
        
                    while(linha != null){
                        ArrLine = linha.split(";");
                        if(ArrLine[0].compareTo(id) != 0){
                            wr.write(linha);
                        }
                        linha = br.readLine(); // Read the next line
                    }
                    wr.close();
                    br.close();
        
                    
                    File main_file = new File(FILE_NAME);
        
                    if(!main_file.delete()){
                        System.out.println("[ERRO] Failed to delete the old main file!");
                    }
        
                    if(!f_temp.renameTo(main_file)){
                        System.out.println("[ERRO] Failed to rename the new main file!");
                    }
        
                } catch (IOException erro) {
                    System.out.println("[ERRO] Failed to inicialize the File: " + erro.getMessage());
                } 
            } else {
                System.out.println("[ERRO] Wrong password! Try again later.");
            }
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
                    return ArrLine[2]; //Return the password
                }
            }
            
            br.close();
        }catch (IOException erro) {
            System.out.println("[ERRO] Failed to inicialize the File: " + erro.getMessage());
        }
        return null;
    }
}
