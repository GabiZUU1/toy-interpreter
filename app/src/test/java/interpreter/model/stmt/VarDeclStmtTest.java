package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.view.Examples;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VarDeclStmtTest
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
        var e = assertThrows(StatementException.class, () -> new VarDeclStmt("a", new IntType()).execute(state));
        assertEquals("variable a is already declared", e.getMessage());
    }

    @Test
    void executeCorrect() throws MyException
    {
        new VarDeclStmt("abc", new BoolType()).execute(state);

        assertEquals(new BoolValue(false), state.getSymTable().lookup("abc"));
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("int a", new VarDeclStmt("a", new IntType()).toString());
    }
}
