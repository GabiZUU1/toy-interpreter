package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.HashMap;

public class MyDictionary<K, V> implements IDictionary<K, V>
{
    private final HashMap<K, V> map = new HashMap<K, V>();

    @Override
    public boolean isDefined(K key)
    {
        return this.map.containsKey(key);
    }

    @Override
    public V lookup(K key) throws ADTException
    {
        if(!this.map.containsKey(key))
        {
            throw new ADTException("map does not contain key " + key);
        }

        return this.map.get(key);
    }

    @Override
    public void put(K key, V value)
    {
        this.map.put(key, value);
    }

    @Override
    public void update(K key, V newValue) throws ADTException
    {
        if(!this.map.containsKey(key))
        {
            throw new ADTException("map does not contain key " + key);
        }

        this.map.replace(key, newValue);
    }

    @Override
    public String toString()
    {
        return this.map.toString();
    }
}
