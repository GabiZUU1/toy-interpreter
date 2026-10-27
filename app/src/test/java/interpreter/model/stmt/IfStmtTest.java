package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.view.Examples;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IfStmtTest
{   private PrgState state;

    @BeforeEach
    void setUp()
    {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), Examples.ex1);
    }

    @Test
    void executeThrows()
    {
        var e = assertThrows(StatementException.class, () -> new IfStmt(new ValueExp(new IntValue(-12)), new NopStmt(), new NopStmt()).execute(state));
        assertEquals("condition of the if statement is not a boolean", e.getMessage());
    }

    @Test
    void executeCorrect() throws MyException
    {
        state.getSymTable().put("a", new IntValue(0));

        IStmt st = new IfStmt(new ValueExp(new BoolValue(true)),
                              new AssignStmt("a", new ValueExp(new IntValue(1))),
                              new AssignStmt("a", new ValueExp(new IntValue(-1))));
        st.execute(state);
        assertEquals(new AssignStmt("a", new ValueExp(new IntValue(1))),
                state.getExeStack().pop());

        IStmt st2 = new IfStmt(new ValueExp(new BoolValue(false)),
                              new AssignStmt("a", new ValueExp(new IntValue(1))),
                              new AssignStmt("a", new ValueExp(new IntValue(-1))));
        st2.execute(state);
        assertEquals(new AssignStmt("a", new ValueExp(new IntValue(-1))),
                state.getExeStack().pop());
    }

    @Test
    void toStringCorrect()
    {
        IStmt st = new IfStmt(new ValueExp(new BoolValue(true)),
                new AssignStmt("a", new ValueExp(new IntValue(1))),
                new AssignStmt("a", new ValueExp(new IntValue(-1))));

        assertEquals("(if true then a = 1 else a = -1)", st.toString());
    }
}
