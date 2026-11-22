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

public class NewStmtTest
{
    private PrgState state;

    @BeforeEach
    void setUp()
    {
        this.state = PrgState.of(Examples.ex1());
    }

    @Test
    void executeCorrect() throws MyException
    {
        new VarDeclStmt("a", new RefType(new IntType())).execute(this.state);
        new NewStmt("a", new ValueExp(new IntValue(10))).execute(this.state);

        assertEquals(new IntValue(10), this.state.getHeap().get(1));
    }

    @Test
    void varNotDeclThrows()
    {
        var e = assertThrows(StatementException.class, () -> new NewStmt("a", new ValueExp(new IntValue(0))).execute(this.state));
        assertEquals("new: a is not declared", e.getMessage());
    }

    @Test
    void varNotRefThrows() throws MyException
    {
        new VarDeclStmt("a", new IntType()).execute(this.state);
        var e = assertThrows(StatementException.class, () -> new NewStmt("a", new ValueExp(new IntValue(10))).execute(this.state));
        assertEquals("new: a is not a reference", e.getMessage());
    }

    @Test
    void varWrongTypeThrows() throws MyException
    {
        new VarDeclStmt("a", new RefType(new IntType())).execute(this.state);
        var e = assertThrows(StatementException.class, () -> new NewStmt("a", new ValueExp(new StringValue("asd"))).execute(this.state));
        assertEquals("new: asd has type string, expected int", e.getMessage());
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("new(a, 1)", new NewStmt("a", new ValueExp(new IntValue(1))).toString());
    }
}
