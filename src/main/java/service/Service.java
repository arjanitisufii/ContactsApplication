package service;

import database.Datasource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Service {

    public List<Contacts> getContacts() {
        try {
            Statement statement = Datasource.getInstance().getStatement();
            ResultSet resultSet = statement.executeQuery(Datasource.SELECT);
            List<Contacts> list = new ArrayList<>();
            while (resultSet.next()) {
                Contacts contact = new Contacts();
                contact.setName(resultSet.getString(1));
                contact.setPhone(resultSet.getLong(2));
                contact.setLocation(resultSet.getString(3));
                list.add(contact);
            }

            list.sort(Comparator.comparing(Contacts::getName));
            return list;

        } catch (SQLException sqlException) {
            System.out.println("SQLException on getData() : " + sqlException.getMessage());
            sqlException.printStackTrace();
            return null;
        }
    }

    public void insertContact(String name, long phone, String location) {
        try {
            Datasource.getInstance().getInsert().setString(1, name);
            Datasource.getInstance().getInsert().setLong(2, phone);
            Datasource.getInstance().getInsert().setString(3, location);
            Datasource.getInstance().getInsert().execute();
        } catch (SQLException sqlException) {
            System.out.println("SQLException on insertContact() : " + sqlException.getMessage());
        }
    }

    public void deleteContact(String name) {
        if (!ServiceUtility.getInstance().checkIfNameExits(name)) {
            ServiceUtility.getInstance().delete(name);
            System.out.println("Contact deleted successfully");
            return;
        }
        System.out.println("This contact doesn't exits");
    }


    public boolean editContact(String oldName, String newName, long newPhone, String location) {
        //checking if parameters aren't null or empty
        if (ServiceUtility.getInstance().testParameters(oldName) || ServiceUtility.getInstance().testParameters(newName)) {
            return false;
        }
        //checking if phone is negative number
        long phone = ServiceUtility.getInstance().testParameters(newPhone);
        Statement statement = Datasource.getInstance().getStatement();
        StringBuilder sb = new StringBuilder(Datasource.EDIT_CONTACT);
        sb.append(Datasource.TABLE_PHONE + " = \"" + phone + " \", ");
        sb.append(Datasource.TABLE_NAME + " = \"" + newName + " \",");
        sb.append(Datasource.TABLE_LOCATION + " = \"" + location + " \"");
        sb.append(" WHERE " + Datasource.TABLE_NAME + " = \"" + oldName + "\" COLLATE NOCASE");
        try {
            statement.execute(sb.toString());
            return true;
        } catch (SQLException sqlException) {
            return false;
        }
    }
}
