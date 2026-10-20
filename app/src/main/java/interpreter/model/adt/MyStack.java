package interpreter.model.adt;

import java.util.ArrayDeque;
import java.util.Deque;

public class MyStack<T> implements IStack<T>
{
    private final Deque<T> stack = new ArrayDeque<>();

    @Override
    public void push(T v)
    {
        stack.push(v);
    }

    @Override
    public T pop() throws ADTException
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
    public String toString()
    {
        return stack.toString();
    }
}

// TODO: IDictionary<K, V> / MyDictionary<K, V>: isDefined, lookup, put, update
// (wraps a HashMap)
// IList<T> / MyList<T>: add, get, size (wraps an ArrayList)