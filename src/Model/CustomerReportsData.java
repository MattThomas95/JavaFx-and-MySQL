/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static Model.DatabaseConnection.conn;
import static Model.DatabaseConnection.mySQLjdbcDriver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author James
 */
public class CustomerReportsData {

    private static ObservableList<String> allMonths = FXCollections.observableArrayList();
    private static ObservableList<String> allNames = FXCollections.observableArrayList();
    private static ObservableList<CustomerRecords> allCustomerRecords = FXCollections.observableArrayList();
    private static ArrayList<Integer> appointmentsInYear = new ArrayList<Integer>();
    private static ArrayList<Integer> typesInMonth = new ArrayList<Integer>();
    //filtered lambda

    public static void setMonths() {
        allMonths.add("January");
        allMonths.add("February");
        allMonths.add("March");
        allMonths.add("April");
        allMonths.add("May");
        allMonths.add("June");
        allMonths.add("July");
        allMonths.add("August");
        allMonths.add("September");
        allMonths.add("October");
        allMonths.add("November");
        allMonths.add("December");
    }

    public static ObservableList<String> getMonths() {
        return allMonths;
    }

    public static void setAllCustomers() {
        allCustomerRecords.addAll(CustomerRecordsData.getAllCustomerRecords());
    }

    public static void setNames() {
        allCustomerRecords.stream()
                .peek(e -> e.getCustomerName())
                .collect(Collectors.toList());
        System.out.println(Collectors.toList());

        for (CustomerRecords names : allCustomerRecords) {
            allNames.add(names.getCustomerName());
        }
    }

    public static ObservableList<String> getNames() {
        return allNames;
    }

    public static void setCountYear() {

        try {
            Class.forName(mySQLjdbcDriver);
            Statement statement = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT COUNT(*)\n"
                    + "FROM appointment\n"
                    + "WHERE YEAR(start) = YEAR(CURDATE()); ");

            while (set.next()) {
                appointmentsInYear.add(set.getInt(1));
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void setTypesByMonth(int month) {
        try {
            Class.forName(mySQLjdbcDriver);
            Statement statement = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT COUNT(DISTINCT type)\n"
                    + "FROM appointment\n"
                    + "WHERE MONTH(start) =" + month + " AND YEAR(start) = YEAR(CURDATE());");

            while (set.next()) {
                typesInMonth.removeAll(typesInMonth);
                typesInMonth.add(set.getInt(1));
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static Integer getCount() {
        return appointmentsInYear.get(0);
    }

    public static Integer getMonthCount() {
        return typesInMonth.get(0);
    }
}
