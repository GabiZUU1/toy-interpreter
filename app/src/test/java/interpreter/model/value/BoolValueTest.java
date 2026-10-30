package interpreter.model.value;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.model.type.BoolType;
import org.junit.jupiter.api.Test;

public class BoolValueTest
{
    @Test
    void getTypeCorrect()
    {
        assertEquals(new BoolType(), new BoolValue(true).getType());
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("false", new BoolValue(false).toString());
    }
}
