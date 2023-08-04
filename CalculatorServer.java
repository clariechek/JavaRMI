import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorServer {
    public CalculatorServer() {}

    public static void main(String args[]) {
        try {
            // Create a new instance of the Calculator server
            Calculator stub = new CalculatorImplementation();

            // Export the object of the Calculator server to the RMI runtime
            // Calculator stub = (Calculator) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Calculator", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
    
}