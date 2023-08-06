import java.rmi.*;
import java.rmi.server.*;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.util.*;

public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {
    // Default constructor to throw RemoteException from parent constructor
    CalculatorImplementation() throws RemoteException {
        super();
    }

    private Map<Integer, Stack<Integer>> clientStacks = new HashMap<>();

    // Check if clientID is unique
    public boolean createNewClientID(int clientID) throws RemoteException {
        if (clientStacks.containsKey(clientID)) {
            System.out.println("Client ID " + clientID + " already exists. Please choose a different client ID.");
            return false;
        } else {
            System.out.println("Client ID " + clientID + " created.");
        }
        return true;
    }

    // Create a new stack for the client
    public void createNewClientStack(int clientID) throws RemoteException {
        Stack<Integer> newStack = new Stack<>();
        clientStacks.put(clientID, newStack);
    }

    // Implmentation of pushValue
    public void pushValue(int val, int clientID) throws RemoteException {
        System.out.println("Pushing value " + val);
        clientStacks.get(clientID).push(val);
    }

    // Implementation of pushOperation
    public void pushOperation(String operator, int clientID) throws RemoteException {
        System.out.println("Pushing operation " + operator);
        if (operator.equals("min")) {
            // Pop all values on the clientStacks, find the minimum value, and push it back into the clientStacks
            int minimumVal = clientStacks.get(clientID).peek();
            clientStacks.get(clientID).pop();
            while (!clientStacks.get(clientID).empty()) {
                minimumVal = Math.min(minimumVal, clientStacks.get(clientID).peek());
                clientStacks.get(clientID).pop();
            }
            clientStacks.get(clientID).push(minimumVal);
        } else if (operator.equals("max")) {
            // Pop all values on the clientStacks, find the maximum value, and push it back into the clientStacks
            int maximumVal = clientStacks.get(clientID).peek();
            clientStacks.get(clientID).pop();
            while (!clientStacks.get(clientID).empty()) {
                maximumVal = Math.max(maximumVal, clientStacks.get(clientID).peek());
                clientStacks.get(clientID).pop();
            }
            clientStacks.get(clientID).push(maximumVal);
        } else if (operator.equals("lcm")) {
            // Find the least common multiple of all the values on the clientStacks
            while (clientStacks.get(clientID).size() > 1) {
                int a = clientStacks.get(clientID).peek();
                clientStacks.get(clientID).pop();
                int b = clientStacks.get(clientID).peek();
                clientStacks.get(clientID).pop();
                clientStacks.get(clientID).push(lcm(a, b));
            }
        } else if (operator.equals("gcd")) {
            // Find the greatest common divisor of all the values on the clientStacks
            while (clientStacks.get(clientID).size() > 1) {
                int a = clientStacks.get(clientID).peek();
                clientStacks.get(clientID).pop();
                int b = clientStacks.get(clientID).peek();
                clientStacks.get(clientID).pop();
                clientStacks.get(clientID).push(gcd(a, b));
            }
        } else {
            System.out.println("Invalid operator " + operator);
        }
    }

    // Get the least common multiple of two numbers
    private int lcm(int a, int b) {
        if (a == 0 || b == 0) return 0;
        return Math.abs(a * b) / gcd(a, b);
    }

    // Get the greatest common divisor of two numbers
    private int gcd(int a, int b) {
        int gcd = 1;
        for (int i = 1; i <= a && i <= b; i++) {
            if (a%i == 0 && b%i == 0) {
                gcd = i;
            }
        }
        return gcd;
    }

    // Implementation of pop
    public int pop(int clientID) throws RemoteException {
        System.out.println("Popping value");
        int value = clientStacks.get(clientID).peek();
        clientStacks.get(clientID).pop();
        return value;
    }

    // Implementation of isEmpty
    public boolean isEmpty(int clientID) throws RemoteException {
        System.out.println("Checking if clientStacks is empty");
        return (clientStacks.get(clientID).empty());
    }

    // Implementation of delayPop
    public int delayPop(int millis, int clientID) throws RemoteException {
        int value = 0;
        try {
            System.out.println("Delaying pop for " + millis + " milliseconds");
            // TimeUnit.MILLISECONDS.sleep(millis);
            Thread.sleep(millis);
            value = clientStacks.get(clientID).peek();
            clientStacks.get(clientID).pop();
        } catch (InterruptedException e) {
            System.out.println("Interrupted exception: " + e.toString());
            e.printStackTrace();
        }
        return value;
    }
}