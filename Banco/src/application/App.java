package application;

import entities.Account;
import java.util.Scanner;
import services.FileManager;

public class App {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int escolha;
        Account account = null;

        FileManager.InitFile();

        do{
            Menu();
            escolha = sc.nextInt();
            
            switch (escolha) {
                case 1:
                    account = CreateAccount(sc);
                    FileManager.AddAccount(account);
                    break;
                case 2:
                    account.WithDrawMoney(sc);
                    break;

                case 3:
                    account.DepositMoney(sc);
                    break;

                case 4:
                    FileManager.DeleteAccount(account, sc);
                    break;

                case 5:
                    String ac = FileManager.ShowAccountInfo(account, sc);
                    System.out.println(ac);
                    break;
                case 6:
                    System.out.println("App closed!");
                    sc.close();
                    break;                    
                default:
                    System.out.println("[ERRO] Option Invalid! Try Again.");
                    break;
            }
        }while(escolha != 6);

        
    }
    
    static Account CreateAccount(Scanner sc){
        
        System.out.print("Type the name of Account Holder: ");
        String holder = sc.next();
        System.out.print("Type the PassWord: ");
        String password = sc.next();

        return new Account(holder, password);
    }

    static void Menu(){
        System.out.println("\n======== MENU ========");
        System.out.println("======================");
        System.out.println("[1] Create Account");
        System.out.println("[2] Withdraw Amount");
        System.out.println("[3] Deposit Amount");
        System.out.println("[4] Delete Account");
        System.out.println("[5] Show Account Information");
        System.out.println("[6] Exit");
        System.out.println("======================");
        System.out.print("Enter your choice: ");
    }   
}
