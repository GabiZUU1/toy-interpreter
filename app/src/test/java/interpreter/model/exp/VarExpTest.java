package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class VarExpTest
{
    private final IDictionary<String, IValue> symTable = new MyDictionary<>();

    @Test
    void notDefinedThrows()
    {
        var e = assertThrows(ExpressionException.class, () -> new VarExp("a").eval(symTable));
        assertEquals("variable a is not defined", e.getMessage());
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("a", new VarExp("a").toString());
    }
}
