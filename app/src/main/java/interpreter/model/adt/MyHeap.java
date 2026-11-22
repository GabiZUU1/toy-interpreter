package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IValue;

import java.util.HashMap;
import java.util.Map;

public class MyHeap implements IHeap
{
    private Map<Integer, IValue> heap = new HashMap<>();
    private int nextFree = 1;

    @Override
    public int allocate(IValue value)
    {
        int address = this.nextFree++;
        this.heap.put(address, value);
        return address;
    }

    @Override
    public IValue get(int address) throws ADTException
    {
        if(!this.heap.containsKey(address))
        {
            throw new ADTException("map does not contain key " + address);
        }

        return this.heap.get(address);
    }

    @Override
    public void update(int address, IValue value) throws ADTException
    {
        if(!this.heap.containsKey(address))
        {
            throw new ADTException("map does not contain key " + address);
        }

        this.heap.replace(address, value);
    }

    @Override
    public boolean isDefined(int address)
    {
        return this.heap.containsKey(address);
    }

    @Override
    public Map<Integer, IValue> getContent()
    {
        return Map.copyOf(this.heap);
    }

    @Override
    public void setContent(Map<Integer, IValue> newContent)
    {
        this.heap = newContent;
    }

    @Override
    public String toString()
    {
        return this.heap.toString();
    }
}
