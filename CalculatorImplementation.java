import java.rmi.*;
import java.rmi.server.*;
import java.util.concurrent.TimeUnit;

public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {
    // Default constructor to throw RemoteException from parent constructor
    CalculatorImplementation() throws RemoteException {
        super();
    }

    // Implmentation of pushValue
    public void pushValue(int val, Stack<Integer> stack) throws RemoteException {
        System.out.println("Pushing value " + val);
        stack.push(val);
    }

    // Implementation of pushOperation
    public void pushOperation(String operator, Stack<Integer> stack) throws RemoteException {
        System.out.println("Pushing operation " + operator);
        if (operator == "min") {
            // Pop all values on the stack, find the minimum value, and push it back into the stack
            int minimumVal = stack.top();
            stack.pop();
            while (!stack.empty()) {
                minimumVal = Math.min(minimumVal, stack.top());
                stack.pop();
            }
            stack.push(minimumVal);
        } else if (operator == "max") {
            // Pop all values on the stack, find the maximum value, and push it back into the stack
            int maximumVal = stack.top();
            stack.pop();
            while (!stack.empty()) {
                maximumVal = Math.max(maximumVal, stack.top());
                stack.pop();
            }
        } else if (operator == "lcm") {
            // Find the least common multiple of all the values on the stack
            
            } 
        } else if (operator == "gcd") {
            // Find the greatest common divisor of all the values on the stack

        } else {
            System.out.println("Invalid operator " + operator);
        }
    }

    // Implementation of pop
    public int pop(Stack<Integer> stack) throws RemoteException {
        System.out.println("Popping value");
        int value = stack.top();
        stack.pop();
        return value;
    }

    // Implementation of isEmpty
    public boolean isEmpty(Stack<Integer> stack) throws RemoteException {
        System.out.println("Checking if stack is empty");
        if (stack.empty()) {
            return true;
        }
        return false;
    }

    // Implementation of delayPop
    public int delayPop(int millis, Stack<Integer> stack) throws RemoteException {
        System.out.println("Delaying pop for " + millis + " milliseconds");
        TimeUnit.MILLISECONDS.sleep(millis);
        int value = stack.top();
        stack.pop();
        return value;
    }
}