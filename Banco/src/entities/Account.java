package entities;

public class Account {
    private String holder;
    private String password;
    private String cpf;
    private double balance;

    //Constructor
    public Account(){
        this.holder = "No named";
        this.cpf = "";
        this.password = "";
        this.balance = 0.0;
    }

    public Account(String n, String p, String cpf){
        this.holder = n;
        this.password = p;
        this.cpf = cpf;
        this.balance = 0.0;
    }

    //Methods

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        
        return builder.toString();
    }

    //Getters and Setters

    //Account Holder
    public String getHolder(){
        return holder;
    }

    public void setHolder(String h){
        this.holder = h;
    }

    //CPF
    public String getCPF(){
        return cpf;
    }

    public void setCPF(String cpf){
        this.cpf = cpf;
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
