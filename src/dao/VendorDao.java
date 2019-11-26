package dao;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;

public interface VendorDao extends Remote {
    Boolean checkVendor(String vendor_email, String password) throws RemoteException;
}
