package application;

import entities.Account;
import java.util.Scanner;

public class App {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int escolha;
        Account account = null;

        do{
            Menu();
            escolha = sc.nextInt();
            String password = null;
            switch (escolha) {
                case 1:
                    account = CreateAccount(sc);
                    break;
                case 2:
                    if (account == null) {
                        System.out.println("No account created yet!");
                        break;
                    }

                    System.out.print("Type the PassWord: ");
                    password = sc.next();

                    account.WithDrawMoney(password, sc);
                    break;
                case 3:
                    if (account == null) {
                        System.out.println("No account created yet!");
                        break;
                    }

                    System.out.print("Type the amount you want to deposit: ");
                    double amount = sc.nextDouble();

                    account.DepositMoney(amount);
                    break;

                case 4:
                    
                    break;

                case 5:
                    if (account == null) {
                        System.out.println("No account created yet!");
                        break;
                    }

                    System.out.print("Type the PassWord: ");
                    password = sc.next();

                    System.out.println(account.ShowAccountInfo(password));
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
