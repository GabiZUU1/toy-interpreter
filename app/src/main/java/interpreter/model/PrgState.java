package interpreter.model;

import interpreter.model.adt.*;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;

public class PrgState
{
    private IStack<IStmt> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private IDictionary<StringValue, BufferedReader> fileTable;
    private final IStmt originalProgram;

    public PrgState(IStack<IStmt> exeStack, IDictionary<String, IValue> symTable, IList<IValue> out, IDictionary<StringValue, BufferedReader> fileTable, IStmt program)
    {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = program;

        exeStack.push(program);
    }

    public static PrgState of(IStmt program)
    {
        return new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), program);
    }

    public IStack<IStmt> getExeStack()
    {
        return this.exeStack;
    }

    public void setExeStack(IStack<IStmt> newExeStack)
    {
        this.exeStack = newExeStack;
    }

    public IDictionary<String, IValue> getSymTable()
    {
        return this.symTable;
    }

    public void setSymTable(IDictionary<String, IValue> newSymTable)
    {
        this.symTable = newSymTable;
    }

    public IList<IValue> getOut()
    {
        return this.out;
    }

    public void setOut(IList<IValue> newOut)
    {
        this.out = newOut;
    }

    public IDictionary<StringValue, BufferedReader> getFileTable()
    {
        return this.fileTable;
    }

    public void setFileTable(IDictionary<StringValue, BufferedReader> newFileTable)
    {
        this.fileTable = newFileTable;
    }

    public IStmt getOriginalProgram()
    {
        return this.originalProgram;
    }

    @Override
    public String toString()
    {
        return exeStack + " | " + symTable + " | " + out;
    }
}

// PrgState state = new PrgState(MyStack<>(), new MyDictionary<>(), new MyList<>(), program);