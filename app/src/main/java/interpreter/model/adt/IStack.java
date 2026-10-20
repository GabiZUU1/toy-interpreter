package interpreter.model.adt;

public interface IStack<T>
{
    void push(T v);
    T pop() throws ADTException;
    boolean isEmpty();
}