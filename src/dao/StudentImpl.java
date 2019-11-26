package dao;

import bll.Student;
import utils.DbConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentImpl extends UnicastRemoteObject implements StudentDao {
    Connection cn= DbConnection.myConnection();
    public StudentImpl() throws RemoteException {
        super();
    }

    @Override
    public void addVendor(Student s) throws RemoteException {
        try {
            String sql = "INSERT INTO user(FirstName, LastName, Email, PhoneNumber, Password) VALUES(?,?,?,?,?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, s.getFirstName());
            ps.setString(2, s.getLastName());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getPhoneNumber());
            ps.setString(5, s.getPassword());
            ps.execute();

        } catch (SQLException e) {
            System.out.print(e);

        }

    }
}
