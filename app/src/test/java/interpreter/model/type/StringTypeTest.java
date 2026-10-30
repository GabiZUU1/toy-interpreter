package interpreter.model.type;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

public class StringTypeTest
{
    @Test
    void defaultValueCorrect()
    {
        assertEquals(new StringValue(""), new StringType().defaultValue());
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("string", new StringType().toString());
    }
}
