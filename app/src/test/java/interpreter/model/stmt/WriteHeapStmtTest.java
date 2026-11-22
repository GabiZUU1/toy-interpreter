package interpreter.model.stmt;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import interpreter.view.Examples;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WriteHeapStmtTest
{
    private PrgState state;

    @BeforeEach
    void setUp() throws MyException
    {
        this.state = PrgState.of(Examples.ex1());
        new VarDeclStmt("a", new IntType()).execute(this.state);
        new VarDeclStmt("b", new RefType(new IntType())).execute(this.state);
        new VarDeclStmt("c", new RefType(new IntType())).execute(this.state);
        new NewStmt("c", new ValueExp(new IntValue(10))).execute(this.state);
    }

    @Test
    void executeCorrect() throws MyException
    {
        new WriteHeapStmt("c", new ValueExp(new IntValue(99))).execute(this.state);
        assertEquals(new IntValue(99), this.state.getHeap().get(1));
    }

    @Test
    void varNotDefinedThrows()
    {
        var e = assertThrows(StatementException.class, () -> new WriteHeapStmt("nu", new ValueExp(new IntValue(10))).execute(this.state));
        assertEquals("wH: nu is not defined", e.getMessage());
    }

    @Test
    void varNotReferenceThrows()
    {
        var e = assertThrows(StatementException.class, () -> new WriteHeapStmt("a", new ValueExp(new IntValue(10))).execute(this.state));
        assertEquals("wH: a is not a reference", e.getMessage());
    }

    @Test
    void addressNotInHeapThrows()
    {
        var e = assertThrows(StatementException.class, () -> new WriteHeapStmt("b", new ValueExp(new IntValue(10))).execute(this.state));
        assertEquals("wH: address 0 is not in the heap", e.getMessage());
    }

    @Test
    void wrongTypeThrows()
    {
        var e = assertThrows(StatementException.class, () -> new WriteHeapStmt("c", new ValueExp(new StringValue("asd"))).execute(this.state));
        assertEquals("wH: asd has type string, expected int", e.getMessage());
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("wH(a, 1)", new WriteHeapStmt("a", new ValueExp(new IntValue(1))).toString());
    }
}
