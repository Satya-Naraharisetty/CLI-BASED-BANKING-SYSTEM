package org.banking;

import java.io.*;
import java.sql.*;

public class BankManagement { // these class provides all
    // bank method

    private static final int NULL = 0;

    static Connection con = conn.getConnection();
    static String sqlQuery = "";
    public static boolean createAccount(String CName, int Password) { // create account function
        try {// validation
            if (CName == "" || Password == NULL) {
                System.out.println("All Field Required!");
                return false;
            }
            // query
            Statement st = con.createStatement();
            sqlQuery = "INSERT INTO customer(CName, Balance, Password) values('" + CName + "',1000," + Password + ")";

            // Execution
            if (st.executeUpdate(sqlQuery) == 1) {
                System.out.println(CName + ", Now You Login!");
                return true;
            }
            // return
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Username Not Available!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean loginAccount(String CName, int Password) { // login method
        try {// validation
            if (CName == "" || Password == NULL) {
                System.out.println("All Field Required!");
                return false;
            }
            // query
            sqlQuery = "select * from customer where CName='" + CName + "' and Password=" + Password;
            PreparedStatement st = con.prepareStatement(sqlQuery);
            ResultSet rs = st.executeQuery();
            // Execution
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

            if (rs.next()) {
                // after login menu driven interface method
                int ch = 5;
                int Amount = 0;
                int Sender_Acc = rs.getInt("Acc_No");
                ;
                int receipentAcc;
                while (true) {
                    try {
                        System.out.println("Hello, " + rs.getString("CName"));
                        System.out.println("1) Transfer Money");
                        System.out.println("2) View Balance");
                        System.out.println("5) LogOut");
                        System.out.print("Enter Choice:");
                        ch = Integer.parseInt(sc.readLine());
                        if (ch == 1) {
                            System.out.print("Enter Receiver A/c No:");
                            receipentAcc = Integer.parseInt(sc.readLine());
                            System.out.print("Enter Amount:");
                            Amount = Integer.parseInt(sc.readLine());

                            if (BankManagement.transferMoney(Sender_Acc, receipentAcc, Amount)) {
                                System.out.println("MSG : Money Sent Successfully!\n");
                            }
                            else {
                                System.out.println("ERR : Failed!\n");
                            }
                        }
                        else if (ch == 2) {
                            BankManagement.getBalance(Sender_Acc);
                        }
                        else if (ch == 5) {
                            break;
                        }
                        else {
                            System.out.println("Err : Enter Valid input!\n");
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                return false;
            }
            // return
            return true;
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Username Not Available!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void getBalance(int Acc_No) // fetch balance method
    {
        try {

            // query
            sqlQuery = "select * from customer where Acc_No=" + Acc_No;
            PreparedStatement st = con.prepareStatement(sqlQuery);

            ResultSet rs = st.executeQuery(sqlQuery);
            System.out.println("-----------------------------------------------------------");
            System.out.printf("%12s %10s %10s\n", "Account No", "Name", "Balance");

            // Execution

            while (rs.next()) {
                System.out.printf("%12d %10s %10d.00\n",
                        rs.getInt("Acc_No"),
                        rs.getString("CName"),
                        rs.getInt("Balance"));
            }
            System.out.println(
                    "-----------------------------------------------------------\n");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean transferMoney(int Sender_Acc, int Receiver_Acc, int Amount) throws SQLException { // transfer money method
        // validation
        if (Receiver_Acc == NULL || Amount == NULL) {
            System.out.println("All Field Required!");
            return false;
        }
        try {
            con.setAutoCommit(false);
            sqlQuery = "select * from customer where Acc_No=" + Sender_Acc;
            PreparedStatement ps = con.prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (rs.getInt("Balance") < Amount) {
                    System.out.println("Insufficient Balance!");
                    return false;
                }
            }

            Statement st = con.createStatement();

            // debit
            con.setSavepoint();

            sqlQuery = "update customer set balance=balance-" + Amount + " where Acc_No=" + Sender_Acc;
            if (st.executeUpdate(sqlQuery) == 1) {
                System.out.println("Amount Debited!");
            }

            // credit
            sqlQuery = "update customer set balance=balance+" + Amount + " where Acc_No=" + Receiver_Acc;
            st.executeUpdate(sqlQuery);

            con.commit();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            con.rollback();
        }
        // return
        return false;
    }
}
