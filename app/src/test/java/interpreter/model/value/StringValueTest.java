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
}
