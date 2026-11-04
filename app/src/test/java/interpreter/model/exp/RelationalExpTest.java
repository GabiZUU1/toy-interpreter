package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.type.BoolType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class RelationalExpTest
{
    private final IDictionary<String, IValue> symTable = new MyDictionary<>();
    private static IExp nr(int nr)
    {
        return new ValueExp(new IntValue(nr));
    }

    @Test
    void firstOperandNotIntThrows()
    {
        IExp e = new RelationalExp(RelOp.EQ, new ValueExp(new BoolValue(true)), nr(1));
        var ex = assertThrows(ExpressionException.class, () -> e.eval(this.symTable));
        assertEquals("first operand is not an integer", ex.getMessage());
    }

    @Test
    void secondOperandNotIntThrows()
    {
        IExp e = new RelationalExp(RelOp.EQ, nr(1), new ValueExp(new BoolValue(true)));
        var ex = assertThrows(ExpressionException.class, () -> e.eval(this.symTable));
        assertEquals("second operand is not an integer", ex.getMessage());
    }

    @Test
    void operationsCorrect() throws MyException
    {
        IValue v1 = new RelationalExp(RelOp.LT, nr(1), nr(5)).eval(symTable);
        assertEquals(new BoolValue(true), v1);

        IValue v2 = new RelationalExp(RelOp.LE, nr(1), nr(1)).eval(symTable);
        assertEquals(new BoolValue(true), v2);

        IValue v3 = new RelationalExp(RelOp.EQ, nr(1), nr(1)).eval(symTable);
        assertEquals(new BoolValue(true), v3);

        IValue v4 = new RelationalExp(RelOp.NE, nr(1), nr(5)).eval(symTable);
        assertEquals(new BoolValue(true), v4);

        IValue v5 = new RelationalExp(RelOp.GT, nr(7), nr(5)).eval(symTable);
        assertEquals(new BoolValue(true), v5);

        IValue v6 = new RelationalExp(RelOp.GE, nr(5), nr(5)).eval(symTable);
        assertEquals(new BoolValue(true), v6);
    }

    @Test
    void operationsIncorrect() throws MyException
    {
        IValue v1 = new RelationalExp(RelOp.LT, nr(9), nr(5)).eval(symTable);
        assertEquals(new BoolValue(false), v1);

        IValue v2 = new RelationalExp(RelOp.LE, nr(2), nr(1)).eval(symTable);
        assertEquals(new BoolValue(false), v2);

        IValue v3 = new RelationalExp(RelOp.EQ, nr(1), nr(0)).eval(symTable);
        assertEquals(new BoolValue(false), v3);

        IValue v4 = new RelationalExp(RelOp.NE, nr(5), nr(5)).eval(symTable);
        assertEquals(new BoolValue(false), v4);

        IValue v5 = new RelationalExp(RelOp.GT, nr(1), nr(5)).eval(symTable);
        assertEquals(new BoolValue(false), v5);

        IValue v6 = new RelationalExp(RelOp.GE, nr(4), nr(5)).eval(symTable);
        assertEquals(new BoolValue(false), v6);
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("(1 == 0)", new RelationalExp(RelOp.EQ, nr(1), nr(0)).toString());
    }
}
