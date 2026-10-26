package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyListTest
{
    private IList<IValue> lis;

    private static IValue num(int n)
    {
        return new IntValue(n);
    }

    private static IValue bool(boolean n)
    {
        return new BoolValue(n);
    }

    @BeforeEach
    void setup()
    {
        lis = new MyList<>();
        lis.add(num(10));
        lis.add(bool(false));
        lis.add(num(30));
    }

    @Test
    void addCorrect()
    {
        lis.add(bool(true));
        assertEquals(4, lis.size());
    }

    @Test
    void getThrows()
    {
        var e = assertThrows(ADTException.class, () -> lis.get(-1));
        assertEquals("index -1 is out of bounds", e.getMessage());

        var ee = assertThrows(ADTException.class, () -> lis.get(3));
        assertEquals("index 3 is out of bounds", ee.getMessage());
    }

    @Test
    void getCorrect() throws MyException
    {
        assertEquals(num(30), lis.get(2));
    }

    @Test
    void sizeCorrect()
    {
        assertEquals(3, lis.size());
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("[10, false, 30]", lis.toString());
    }
}
