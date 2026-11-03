package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import interpreter.view.Examples;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReadFileStmtTest
{
    private PrgState state;
    private final String file = "../test.in";

    @BeforeEach
    void setUp()
    {
        this.state = PrgState.of(Examples.ex1);
    }

    @Test
    void expNotStringThrows()
    {
        var ex = assertThrows(StatementException.class, () -> new ReadFileStmt(new ValueExp(new IntValue(1)), "okay").execute(this.state));
        assertEquals("readFile: 1 is not a string", ex.getMessage());
    }

    @Test
    void fileNotOpenThrows()
    {
        var ex = assertThrows(StatementException.class, () -> new ReadFileStmt(new ValueExp(new StringValue("ok")), "a").execute(this.state));
        assertEquals("readFile: ok is not open", ex.getMessage());
    }

    @Test
    void varNotDefinedThrows() throws MyException
    {
        new OpenRFileStmt(new ValueExp(new StringValue(this.file))).execute(this.state);

        var ex = assertThrows(StatementException.class, () -> new ReadFileStmt(new ValueExp(new StringValue(this.file)), "ok").execute(this.state));
        assertEquals("readFile: ok is not defined", ex.getMessage());
    }

    @Test
    void varNotIntThrows() throws MyException
    {
        new OpenRFileStmt(new ValueExp(new StringValue(this.file))).execute(this.state);
        this.state.getSymTable().put("a", new StringValue("aa"));

        var ex = assertThrows(StatementException.class, () -> new ReadFileStmt(new ValueExp(new StringValue(this.file)), "a").execute(this.state));
        assertEquals("readFile: a is not of type int", ex.getMessage());
    }

    @Test
    void cantReadTest() throws MyException
    {
        new VarDeclStmt("a", new IntType()).execute(this.state);
        new OpenRFileStmt(new ValueExp(new StringValue("../testException.in"))).execute(this.state);

        var ex = assertThrows(StatementException.class, () -> new ReadFileStmt(new ValueExp(new StringValue("../testException.in")), "a").execute(this.state));
        assertEquals("readFile: cannot read ../testException.in: For input string: \"asd\"", ex.getMessage());
    }

    @Test
    void adds0Value() throws MyException
    {
        new VarDeclStmt("a", new IntType()).execute(this.state);
        new AssignStmt("a", new ValueExp(new IntValue(123))).execute(this.state);
        new OpenRFileStmt(new ValueExp(new StringValue("../testEmpty.in"))).execute(this.state);
        new ReadFileStmt(new ValueExp(new StringValue("../testEmpty.in")), "a").execute(this.state);

        assertEquals(new IntValue(0), this.state.getSymTable().lookup("a"));
    }

    @Test
    void addsAValue() throws MyException
    {
        new VarDeclStmt("a", new IntType()).execute(this.state);
        new AssignStmt("a", new ValueExp(new IntValue(123))).execute(this.state);
        new OpenRFileStmt(new ValueExp(new StringValue("../test.in"))).execute(this.state);
        new ReadFileStmt(new ValueExp(new StringValue("../test.in")), "a").execute(this.state);

        assertEquals(new IntValue(15), this.state.getSymTable().lookup("a"));
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("readFile(../test.in)", new ReadFileStmt(new ValueExp(new StringValue("../test.in")), "a").toString());
    }
}
