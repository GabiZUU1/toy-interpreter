package interpreter.model.adt;


import interpreter.exceptions.MyException;

import java.util.List;

public interface IList<T>
{
    void add(T item);
    T get(int index) throws MyException;
    int size();
    List<T> toList();
}
