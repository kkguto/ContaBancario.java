package entities;

import java.util.Scanner;
import services.FileManager;

public class Account {
    private String holder;
    private String password;
    private double balance;

    //Constructor
    public Account(){
        this.holder = "No named";
        this.password = "";
        this.balance = 0.0;
    }

    public Account(String n, String p){
        this.holder = n;
        this.password = p;
    }

    //Methods
    public void WithDrawMoney(Scanner sc){
        Account account = null;
        double amount;
        boolean update = false;

        System.out.print("Type the Account's ID: ");
        String id = sc.next();

        String true_password = FileManager.VerifyID(account, id); //Return the right password or null if the ID does not exist

        if(true_password != null){ //if the "true_password" is not NULL
            System.out.print("Type the password: ");
            String try_password = sc.next();

            if(true_password.compareTo(try_password) == 0){
                System.out.print("Type the amount to Withdraw: ");
                amount = sc.nextDouble();

                update = FileManager.UpdateAccountAmount(account, -amount, id); //Retunr true ou false
                
                if(update){
                    System.out.println("Amount successfully WithDraw!");
                }else{
                    System.out.println("Insufficient funds!");
                }

            }else{
                System.out.println("[ERRO] Wrong password! Try again later.");
            }
        }else{
            System.out.println("[ERRO] This ID does not exist! Try again later.");
        }

    }

    public void DepositMoney(Scanner sc){
        Account account = null;
        
        double amount;
        boolean update = false;
        System.out.print("Type the Account's ID: ");
        String id = sc.next();

        String true_password = FileManager.VerifyID(account, id);

        if(true_password != null){
            System.out.print("Type the password: ");
            String try_password = sc.next();

            if(true_password.compareTo(try_password) == 0){
                System.out.print("Type the amount to Deposit Money: ");
                amount = sc.nextDouble();

                update = FileManager.UpdateAccountAmount(account, amount, id);

                if(update){
                    System.out.println("Amount successfully deposited!");
                }else{
                    System.out.println("Insufficient funds!");
                } 
                
            }else{
                System.out.println("[ERRO] Wrong password! Try again later.");
            }
        }else{
            System.out.println("[ERRO] This ID does not exist! Try again later.");
        }
    }


    @Override
    public String toString(){
        return  "\nAccount Holder: " + holder;
    }

    //Getters and Setters

    //Account Holder
    public String getHolder(){
        return holder;
    }

    public void setHolder(String h){
        this.holder = h;
    }

    //Password
    public String getPassword(){
        return password;
    }

    public void setPassword(String p){
        this.password = p;
    }

    //Value or Amount
    public double getBalance(){
        return balance;
    }
    //Just the getter because i want to they access the amount with only the methods

}
