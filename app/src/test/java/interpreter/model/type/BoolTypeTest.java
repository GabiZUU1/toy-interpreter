package interpreter.model.type;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.model.value.BoolValue;
import org.junit.jupiter.api.Test;

public class BoolTypeTest
{
    @Test
    void defaultValueCorrect()
    {
        assertEquals(new BoolValue(false), new BoolType().defaultValue());
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("bool", new BoolType().toString());
    }
}
