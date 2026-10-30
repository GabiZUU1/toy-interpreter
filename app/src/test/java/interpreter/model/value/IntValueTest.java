package interpreter.model.value;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.model.type.IntType;
import org.junit.jupiter.api.Test;

public class IntValueTest
{
    @Test
    void getTypeCorrect()
    {
        assertEquals(new IntType(), new IntValue(0).getType());
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("123", new IntValue(123).toString());
    }
}
