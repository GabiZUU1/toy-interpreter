package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LogicExpTest
{
    private final IDictionary<String, IValue> symTable = new MyDictionary<>();
    private static IExp bool(boolean b)
    {
        return new ValueExp(new BoolValue(b));
    }

    @Test
    void firstOperandNotBooleanThrows()
    {
        IExp e = new LogicExp(LogicOp.AND, new ValueExp(new IntValue(1)), bool(false));
        var ex = assertThrows(ExpressionException.class, () -> e.eval(symTable));
        assertEquals("first operand is not a boolean", ex.getMessage());
    }

    @Test
    void secondOperandNotBooleanThrows()
    {
        IExp e = new LogicExp(LogicOp.OR, bool(false), new ValueExp(new IntValue(1)));
        var ex = assertThrows(ExpressionException.class, () -> e.eval(symTable));
        assertEquals("second operand is not a boolean", ex.getMessage());
    }

    @Test
    void testAndCorrect() throws MyException
    {
        IExp e1 = new LogicExp(LogicOp.AND, bool(true), bool(true));
        IExp e2 = new LogicExp(LogicOp.AND, bool(false), bool(true));

        assertEquals(new BoolValue(true), e1.eval(symTable));
        assertEquals(new BoolValue(false), e2.eval(symTable));
    }

    @Test
    void testOrCorrect() throws MyException
    {
        IExp e1 = new LogicExp(LogicOp.OR, bool(true), bool(false));
        IExp e2 = new LogicExp(LogicOp.OR, bool(false), bool(false));
        IExp e3 = new LogicExp(LogicOp.OR, bool(true), bool(true));

        assertEquals(new BoolValue(true), e1.eval(symTable));
        assertEquals(new BoolValue(false), e2.eval(symTable));
        assertEquals(new BoolValue(true), e3.eval(symTable));
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("(true and false)", new LogicExp(LogicOp.AND, bool(true), bool(false)).toString());
    }
}
