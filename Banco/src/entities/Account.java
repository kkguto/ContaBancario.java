package entities;

import java.util.Scanner;

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
    public void WithDrawMoney(String password, Scanner sc){
        if(this.password.compareTo(password) == 0){ 
            System.out.print("Type the amount you want to Withdraw: ");
            double amount = sc.nextDouble();

            if(this.balance >= amount){
                this.balance -= amount;
                System.out.println("Amount successfully WithDraw!");
            }else{
                System.out.println("Insufficient funds!");
            } 
        }else{
            System.out.println("Wrong password!");
        }

    }

    public void DepositMoney(double value){
        if(value > 0){
            this.balance += value;
            System.out.println("Amount successfully Deposited into Account!");
        }else{
            System.out.println("[ERRO] Deposit attempt with Negative Numbers! Try again with numbers greater than zero.\n");
        }

    }

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
