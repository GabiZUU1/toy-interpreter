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

    public String toLogString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ExeStack:\n");
        this.exeStack.toList().forEach(s -> sb.append(s).append("\n"));
        sb.append("SymTable:\n");
        this.symTable.toMap().forEach((k, v) -> sb.append(k).append(" --> ").append(v).append("\n"));
        sb.append("Out:\n");
        this.out.toList().forEach(v -> sb.append(v).append("\n"));
        sb.append("FileTable:\n");
        this.fileTable.toMap().keySet().forEach(k -> sb.append(k.val()).append("\n"));

        return sb.toString();
    }
}