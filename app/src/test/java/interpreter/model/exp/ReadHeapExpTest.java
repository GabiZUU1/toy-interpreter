package interpreter.model.exp;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.IHeap;
import interpreter.model.adt.MyHeap;
import interpreter.model.stmt.NewStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IntValue;
import interpreter.view.Examples;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReadHeapExpTest
{
    private PrgState state;

    @BeforeEach
    void setUp() throws MyException
    {
        this.state = PrgState.of(Examples.ex1());
        new VarDeclStmt("a", new RefType(new IntType())).execute(this.state);
        new VarDeclStmt("b", new IntType()).execute(this.state);
        new VarDeclStmt("c", new RefType(new IntType())).execute(state);
        new NewStmt("a", new ValueExp(new IntValue(10))).execute(this.state);
    }

    @Test
    void evalCorrect() throws MyException
    {
        assertEquals(new IntValue(10), new ReadHeapExp(new VarExp("a")).eval(this.state.getSymTable(), this.state.getHeap()));
    }

    @Test
    void notReferenceThrows()
    {
        var e = assertThrows(ExpressionException.class, () -> new ReadHeapExp(new VarExp("b")).eval(this.state.getSymTable(), this.state.getHeap()));
        assertEquals("rH: b is not a reference", e.getMessage());
    }

    @Test
    void addressNotInHeapThrows()
    {
        var e = assertThrows(ExpressionException.class, () -> new ReadHeapExp(new VarExp("c")).eval(this.state.getSymTable(), this.state.getHeap()));
        assertEquals("rH: address 0 is not in the heap", e.getMessage());
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("rH(a)", new ReadHeapExp(new VarExp("a")).toString());
    }
}
