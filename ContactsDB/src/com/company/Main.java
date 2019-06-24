package com.company;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final String DB_NAME = "theContactsDB.db";
    private static final String CONNECTION = "jdbc:sqlite:/home/IdeaProjects/ContactsDB/" + DB_NAME;
    private static final String TABLE_CONTACTS = "contacts";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_EMAIL = "email";
    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {

        boolean quit = false;


        while (!quit) {

            try {

                Connection connection = DriverManager.getConnection(CONNECTION);
                Statement statement = connection.createStatement();
                statement.execute("CREATE TABLE if not exists " + TABLE_CONTACTS + " (" + COLUMN_NAME + " text, "
                        + COLUMN_PHONE + " text, " + COLUMN_EMAIL + " text)");

                printActions();
                int check = scanner.nextInt();
                scanner.nextLine();

                switch (check) {

                    case 0:

                        System.out.println("\nShutting down...");
                        quit = true;
                        break;


                    case 1:

                        ResultSet results = statement.executeQuery("select * from " + TABLE_CONTACTS);
                        while (results.next()) {
                            System.out.println(results.getString(COLUMN_NAME) + " --- " + results.getString(COLUMN_PHONE) + " --- " + results.getString(COLUMN_EMAIL));
                        }

                        results.close();
                        break;


                    case 2:

                        System.out.println("Enter the contact name: ");
                        String name1 = scanner.nextLine();
                        System.out.println("Enter the contact phone number: ");
                        String phone1 = scanner.nextLine();
                        System.out.println("Enter the contact email: ");
                        String email1 = scanner.nextLine();

                        String sql1 = "insert into " + TABLE_CONTACTS + " (" + COLUMN_NAME +
                                ", " + COLUMN_PHONE + ", " + COLUMN_EMAIL + ")" + " values (?, ?, ?)";
                        PreparedStatement preStatement = connection.prepareStatement(sql1);
                        preStatement.setString(1, name1);
                        preStatement.setString(2, phone1);
                        preStatement.setString(3, email1);
                        preStatement.executeUpdate();

                        preStatement.close();
                        statement.close();
                        connection.close();
                        break;


                    case 3:

                        System.out.println("Enter the name of the contact you wanna update: ");
                        String updateName = scanner.nextLine();
                        System.out.println("Enter the contact name: ");
                        String name2 = scanner.nextLine();
                        System.out.println("Enter the contact phone number: ");
                        String phone2 = scanner.nextLine();
                        System.out.println("Enter the contact email: ");
                        String email2 = scanner.nextLine();

                        String sql2 = "update " + TABLE_CONTACTS + " set " + COLUMN_NAME + "=?, " + COLUMN_PHONE +
                                "=?, " + COLUMN_EMAIL + "=? where " + COLUMN_NAME + "=?";
                        PreparedStatement preStatement2 = connection.prepareStatement(sql2);
                        preStatement2.setString(1, name2);
                        preStatement2.setString(2, phone2);
                        preStatement2.setString(3, email2);
                        preStatement2.setString(4, updateName);
                        preStatement2.executeUpdate();

                        preStatement2.close();
                        statement.close();
                        connection.close();
                        break;


                    case 4:

                        System.out.println("Enter the name of the contact you wanna delete: ");
                        String name3 = scanner.nextLine();
                        String sql3 = "delete from " + TABLE_CONTACTS + " where " + COLUMN_NAME + "=?";
                        PreparedStatement preStatement3 = connection.prepareStatement(sql3);
                        preStatement3.setString(1, name3);
                        preStatement3.executeUpdate();

                        preStatement3.close();
                        statement.close();
                        connection.close();
                        break;


                    default:

                        System.out.println("Please enter only numbers between 0 and 4.");
                        break;


                }

            } catch (SQLException e) {

                System.out.println("Something went wrong --> " + e.getMessage());
                e.printStackTrace();
                break;

            } catch (InputMismatchException exception){

                System.out.println("Invalid input, enter only an integer value!");
                scanner.nextLine();
            }
        }
        scanner.close();

    }

    private static void printActions() {

        System.out.println("\nPress:");
        System.out.println("0  - to shutdown.\n" +
                "1  - to print the list of contacts.\n" +
                "2  - to insert a new contacts.\n" +
                "3  - to update an existing contact.\n" +
                "4  - to remove an existing contact.\n");
    }
}