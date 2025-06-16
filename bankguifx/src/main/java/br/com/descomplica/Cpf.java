package br.com.descomplica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Cpf {

    public static final String FILE_NAME = "accounts.csv";

    static boolean AllTheSame(String cpf){
        
        for(int i = 1; i < 11; i++){
            if(cpf.charAt(i) != cpf.charAt(0)){
                return false;
            }
        }
         
        return true;
    }

     public static String JustDigits(String cpf){
        String numeros = "";

        // Remove qualquer caractere não numérico
        for (int i = 0; i < cpf.length(); i++) {
            if (Character.isDigit(cpf.charAt(i))) {
                numeros += cpf.charAt(i);
            }
        }

        return numeros;
     }
    
    public static boolean VerificationCpf(String cpf){
        String numeros = JustDigits(cpf);

        // Verifica se o CPF tem 11 dígitos
        if (numeros.length() != 11) {
            System.out.println("[ERRO] Invalid CPF: It must contain exactly 11 digits.");
            return false;
        }
    
        // Verifica se todos os dígitos são iguais
        if (AllTheSame(numeros)) {
            System.out.println("[ERRO] Invalid CPF: all digits are the same, which is not allowed.");
            return false;
        }
    
        // Validação do primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (numeros.charAt(i) - '0') * (10 - i);
        }
        int resultado1 = (soma * 10) % 11;
        if (resultado1 == 10) resultado1 = 0;
    
        // Validação do segundo dígito verificador
        int soma2 = 0;
        for (int i = 0; i < 10; i++) {
            soma2 += (numeros.charAt(i) - '0') * (11 - i);
        }
        int resultado2 = (soma2 * 10) % 11;
        if (resultado2 == 10) resultado2 = 0;
    
        // Verifica se os dígitos batem
        return (resultado1 == (numeros.charAt(9) - '0')) && (resultado2 == (numeros.charAt(10) - '0')); //return false or true
    }

    public static String GetPasswordByCpf(String cpf){
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))){
            String linha = br.readLine();
            
            while (linha != null){
                String[] ArrLine = linha.split(";");
                System.out.println("Comparando: arquivo=" + ArrLine[0].trim() + ", entrada=" + cpf);
                if(ArrLine[0].equals(cpf)){
                    return ArrLine[2];
                }
                linha = br.readLine();
            }
            
        }catch(IOException erro) {
            System.out.println("[ERRO] Failed to inicialize the File: " + erro.getMessage());
        }

        return null;
    }

    public static Double GetBalanceByCpf(String cpf){
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))){
            String linha = br.readLine();
            
            while (linha != null){
                String[] ArrLine = linha.split(";");

                if(ArrLine[0].equals(cpf)){
                    Double balance = Double.parseDouble(ArrLine[3]);
                    return balance;
                }
                linha = br.readLine();
            }
            
        }catch(IOException erro) {
            System.out.println("[ERRO] Failed to inicialize the File: " + erro.getMessage());
        }

        return -1.0;
    }

    public static Boolean SearchCpf(String cpf){
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))){
            String linha = br.readLine();

            while (linha != null){
                String[] ArrLine = linha.split(";");
                if(ArrLine[0].equals(cpf)){
                    return true;
                }

                linha = br.readLine();
            }

        }catch(IOException erro) {
            System.out.println("[ERRO] Failed to inicialize the File: " + erro.getMessage());
        }

        return false;
    }
}

