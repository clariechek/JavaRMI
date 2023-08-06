import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.*;
import java.util.*;

public class CalculatorClient {
    // Default constructor to increment clientIDCount and assign clientID
    private CalculatorClient() {}

    // Displays the menu of options for the user
    private void printMenu() {
        System.out.println("MAIN MENU");
        System.out.println("What would you like to do? Enter the number of the option you would like to choose.");
        System.out.println("1. Push value");
        System.out.println("2. Push operation");
        System.out.println("3. Pop");
        System.out.println("4. Check if stack is empty?");
        System.out.println("5. Delay pop");
        System.out.println("6. Exit");
    }

    // Performs the pushValue option. Pushes one value at a time.
    private void pushValueOption(Calculator stub, int clientID) {
        System.out.println("Enter the value you would like to push to the stack.");
        Scanner scanner = new Scanner(System.in);
        int val = scanner.nextInt();
        try {
            stub.pushValue(val, clientID);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
        System.out.println("Value: " + val + " pushed.");
    }

    // Performs the pushOperation option. Pushes one operation at a time.
    private void pushOperationOption(Calculator stub, int clientID) {
        // Display menu of operations
        System.out.println("Enter the number of the operation you would like to perform on the values in the stack.");
        System.out.println("1. min");
        System.out.println("2. max");
        System.out.println("3. lcm");
        System.out.println("4. gcd");
        System.out.println("5. exit");

        // Read user input
        Scanner scanner = new Scanner(System.in);
        int operation = 0;
        while (true) {
            operation = scanner.nextInt();
            if (operation > 5 || operation < 1) {
                System.out.println("Invalid option. Please enter a number between 1 and 5.");
                continue;
            } else {
                break;
            }
        }

        // Perform action based on user input
        if (operation == 1) {
            try {
                stub.pushOperation("min", clientID);
            } catch (Exception e) {
                System.err.println("Client exception: " + e.toString());
                e.printStackTrace();
            }
        } else if (operation == 2) {
            try {
                stub.pushOperation("max", clientID);
            } catch (Exception e) {
                System.err.println("Client exception: " + e.toString());
                e.printStackTrace();
            }
        } else if (operation == 3) {
            // Check if stack contains 0. 
            boolean containsZero = false;
            try {
                containsZero = stub.hasZero(clientID);
            } catch (Exception e) {
                System.err.println("Client exception: " + e.toString());
                e.printStackTrace();
            }

            // If yes, clear stack and return error message. Otherwise, push lcm operation.
            if (containsZero) {
                System.out.println("Cannot perform lcm on 0. All values have been removed from stack. Please enter a non-zero value.");
                return;
            } else {
                try {
                    stub.pushOperation("lcm", clientID);
                } catch (Exception e) {
                    System.err.println("Client exception: " + e.toString());
                    e.printStackTrace();
                }
            }
        } else if (operation == 4) {
            try {
                stub.pushOperation("gcd", clientID);
            } catch (Exception e) {
                System.err.println("Client exception: " + e.toString());
                e.printStackTrace();
            }
        } else {
            System.out.println("Back to Main Menu...");
        }
    }

    // Performs the pop option. Pops one value at a time.
    private void popOption(Calculator stub, int clientID) {
        try {
            int result = stub.pop(clientID);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    // Checks if the stack is empty and returns true if it is empty.
    private boolean isEmptyOption(Calculator stub, int clientID) {
        boolean result = true;
        try {
            result = stub.isEmpty(clientID);
            if (result) {
                System.out.println("Stack is empty.");
            } else {
                System.out.println("Stack is not empty.");
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
        return result;
    }

    // Pops one value at a time after a delay.
    private void delayPopOption(Calculator stub, int clientID) {
        System.out.println("Enter the number of milliseconds you would like to delay the pop operation.");
        Scanner scanner = new Scanner(System.in);
        int millis = scanner.nextInt();
        try {
            int result = stub.delayPop(millis, clientID);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        try {
            // Get the registry
            Registry registry = LocateRegistry.getRegistry(host);

            // Look up the remote object
            Calculator stub = (Calculator) registry.lookup("Calculator");

            // Allocate unique client ID to each client
            CalculatorClient client = new CalculatorClient();
            System.out.println("Welcome to the Calculator application!");
            System.out.println("Please create your client ID to begin. Any positive integer is valid.");
            Scanner scanner = new Scanner(System.in);
            int clientID = -1;
            while (true) {
                clientID = scanner.nextInt();
                if (clientID < 0) {
                    System.out.println("Invalid client ID. Please enter a positive integer.");
                    continue;
                } else if (!stub.createNewClientID(clientID)) {
                    System.out.println("Client ID " + clientID + " already exists. Please choose a different client ID.");
                    continue;
                } else if (stub.createNewClientID(clientID)) {
                    System.out.println("Client ID " + clientID + " created.");
                    stub.createNewClientStack(clientID);
                    break;
                } else {
                    continue;
                }
            }

            // Application loop
            while (true) {
                // Display menu
                client.printMenu();

                // Read user input
                scanner = new Scanner(System.in);
                int option = 0;
                while (true) {
                    option = scanner.nextInt();
                    if (option > 6 || option < 1) {
                        System.out.println("Invalid option. Please enter a number between 1 and 6.");
                        continue;
                    } else {
                        break;
                    }
                }

                // Perform action based on user input
                if (option == 1) {
                    client.pushValueOption(stub, clientID);
                } else if (option == 2) {
                    // Check if stack has at least 2 values before pushing operation
                    if (!stub.hasTwoValues(clientID)) {
                        System.out.println("At least two values are required to perform an operation. Please push more values to the stack.");
                        continue;
                    } else {
                        client.pushOperationOption(stub, clientID);
                    }
                } else if (option == 3) {
                    // Check if stack has at least 1 value before popping
                    if (client.isEmptyOption(stub, clientID)) {
                        System.out.println("Stack is empty. Please push a value to the stack before popping.");
                        continue;
                    } else {
                        client.popOption(stub, clientID);
                    }
                } else if (option == 4) {
                    client.isEmptyOption(stub, clientID);
                } else if (option == 5) {
                    // Check if stack has at least 1 value before popping
                    if (client.isEmptyOption(stub, clientID)) {
                        System.out.println("Stack is empty. Please push a value to the stack before popping.");
                        continue;
                    } else {
                        client.delayPopOption(stub, clientID);
                    }
                } else {
                    System.out.println("Exiting...");
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}