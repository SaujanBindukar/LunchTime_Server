/**
 * @author Saujan Bindukar
 * This class is used to start the Server.
 */

import dao.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    public static void main(String[] args) {
        try {
            /** Creating objects of interface */
            VendorDao obj = new VendorDaoImpl();
            StudentDao sd= new StudentImpl();
            FoodDao fd= new FoodDaoImpl();
            UserOrderDao ud= new UserOrderDaoImpl();

            /** Create the registry  and rebind */
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("HelloServer", obj);
            registry.rebind("HelloStudent", sd);
            registry.rebind("HelloFoodMenu", fd);
            registry.rebind("HelloUserOrder", ud);

            /** Message for knowing the server status*/
            System.out.print("Server Started");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
