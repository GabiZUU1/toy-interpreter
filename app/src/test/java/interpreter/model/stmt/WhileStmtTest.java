package interpreter.model.stmt;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.*;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WhileStmtTest
{
    private PrgState state;

    @BeforeEach
    void setUp() throws MyException
    {
        this.state = PrgState.of(new NopStmt());
        new VarDeclStmt("v", new IntType()).execute(this.state);
        new AssignStmt("v", new ValueExp(new IntValue(-1))).execute(this.state);
    }

    @Test
    void notBooleanThrows()
    {
        var e = assertThrows(StatementException.class, () -> new WhileStmt(new ValueExp(new IntValue(23)), new NopStmt()).execute(this.state));
        assertEquals("condition of the while statement is not a boolean", e.getMessage());
    }

    @Test
    void whileRuns0Times() throws MyException
    {
        new WhileStmt(new ValueExp(new BoolValue(false)), new AssignStmt("v", new ValueExp(new IntValue(110)))).execute(this.state);
        assertEquals(new IntValue(-1), new VarExp("v").eval(this.state.getSymTable(), this.state.getHeap()));
    }

    @Test
    void whileRuns1Time() throws MyException
    {
        IStmt st = new WhileStmt(new RelationalExp(RelOp.EQ, new VarExp("v"), new ValueExp(new IntValue(-1))),
                                 new AssignStmt("v", new ValueExp(new IntValue(10))));
        st.execute(this.state);

        assertEquals(new AssignStmt("v", new ValueExp(new IntValue(10))), this.state.getExeStack().pop());
    }

    @Test
    void whileRunsSeveralTimes() throws MyException
    {
        IStmt st = new WhileStmt(new RelationalExp(RelOp.LE, new VarExp("v"), new ValueExp(new IntValue(4))),
                                 new AssignStmt("v", new ArithExp(ArithOp.ADD, new VarExp("v"), new ValueExp(new IntValue(1)))));
        st.execute(this.state);

        while(!this.state.getExeStack().isEmpty())
        {
            this.state.getExeStack().pop().execute(this.state);
        }

        assertEquals(new IntValue(5), new VarExp("v").eval(this.state.getSymTable(), this.state.getHeap()));
    }

    @Test
    void toStringCorrect()
    {
        IStmt st = new WhileStmt(new ValueExp(new BoolValue(true)), new AssignStmt("v", new ArithExp(ArithOp.ADD, new VarExp("v"), new ValueExp(new IntValue(1)))));
        assertEquals("(while (true) v = (v + 1))", st.toString());
    }
}
