/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contohdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author putuguna
 */
public class DatabaseFunctions {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    /**
     * this method used for load the driver of mySQL
     */
    public void loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Failed load Driver " + ex.getMessage());
        }
    }

    public void getConnection() {
        try {

            loadDriver();

            String urlDatabase = "jdbc:mysql://localhost/student"; //alamat database
            String user = "root"; //user yang dipakai utk akses database
            String password = ""; //password yang digunakan utk akses database
            connection = DriverManager.getConnection(urlDatabase, user, password);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Connection Failed! " + ex.getMessage());
        }
    }

    public void InsertData(String name, String majors) {
        try {

            String queryInsert = "INSERT INTO personaldata (studentname, studentmajors) VALUES ('" + name + "','" + majors + "')";

            statement = connection.createStatement();
            statement.executeUpdate(queryInsert);
            statement.close();
            JOptionPane.showMessageDialog(null, "Insert Successfully");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Insert Failed " + ex.getMessage());
            Logger.getLogger(DatabaseFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateDataByID(String name, String majors, String id) {
        try {
            String queryUpdate = "UPDATE personaldata SET studentname='" + name + "', studentmajors = '" + majors + "' WHERE idstudent=" + id;
            statement = connection.createStatement();
            statement.executeUpdate(queryUpdate);
            statement.close();
            JOptionPane.showMessageDialog(null, "Update Successfully");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Update Failed" + ex.getMessage());
            Logger.getLogger(DatabaseFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteDataByID(int id) {
        try {
            String deleteQuery = "DELETE FROM personaldata WHERE idstudent=" + id;
            statement = connection.createStatement();
            statement.executeUpdate(deleteQuery);
            statement.close();
            JOptionPane.showMessageDialog(null, "Delete Successfully");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Delete Failed" + ex.getMessage());
        }
    }

    public void showIdOnComboBox(javax.swing.JComboBox cb) {

        DefaultComboBoxModel model = new DefaultComboBoxModel();
        cb.setModel(model);
        try {

            String showDataQuery = "SELECT * FROM personaldata";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(showDataQuery);
          
            while (resultSet.next()) {
                model.addElement("id : " + resultSet.getString("idstudent") + " (" + resultSet.getString("studentname") + " )");
            }

            statement.close();
            resultSet.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Failed show data in combobox " + ex.getMessage());
            Logger.getLogger(DatabaseFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showDataByID(javax.swing.JTextField etName, javax.swing.JTextField etMajors, String id) {
        try {
            String queryDisplay = "SELECT studentname, studentmajors FROM personaldata WHERE idstudent=" + Integer.parseInt(id);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryDisplay);

            while (resultSet.next()) {
                etName.setText(resultSet.getString("studentname"));
                etMajors.setText(resultSet.getString("studentmajors"));
            }
            statement.close();
            resultSet.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Failed show data by id " + ex.getMessage());
            Logger.getLogger(DatabaseFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void showAllData(javax.swing.JTable table) {

        try {

            Object header[] = {"ID Student", "Student Name", "Student Majors"};
            DefaultTableModel model = new DefaultTableModel(null, header);
            table.setModel(model);

            String queryDisplay = "SELECT * FROM personaldata";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryDisplay);

            while (resultSet.next()) {
                Object[] objectDataStudent = new Object[3];
                objectDataStudent[0] = resultSet.getString("idstudent");
                objectDataStudent[1] = resultSet.getString("studentname");
                objectDataStudent[2] = resultSet.getString("studentmajors");
                model.addRow(objectDataStudent);

            }
            statement.close();
            resultSet.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
