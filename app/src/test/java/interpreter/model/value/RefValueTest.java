package interpreter.model.value;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import org.junit.jupiter.api.Test;

public class RefValueTest
{
    @Test
    void getTypeCorrect()
    {
        assertEquals(new RefType(new IntType()), new RefValue(1, new IntType()).getType());
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("(1, int)", new RefValue(1, new IntType()).toString());
    }
}
