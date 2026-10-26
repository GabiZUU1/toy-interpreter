package interpreter.model.adt;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyStackTest
{
    private IStack<Integer> stk;

    @BeforeEach
    void setUp()
    {
        stk = new MyStack<>();
        stk.push(10);
        stk.push(99);
        stk.push(-13);
    }

    @Test
    void pushCorrect() throws MyException
    {
        stk.push(123);
        assertEquals(123, stk.pop());
    }

    @Test
    void popThrows()
    {
        IStack<Integer> s = new MyStack<>();
        var e = assertThrows(ADTException.class, () -> s.pop());
        assertEquals("pop from an empty stack", e.getMessage());
    }

    @Test
    void isEmptyCorrect()
    {
        IStack<Integer> s = new MyStack<>();
        assertTrue(s.isEmpty());

        assertFalse(stk.isEmpty());
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("[-13, 99, 10]", stk.toString());
    }
}
