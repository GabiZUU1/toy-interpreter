package interpreter.model.value;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.model.type.StringType;
import org.junit.jupiter.api.Test;

public class StringValueTest
{
    @Test
    void getTypeCorrect()
    {
        assertEquals(new StringType(), new StringValue("").getType());
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("buna", new StringValue("buna").toString());
    }

    @Test
    void stringsAreEqual()
    {
        var a = new StringValue("buna");
        var b = new StringValue("buna");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
