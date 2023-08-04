# JavaRMI
A calculator server (Java RMI) application to understand how distributed systems work.

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
1. To start Java RMI Registry, run the instruction below (for Solaris(tm) Operating System)
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

3. Finally, run the client using this command:
```
java  -classpath ./ CalculatorClient
```
