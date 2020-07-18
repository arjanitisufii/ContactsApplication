package service;


import database.Datasource;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class ServiceUtility {

    private static ServiceUtility serviceUtility = new ServiceUtility();

    private ServiceUtility() {
    }

    public static ServiceUtility getInstance() {
        return serviceUtility;
    }

    //Testing giving number if it matches any regex to get their location
    public String phoneNumbersRegex(String phoneNumber) {

        String phone = testNegativePhoneNumbers(phoneNumber);

        Map<String, String> map = new HashMap<>();
        map.put("1\\d{10}", "USA");
        map.put("34\\d{9}", "Spain");
        map.put("33\\d{10}", "France");
        map.put("49\\d{10}", "Germany");
        map.put("383\\d{8}", "Kosovo");
        map.put("355\\d{10}", "Albania");
        map.put("61\\d{9}", "Australia");
        map.put("90\\d{11}", "Turkey");
        map.put("54\\d{10}", "Argentina");
        map.put("44\\d{10}", "United Kingdom");
        map.put("39\\d{10}", "Italy");

        for (String matched : map.keySet()) {
            if (phone.matches(matched)) {
                return map.get(matched);
            }
        }
        return null;
    }

    public boolean isPhoneNumber(String name) {
        if (name == null || name.length() == 0) {
            return false;
        }

        String phone = testNegativePhoneNumbers(name);

        int count = 0;
        for (int i = 0; i < phone.length(); i++) {
            if (Character.isDigit(phone.charAt(i))) {
                count++;
            }
        }
        return count == phone.length();

    }

    public boolean checkIfPhoneNumberExits(long phone) {
        try {
            long value = testParameters(phone);
            Datasource.getInstance().getCheckPhoneNumber().setLong(1, value);
            int number = 0;
            ResultSet rs = Datasource.getInstance().getCheckPhoneNumber().executeQuery();
            while (rs.next()) {
                number++;
            }
            return number == 0;

        } catch (SQLException s) {
            System.out.println(s.getMessage());
            return false;
        }
    }

    public boolean checkIfNameExits(String name) {
        try {
            if (testParameters(name)) {
                return false;
            }
            int count = 0;
            Datasource.getInstance().getCheckName().setString(1, name);
            ResultSet rs = Datasource.getInstance().getCheckName().executeQuery();
            while (rs.next()) {
                count++;
            }
            return count == 0;
        } catch (SQLException s) {
            System.out.println("SQLException on checkName() : " + s.getMessage());
            return false;
        }
    }

    public void delete(String nameOfContact) {
        try {
            if (testParameters(nameOfContact)) {
                throw new NullPointerException("Name is null");
            }
            Datasource.getInstance().getDeleteName().setString(1, nameOfContact);
            Datasource.getInstance().getDeleteName().execute();
        } catch (SQLException sqlException) {
            System.out.println("SQLException on delete() : " + sqlException.getMessage());
        }
    }

    public boolean checkPhoneNumberLength(String phoneNumber) {
        if (testParameters(phoneNumber)) {
            throw new NullPointerException("Phone number is null");
        }
        String phone = testNegativePhoneNumbers(phoneNumber);
        //long doesn't accept more than 19 numbers
        return phone.length() <= 19;
    }

    public boolean testParameters(String value1) {
        return value1 == null || value1.length() == 0;
    }

    public long testParameters(long phone) {
        return (phone < 0) ? -phone : phone;
    }

    public String testNegativePhoneNumbers(String phone) {
        if (testParameters(phone)) {
            return null;
        }
        if (phone.startsWith("-")) {
            return phone.substring(1);
        }
        return phone;
    }
}
