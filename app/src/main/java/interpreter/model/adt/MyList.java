package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;

import java.util.ArrayList;

public class MyList<T> implements IList<T>
{
    private final ArrayList<T> list = new ArrayList<T>();

    @Override
    public void add(T item)
    {
        this.list.add(item);
    }

    @Override
    public T get(int index) throws MyException
    {
        if(index < 0 || index >= this.list.size())
        {
            throw new ADTException("index " + index + " is out of bounds");
        }

        return this.list.get(index);
    }

    @Override
    public int size()
    {
        return this.list.size();
    }

    @Override
    public String toString()
    {
        return this.list.toString();
    }
}
