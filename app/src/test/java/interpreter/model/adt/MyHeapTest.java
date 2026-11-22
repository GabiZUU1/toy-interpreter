package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MyHeapTest
{
    private IHeap heap;

    @BeforeEach
    void setUp()
    {
        this.heap = new MyHeap();
        this.heap.allocate(new IntValue(10));
        this.heap.allocate(new IntValue(20));
        this.heap.allocate(new IntValue(30));
    }

    @Test
    void allocateCorrect()
    {
        assertTrue(this.heap.isDefined(1));
        assertTrue(this.heap.isDefined(2));
        assertTrue(this.heap.isDefined(3));
    }

    @Test
    void getThrows()
    {
        var e = assertThrows(ADTException.class, () -> this.heap.get(11));
        assertEquals("map does not contain key 11", e.getMessage());
    }

    @Test
    void getCorrect() throws ADTException
    {
        assertEquals(new IntValue(10), this.heap.get(1));
        assertEquals(new IntValue(20), this.heap.get(2));
        assertEquals(new IntValue(30), this.heap.get(3));
    }

    @Test
    void updateThrows()
    {
        var e = assertThrows(ADTException.class, () -> this.heap.update(11, new IntValue(20)));
        assertEquals("map does not contain key 11", e.getMessage());
    }

    @Test
    void updateCorrect() throws ADTException
    {
        this.heap.update(1, new IntValue(99));
        assertEquals(new IntValue(99), this.heap.get(1));
    }

    @Test
    void isDefinedCorrect()
    {
        assertTrue(this.heap.isDefined(1));
        assertFalse(this.heap.isDefined(1231));
    }

    @Test
    void getContentCorrect()
    {
        assertInstanceOf(Map.class, this.heap.getContent());
    }

    @Test
    void setContentCorrect() throws ADTException
    {
        Map<Integer, IValue> m = new HashMap<>();
        m.put(1, new IntValue(67));
        this.heap.setContent(m);
        assertEquals(new IntValue(67), this.heap.get(1));
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("{1=10, 2=20, 3=30}", this.heap.toString());
    }
}
