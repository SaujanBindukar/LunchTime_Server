package dao;

import utils.DbConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserOrderDaoImpl extends UnicastRemoteObject implements UserOrderDao {
    Connection cn= DbConnection.myConnection();
    public UserOrderDaoImpl() throws RemoteException {
    }

    @Override
    public ResultSet getUserOrder() throws RemoteException, SQLException {
        ResultSet rs= cn.createStatement().executeQuery("select * from user_order");
        return rs;
    }
}
