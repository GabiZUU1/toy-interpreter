package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IntValue;
import interpreter.view.Examples;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrintStmtTest
{
    private PrgState state;

    @BeforeEach
    void setUp()
    {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), Examples.ex1);
    }

    @Test
    void executeCorrect() throws MyException
    {
        new PrintStmt(new ValueExp(new IntValue(10))).execute(state);

        assertEquals(1, state.getOut().size());
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("print(1)", new PrintStmt(new ValueExp(new IntValue(1))).toString());
    }
}
