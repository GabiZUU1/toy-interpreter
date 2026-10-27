package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.view.Examples;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AssignStmtTest
{
    private PrgState state;

    @BeforeEach
    void setUp()
    {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), Examples.ex1);

        state.getSymTable().put("a", new IntValue(10));
    }

    @Test
    void executeThrows()
    {
        var e = assertThrows(StatementException.class, () -> new AssignStmt("ok", new VarExp("ok")).execute(state));
        assertEquals("variable ok was not declared", e.getMessage());

        var ee = assertThrows(StatementException.class, () -> new AssignStmt("a", new ValueExp(new BoolValue(false))).execute(state));
        assertEquals("declared type of variable a and type of the assigned expression do not match", ee.getMessage());
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("a = 22", new AssignStmt("a", new ValueExp(new IntValue(22))).toString());
    }
}
