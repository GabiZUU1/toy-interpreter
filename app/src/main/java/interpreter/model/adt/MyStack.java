package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class MyStack<T> implements IStack<T>
{
    private final Deque<T> stack = new ArrayDeque<>();

    @Override
    public void push(T v)
    {
        stack.push(v);
    }

    @Override
    public T pop() throws MyException
    {
        if(stack.isEmpty())
        {
            throw new ADTException("pop from an empty stack");
        }
        return stack.pop();
    }

    @Override
    public boolean isEmpty()
    {
        return stack.isEmpty();
    }

    @Override
    public List<T> toList()
    {
        return new ArrayList<>(this.stack);
    }

    @Override
    public String toString()
    {
        return stack.toString();
    }
}