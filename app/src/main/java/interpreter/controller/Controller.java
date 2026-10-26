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
    private boolean displayFlag;

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
        PrgState prg = repo.getCrtPrg();
        display(prg); // only if the display flag is on
        while(!prg.getExeStack().isEmpty())
        {
            oneStep(prg);
            display(prg);
        }
    }

    private void display(PrgState state)
    {
        if(this.displayFlag)
        {
            println("***************************************");
            println(state);
        }
    }
}
