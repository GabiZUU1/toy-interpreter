package interpreter.model;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;

public class PrgStateTest
{
    private IStmt prg;
    private PrgState state;

    @BeforeEach
    void setUp()
    {
        this.prg = new NopStmt();
        this.state = PrgState.of(this.prg);
    }

    @Test
    void constructorCorrect() throws MyException
    {
        // is the initial program the only program in the execution stack
        assertEquals(this.prg, this.state.getExeStack().pop());

        // is the execution stack empty after popping an item out of it
        assertTrue(this.state.getExeStack().isEmpty());

        // are symTable, out and fileTable empty
        assertEquals("{}", this.state.getSymTable().toString());
        assertEquals(0, this.state.getOut().size());
        assertEquals("{}", this.state.getFileTable().toString());
    }

    @Test
    void ofCorrect()
    {
        PrgState s = PrgState.of(new NopStmt());
        assertInstanceOf(PrgState.class, s);
    }

    @Test
    void setExeStackCorrect()
    {
        MyStack<IStmt> newStack = new MyStack<>();
        this.state.setExeStack(newStack);

        assertSame(newStack, this.state.getExeStack());
    }

    @Test
    void setSymTableCorrect()
    {
        MyDictionary<String, IValue> newDict = new MyDictionary<>();
        this.state.setSymTable(newDict);

        assertSame(newDict, this.state.getSymTable());
    }

    @Test
    void setOutCorrect()
    {
        MyList<IValue> newList = new MyList<>();
        this.state.setOut(newList);

        assertSame(newList, this.state.getOut());
    }

    @Test
    void setFileTableCorrect()
    {
        MyDictionary<StringValue, BufferedReader> newFileTable = new MyDictionary<>();
        this.state.setFileTable(newFileTable);

        assertSame(newFileTable, this.state.getFileTable());
    }

    @Test
    void getOriginalProgramCorrect()
    {
        assertSame(this.prg, this.state.getOriginalProgram());
    }

    @Test
    void toStringCorrect()
    {
        String c = "[nop] | {} | []";
        assertEquals(c, this.state.toString());
    }
}
