package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;

import static java.lang.IO.println;

public class Controller
{
    IRepository repo;
    private final boolean displayFlag;

    public Controller(IRepository repo, boolean displayFlag)
    {
        this.repo = repo;
        this.displayFlag = displayFlag;
    }

    public PrgState oneStep(PrgState state) throws MyException
    {
        var stack = state.getExeStack();
        if(stack.isEmpty())
        {
            throw new StatementException("program state stack is empty");
        }

        IStmt crtStmt = stack.pop();
        return crtStmt.execute(state);
    }

    public void allStep() throws MyException
    {
        PrgState prg = this.repo.getCrtPrg();
        this.repo.logPrgStateExec();
        display(prg);
        while(!prg.getExeStack().isEmpty())
        {
            oneStep(prg);
            this.repo.logPrgStateExec();
            display(prg);
        }
    }

    private void display(PrgState state)
    {
        if(this.displayFlag)
        {
            println();
            //println("*".repeat(state.toString().length()));
            println(state);
        }
    }
}
