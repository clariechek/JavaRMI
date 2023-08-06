import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.rmi.RemoteException;

public class CalculatorImplementationTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    // Reassign stdout stream to new PrintStream 
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    // Restore stdout stream to original state
    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("Single Client: Test min operation with values {0, 5}")
    void minOperationTest1() throws RemoteException {
        CalculatorImplementation calc = new CalculatorImplementation();
        calc.pushValue(0, 0);
        calc.pushValue(5, 0);
        calc.pushOperation("min", 0);
        int expectedOutput = 0;
        int output = calc.pop(0);
        assertEquals(expectedOutput, output);
    }

    @Test
    @DisplayName("Single Client: Test min operation with values {-4, 0, 5}")
    void minOperationTest2() throws RemoteException {
        CalculatorImplementation calc = new CalculatorImplementation();
        calc.pushValue(-4, 0);
        calc.pushValue(0, 0);
        calc.pushValue(5, 0);
        calc.pushOperation("min", 0);
        int expectedOutput = -4;
        int output = calc.pop(0);
        assertEquals(expectedOutput, output);
    }

    @Test
    @DisplayName("Single Client: Test max operation with values {59, 10003, 130, 2}")
    void maxOperationTest1() throws RemoteException {
        CalculatorImplementation calc = new CalculatorImplementation();
        calc.pushValue(59, 0);
        calc.pushValue(10003, 0);
        calc.pushValue(130, 0);
        calc.pushValue(2, 0);
        calc.pushOperation("max", 0);
        int expectedOutput = 10003;
        int output = calc.pop(0);
        assertEquals(expectedOutput, output);
    }

    @Test
    @DisplayName("Single Client: Test max operation with values {-27, 1, 0, -9}")
    void maxOperationTest2() throws RemoteException {
        CalculatorImplementation calc = new CalculatorImplementation();
        calc.pushValue(-27, 0);
        calc.pushValue(1, 0);
        calc.pushValue(0, 0);
        calc.pushValue(-9, 0);
        calc.pushOperation("max", 0);
        int expectedOutput = 1;
        int output = calc.pop(0);
        assertEquals(expectedOutput, output);
    }

    @Test
    @DisplayName("Single Client: Test gcd operation with values {5, 0}")
    void gcdOperationTest1() throws RemoteException {
        CalculatorImplementation calc = new CalculatorImplementation();
        calc.pushValue(5, 0);
        calc.pushValue(0, 0);
        calc.pushOperation("gcd", 0);
        int expectedOutput = 5;
        int output = calc.pop(0);
        assertEquals(expectedOutput, output);
    }

    @Test
    @DisplayName("Single Client: Test gcd operation with values {3}")
    void gcdOperationTest2() throws RemoteException {
        CalculatorImplementation calc = new CalculatorImplementation();
        calc.pushValue(3, 0);
        calc.pushOperation("gcd", 0);
        String expectedOutput = "At least two values are required to perform an operation. Please push more values to the stack.";
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("Single Client: Test gcd operation with empty stack")
    void gcdOperationTest3() throws RemoteException {
        CalculatorImplementation calc = new CalculatorImplementation();
        calc.pushOperation("gcd", 0);
        String expectedOutput = "At least two values are required to perform an operation. Please push more values to the stack.";
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("Single Client: Test lcm operation with values {12}")
    void lcmOperationTest1() throws RemoteException {
        CalculatorImplementation calc = new CalculatorImplementation();
        calc.pushValue(12, 0);
        calc.pushOperation("lcm", 0);
        String expectedOutput = "At least two values are required to perform an operation. Please push more values to the stack.";
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("Single Client: Test lcm operation with values {1, 5}")
    void lcmOperationTest2() throws RemoteException {
        CalculatorImplementation calc = new CalculatorImplementation();
        calc.pushValue(1, 0);
        calc.pushValue(5, 0);
        calc.pushOperation("lcm", 0);
        int expectedOutput = 5;
        int output = calc.pop(0);
        assertEquals(expectedOutput, output);
    }

    @Test
    @DisplayName("Single Client: Test lcm operation with empty stack")
    void lcmOperationTest3() throws RemoteException {
        CalculatorImplementation calc = new CalculatorImplementation();
        calc.pushOperation("lcm", 0);
        String expectedOutput = "At least two values are required to perform an operation. Please push more values to the stack.";
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("Single Client: Test lcm operation with values {97, 0}")
    void lcmOperationTest4() throws RemoteException {
        CalculatorImplementation calc = new CalculatorImplementation();
        calc.pushValue(97, 0);
        calc.pushValue(0, 0);
        calc.pushOperation("lcm", 0);
        String expectedOutput = "Cannot perform lcm on 0. All values have been removed from stack. Please enter a non-zero value.";
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("Multiple Client: Test min operation with values {3, 5} for client ID 1 and {7, 9} for client ID 2")
    void minOperationTest3() throws RemoteException {
        CalculatorImplementation calc = new CalculatorImplementation();
        calc.pushValue(3, 1);
        calc.pushValue(7, 2);
        calc.pushValue(5, 1);
        calc.pushValue(9, 2);
        calc.pushOperation("min", 1);
        int expectedOutput = 3;
        int output = calc.pop(1);
        assertEquals(expectedOutput, output);
    }

    @Test
    @DisplayName("Multiple Client: Test min operation with values {3, 5} for client ID 1 and {7, 9} for client ID 2")
    void minOperationTest4() throws RemoteException {
        CalculatorImplementation calc = new CalculatorImplementation();
        calc.pushValue(3, 1);
        calc.pushValue(7, 2);
        calc.pushValue(5, 1);
        calc.pushValue(9, 2);
        calc.pushOperation("min", 2);
        int expectedOutput = 7;
        int output = calc.pop(2);
        assertEquals(expectedOutput, output);
    }

    @Test
    @DisplayName("Multiple Client: Test max operation with values {28, 13, 40} for client ID 1 and {-59, -3, -1} for client ID 2")
    void maxOperationTest3() throws RemoteException {
        CalculatorImplementation calc = new CalculatorImplementation();
        calc.pushValue(28, 1);
        calc.pushValue(13, 1);
        calc.pushValue(40, 1);
        calc.pushValue(-59, 2);
        calc.pushValue(-3, 2);
        calc.pushValue(-1, 2);
        calc.pushOperation("max", 1);
        int expectedOutput = 40;
        int output = calc.pop(1);
        assertEquals(expectedOutput, output);
    }

    @Test
    @DisplayName("Multiple Client: Test max operation with values {28, 13, 40} for client ID 1 and {-59, -3, -1} for client ID 2")
    void maxOperationTest4() throws RemoteException {
        CalculatorImplementation calc = new CalculatorImplementation();
        calc.pushValue(28, 1);
        calc.pushValue(13, 1);
        calc.pushValue(40, 1);
        calc.pushValue(-59, 2);
        calc.pushValue(-3, 2);
        calc.pushValue(-1, 2);
        calc.pushOperation("max", 2);
        int expectedOutput = -1;
        int output = calc.pop(2);
        assertEquals(expectedOutput, output);
    }
    
}