package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest
{
    private static PrgState run(IStmt program) throws MyException
    {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(),
                new MyList<>(), program);
        new Controller(new Repository(state), false).allStep(); // display off
        return state;
    }

    @Test
    void example1Prints2() throws MyException
    {
        PrgState st = run(Examples.ex1);

        assertTrue(st.getExeStack().isEmpty());
        assertEquals(new IntValue(2), st.getOut().get(0));
    }

    @Test
    void example2Prints18() throws MyException
    {
        PrgState st = run(Examples.ex2);

        assertTrue(st.getExeStack().isEmpty());
        assertEquals(new IntValue(18), st.getOut().get(0));
    }

    @Test
    void example3Prints2() throws MyException
    {
        PrgState st = run(Examples.ex3);

        assertTrue(st.getExeStack().isEmpty());
        assertEquals(new IntValue(2), st.getOut().get(0));
    }

    @Test
    void oneStepOnEmptyStackThrows() throws MyException
    {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(),
                new MyList<>(), new NopStmt());
        Controller controller = new Controller(new Repository(state), false);
        state.getExeStack().pop();

        var ex = assertThrows(StatementException.class, () -> controller.oneStep(state));

        assertEquals("program state stack is empty", ex.getMessage());
    }
}
