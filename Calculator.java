import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.*;
import java.util.*;

// Remote interface for the Calculator application
public interface Calculator extends Remote {
    void pushValue(int val) throws RemoteException;
    void pushOperation(String operator) throws RemoteException;
    int pop() throws RemoteException;
    boolean isEmpty() throws RemoteException;
    int delayPop(int millis) throws RemoteException;
    // int lcm(int a, int b) throws RemoteException;
    // int gcd(int a, int b) throws RemoteException;
}