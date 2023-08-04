import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
// import java.io.*;
// import java.util.*;

public class CalculatorClient {
    private CalculatorClient() {}

    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        try {
            // Get the registry
            Registry registry = LocateRegistry.getRegistry(host);

            // Look up the remote object
            Calculator stub = (Calculator) registry.lookup("Calculator");
            // Stack stk = new Stack();

            // Push values to the stack
            stub.pushValue(12);
            stub.pushValue(18);
            // stub.pushValue(3);
            // stub.pushValue(4);
            // stub.pushValue(5);

            // Push operations to the stack
            stub.pushOperation("gcd");
            // stub.pushOperation("*");
            // stub.pushOperation("+");
            // stub.pushOperation("*");

            // Pop the result
            int result = stub.pop();
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}