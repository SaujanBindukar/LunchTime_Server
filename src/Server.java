import bll.UserOrder;
import dao.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    public static void main(String[] args) {
        try {

            VendorDao obj = new VendorDaoImpl();
            StudentDao sd= new StudentImpl();
            FoodDao fd= new FoodDaoImpl();
            UserOrderDao ud= new UserOrderDaoImpl();

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("HelloServer", obj);
            registry.rebind("HelloStudent", sd);
            registry.rebind("HelloFoodMenu", fd);
            registry.rebind("HelloUserOrder", ud);

            System.out.print("Server Started");


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
