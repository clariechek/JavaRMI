# JavaRMI
A calculator application that uses Java RMI to implement server and client sides, and JUnit Jupiter framework for automated testing.
The server contains a map that links each client's ID to their respective stack.
The methods include:
1. `void pushValue(int val, int clientID);`: adds integer `val` to the client's (with `clientID`) stack.
2. `void pushOperation(String operator, int clientID);`: computes the desired operation `operator` on the client's stack values. 
  - This will remove all values on the client stack and push the result of the operation onto the stack. 
  - The operations are `min`, `max`, `lcm`, and `gcd`. 
  - To perform an operation, the client stack must have at least 2 values. Otherwise an error message is returned.
  - Operators `lcm` and `gcd` assume all values as absolute values.
  - Operator `lcm` does not work for value 0.
3. `int pop(int clientID);`: Returns the top-most value on the client's stack.
4. `boolean isEmpty(int clientID);`: Returns true if the client stack is empty.
5. `int delayPop(int millis, int clientID);`: Waits `millis` milliseconds before returning the top-most value on the client's stack.

## Compilation
1. In the project folder run the instruction below. This will compile the files and create the corresponding `.class` files in the `bin` folder.
```
make
```

2. Navigate to the `bin` folder using the below command. This is where we will start the Java RMI Registry, start the server, and run the client.
```
cd bin
```

## Run the Application
1. In the `bin` directory, run the instruction below to start Java RMI Registry (for Solaris(tm) Operating System)
```
rmiregistry &
```
or if you're using Windows, run this:
```
start rmiregistry 
```
The default port the registry runs on is 1099.

2. Next, start the server using the following command (for Solaris(tm) Operating System)
```
java -classpath ./ CalculatorServer &
```
or for Windows, run this:
```
start java -classpath ./ CalculatorServer
```
The output should be the following:
```
Server ready
```

3. Finally, run the client using the command below. To run multiple clients parallelly, run the command below in each terminal for each client. 
```
java  -classpath ./ CalculatorClient
```

## Testing
### Testing Requirements
1. The test script is located in the `tests` folder. To run the tests in Visual Studio Code, you will need:
- JDK (version 1.8 or later)
- Visual Studio Code (version 1.59.0 or later)
- [Visual Studio Code Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)

2. Follow the 'Get Started with Java Development' instructions on Visual Studio Code by clicking the `Install JDK` button (on the left of the screenshot below) and blue `Download` button (on the right of the screenshot below).
<img width="1137" alt="Screenshot 2023-08-07 at 2 58 49 pm" src="https://github.com/clariechek/JavaRMI/assets/44283405/37a2d5c7-5e6b-4ee9-9e73-355de496eea7">

3. Open the project folder and select the test script `CalculatorImplementationTest.java`.

4. Click on the testing button on the left sidebar.
![Uploading Screenshot 2023-08-07 at 3.04.17 pm.png…]()

5. Select Enable Java Tests
<img width="275" alt="Screenshot 2023-08-07 at 3 05 17 pm" src="https://github.com/clariechek/JavaRMI/assets/44283405/492fd8da-0ba4-4b94-b5a4-4a81d1df0821">

6. In the dropdown menu, select JUnit Jupiter.
<img width="275" alt="Screenshot 2023-08-07 at 3 05 47 pm" src="https://github.com/clariechek/JavaRMI/assets/44283405/ced2905b-79b0-45cf-a85e-e9506c0ff9f9">

7. To run the tests, select the play button next to the JavaRMI title as shown below. Alternatively, you can run each test individually by clicking the play button next to each test title.
https://github.com/clariechek/JavaRMI/assets/44283405/0ff80949-5b6b-4d0e-a4d6-f8239762fa63



For more information: https://code.visualstudio.com/docs/java/java-testing
