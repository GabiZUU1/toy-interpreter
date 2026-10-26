package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyDictionaryTest
{
    private IDictionary<String, IValue> dict;

    @BeforeEach
    void setUp()
    {
        dict = new MyDictionary<>();
        this.dict.put("nr", new IntValue(120));
        this.dict.put("tra", new BoolValue(false));
    }

    @Test
    void isDefinedCorrect()
    {
        assertFalse(this.dict.isDefined("ok"));
        assertTrue(this.dict.isDefined("nr"));
    }

    @Test
    void lookupThrows()
    {
        var e = assertThrows(ADTException.class, () -> this.dict.lookup("ok"));
        assertEquals("map does not contain key ok", e.getMessage());
    }

    @Test
    void lookupCorrect() throws MyException
    {
        assertEquals(new IntValue(120), this.dict.lookup("nr"));
        assertEquals(new BoolValue(false), this.dict.lookup("tra"));
    }

    @Test
    void putCorrect() throws MyException
    {
        this.dict.put("buna", new IntValue(30));
        assertTrue(this.dict.isDefined("buna"));
        assertEquals(new IntValue(30), this.dict.lookup("buna"));
    }

    @Test
    void updateThrows()
    {
        var e = assertThrows(ADTException.class, () -> this.dict.update("asdaw", new IntValue(11)));
        assertEquals("map does not contain key asdaw", e.getMessage());
    }

    @Test
    void updateCorrect() throws MyException
    {
        this.dict.update("nr", new IntValue(99));
        assertEquals(new IntValue(99), this.dict.lookup("nr"));
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("{tra=false, nr=120}", this.dict.toString());
    }
}
