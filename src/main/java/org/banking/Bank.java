package org.banking;

import java.io.*;

public class Bank {
    public static void main(String[] args) throws IOException { //main class of bank

        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        String CName = "";
        int Password;
        int Acc_No;
        int ch;

        while (true) {
            System.out.println("\n ->|| Welcome to our Bank ||<- \n");
            System.out.println("1)Create Account");
            System.out.println("2)Login Account");

            try {
                System.out.print("\n Enter Input:"); //user input
                ch = Integer.parseInt(sc.readLine());

                switch (ch) {
                    case 1:
                        try {
                            System.out.print("Enter Unique UserName:");
                            CName = sc.readLine();
                            System.out.print("Enter New Password:");
                            Password = Integer.parseInt(sc.readLine());

                            if (BankManagement.createAccount(CName, Password)) {
                                System.out.println("MSG : Account Created Successfully!\n");
                            }
                            else {
                                System.out.println("ERR : Account Creation Failed!\n");
                            }
                        }
                        catch (Exception e) {
                            System.out.println(" ERR : Enter Valid Data::Insertion Failed!\n");
                        }
                        break;

                    case 2:
                        try {
                            System.out.print("Enter UserName:");
                            CName = sc.readLine();
                            System.out.print("Enter Password:");
                            Password = Integer.parseInt(sc.readLine());

                            if (BankManagement.loginAccount(CName, Password)) {
                                System.out.println("MSG : Logout Successfully!\n");
                            }
                            else {
                                System.out.println("ERR : login Failed!\n");
                            }
                        }
                        catch (Exception e) {
                            System.out.println(" ERR : Enter Valid Data::Login Failed!\n");
                        }

                        break;

                    default:
                        System.out.println("Invalid Entry!\n");
                }

                if (ch == 5) {
                    System.out.println("Exited Successfully!\n\n Thank You :)");
                    break;
                }
            }
            catch (Exception e) {
                System.out.println("Enter Valid Entry!");
            }
        }
        sc.close();
    }
}
