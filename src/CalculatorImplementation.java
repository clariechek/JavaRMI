import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {
    // Default constructor to throw RemoteException from parent constructor
    CalculatorImplementation() throws RemoteException {
        super();
    }

    // Map of clientID to client stack
    private Map<Integer, Stack<Integer>> clientStacks = new HashMap<>();

    // Check if clientID is unique
    public boolean createNewClientID(int clientID) throws RemoteException {
        if (clientStacks.containsKey(clientID)) {
            return false;
        }
        return true;
    }

    // Create a new stack for the client
    public void createNewClientStack(int clientID) throws RemoteException {
        Stack<Integer> newStack = new Stack<>();
        clientStacks.put(clientID, newStack);
    }

    // Adds a new value to the client stack
    public void pushValue(int val, int clientID) throws RemoteException {
        System.out.println("Pushing value " + val);
        clientStacks.get(clientID).push(val);
    }

    // Implements an operation to the values on the client stack
    public void pushOperation(String operator, int clientID) throws RemoteException {
        System.out.println("Pushing operation " + operator);

        // Perform the operation on the client
        if (operator.equals("min")) {
            // Iterate through the values on the client stack and find the minimum value
            int minimumVal = clientStacks.get(clientID).peek();
            clientStacks.get(clientID).pop();
            while (!clientStacks.get(clientID).empty()) {
                minimumVal = Math.min(minimumVal, clientStacks.get(clientID).peek());
                clientStacks.get(clientID).pop();
            }
            // Push the minimum value to the client stack
            clientStacks.get(clientID).push(minimumVal);
        } else if (operator.equals("max")) {
            // Iterate through the values on the client stack and find the maximum value
            int maximumVal = clientStacks.get(clientID).peek();
            clientStacks.get(clientID).pop();
            while (!clientStacks.get(clientID).empty()) {
                maximumVal = Math.max(maximumVal, clientStacks.get(clientID).peek());
                clientStacks.get(clientID).pop();
            }
            // Push the maximum value to the client stack
            clientStacks.get(clientID).push(maximumVal);
        } else if (operator.equals("lcm")) {
            // Check if the stack has at least 2 values
            if (clientStacks.get(clientID).size() < 2) {
                System.out.println("Error: At least two values are required to perform an operation.");
                return;
            }

            // Check if stack contains 0. If yes, clear stack and return error message.
            if (hasZero(clientID)) {
                System.out.println("Error: Cannot perform lcm on 0. Removing all values from stack.");
                return;
            }

            // Find the least common multiple of all the absolute values on the client stack
            while (clientStacks.get(clientID).size() > 1) {
                int a = Math.abs(clientStacks.get(clientID).peek());
                clientStacks.get(clientID).pop();
                int b = Math.abs(clientStacks.get(clientID).peek());
                clientStacks.get(clientID).pop();
                // Push the least common multiple into the stack
                clientStacks.get(clientID).push(lcm(a, b));
            }
        } else if (operator.equals("gcd")) {
            // Check if the stack has at least 2 values
            if (clientStacks.get(clientID).size() < 2) {
                System.out.println("Error: At least two values are required to perform an operation.");
                return;
            }

            // Find the greatest common divisor of the absolute values on the clientStacks
            while (clientStacks.get(clientID).size() > 1) {
                int a = Math.abs(clientStacks.get(clientID).peek());
                clientStacks.get(clientID).pop();
                int b = Math.abs(clientStacks.get(clientID).peek());
                clientStacks.get(clientID).pop();
                // Push the greatest common divisor into the stack
                clientStacks.get(clientID).push(gcd(a, b));
            }
        } else {
            System.out.println("Invalid operator " + operator);
        }
    }

    // Returns the least common multiple of two numbers
    private int lcm(int a, int b) {
        if (a == 0 || b == 0) return 0;
        return Math.abs(a * b) / gcd(a, b);
    }

    // Returns the greatest common divisor of two numbers
    private int gcd(int a, int b) {
        int gcd = 1;
        for (int i = 1; i <= a && i <= b; i++) {
            if (a%i == 0 && b%i == 0) {
                gcd = i;
            }
        }
        return gcd;
    }

    // Removes the top value from the client stack and returns the value
    public int pop(int clientID) throws RemoteException {
        // Check if stack has at least 1 value before popping. If empty, return error message.
        if (isEmpty(clientID)) {
            System.out.println("Error: Stack is empty. Please push a value before popping.");
            return -1;
        } 

        // Pop the value
        System.out.println("Popping value");
        int value = clientStacks.get(clientID).peek();
        clientStacks.get(clientID).pop();
        return value;
    }

    // Checks if the client stack is empty
    public boolean isEmpty(int clientID) throws RemoteException {
        System.out.println("Checking if client stack is empty");
        return (clientStacks.get(clientID).empty());
    }

    // Waits for 'millis' milliseconds before popping the top value from the client stack
    public int delayPop(int millis, int clientID) throws RemoteException {
        int value = 0;
        try {
            System.out.println("Delaying pop for " + millis + " milliseconds");
            // Wait for 'millis' milliseconds before poping
            Thread.sleep(millis);
            value = clientStacks.get(clientID).peek();
            clientStacks.get(clientID).pop();
        } catch (InterruptedException e) {
            System.out.println("Interrupted exception: " + e.toString());
            e.printStackTrace();
        }
        return value;
    }

    // Checks if the client stack has at least 2 values
    public boolean hasTwoValues(int clientID) throws RemoteException {
        System.out.println("Checking if client stack has at least 2 values");
        return (clientStacks.get(clientID).size() >= 2);
    }

    // Check if the client stack contains 0. If yes, removes all values from the stack.
    public boolean hasZero(int clientID) throws RemoteException {
        System.out.println("Checking if client stack has value 0");
        // If the client stack contains 0, remove all values from the stack
        if (clientStacks.get(clientID).contains(0)) {
            while (!clientStacks.get(clientID).empty()) {
                clientStacks.get(clientID).pop();
            }
            return true;
        }
        return false;
    }
}