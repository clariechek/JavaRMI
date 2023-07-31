import java.rmi.*;

// Remote interface for the Calculator application
public interface Calculator extends Remote {
    void pushValue(int val, Stack<Integer> stack) throws RemoteException;
    void pushOperation(String operator, Stack<Integer> stack) throws RemoteException;
    int pop(Stack<Integer> stack) throws RemoteException;
    boolean isEmpty(Stack<Integer> stack) throws RemoteException;
    int delayPop(int millis, Stack<Integer> stack) throws RemoteException;
}
```