package interpreter.model.type;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

public class RefTypeTest
{
    @Test
    void defaultValueCorrect()
    {
        assertEquals(new RefValue(0, new IntType()), new RefType(new IntType()).defaultValue());
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("Ref(int)", new RefType(new IntType()).toString());
    }

    @Test
    void nestedEqualsCorrect()
    {
        var a = new RefType(new RefType(new RefType(new IntType())));
        assertNotEquals(new RefType(new RefType(new IntType())), a);
    }
}
