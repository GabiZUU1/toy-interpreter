package interpreter.model.type;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

public class IntTypeTest
{
    @Test
    void defaultValueCorrect()
    {
        assertEquals(new IntValue(0), new IntType().defaultValue());
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("int", new IntType().toString());
    }
}
