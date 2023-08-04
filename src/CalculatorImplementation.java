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

    private Stack<Integer> stk = new Stack<Integer>();

    // Implmentation of pushValue
    public void pushValue(int val) throws RemoteException {
        System.out.println("Pushing value " + val);
        stk.push(val);
    }

    // Implementation of pushOperation
    public void pushOperation(String operator) throws RemoteException {
        System.out.println("Pushing operation " + operator);
        if (operator.equals("min")) {
            // Pop all values on the stk, find the minimum value, and push it back into the stk
            int minimumVal = stk.peek();
            stk.pop();
            while (!stk.empty()) {
                minimumVal = Math.min(minimumVal, stk.peek());
                stk.pop();
            }
            stk.push(minimumVal);
        } else if (operator.equals("max")) {
            // Pop all values on the stk, find the maximum value, and push it back into the stk
            int maximumVal = stk.peek();
            stk.pop();
            while (!stk.empty()) {
                maximumVal = Math.max(maximumVal, stk.peek());
                stk.pop();
            }
            stk.push(maximumVal);
        } else if (operator.equals("lcm")) {
            // Find the least common multiple of all the values on the stk
            while (stk.size() > 1) {
                int a = stk.peek();
                stk.pop();
                int b = stk.peek();
                stk.pop();
                stk.push(lcm(a, b));
            }
            
        } else if (operator.equals("gcd")) {
            // Find the greatest common divisor of all the values on the stk
            while (stk.size() > 1) {
                int a = stk.peek();
                stk.pop();
                int b = stk.peek();
                stk.pop();
                stk.push(gcd(a, b));
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
    public int pop() throws RemoteException {
        System.out.println("Popping value");
        int value = stk.peek();
        stk.pop();
        return value;
    }

    // Implementation of isEmpty
    public boolean isEmpty() throws RemoteException {
        System.out.println("Checking if stk is empty");
        if (stk.empty()) {
            return true;
        }
        return false;
    }

    // Implementation of delayPop
    public int delayPop(int millis) throws RemoteException {
        int value = 0;
        try {
            System.out.println("Delaying pop for " + millis + " milliseconds");
            TimeUnit.MILLISECONDS.sleep(millis);
            value = stk.peek();
            stk.pop();
        } catch (InterruptedException e) {
            System.out.println("Interrupted exception: " + e.toString());
            e.printStackTrace();
        }
        return value;
    }
}