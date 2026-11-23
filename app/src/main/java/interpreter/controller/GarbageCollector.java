package interpreter.controller;

import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

import java.util.*;
import java.util.stream.Collectors;

public final class GarbageCollector
{
    private GarbageCollector()
    {

    }

    public static Set<Integer> getAddrFromSymTable(Collection<IValue> symTableValues)
    {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).address())
                .collect(Collectors.toSet());
    }

    public static Map<Integer, IValue> unsafeGarbageCollector(Set<Integer> symTableAddr, Map<Integer, IValue> heap)
    {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<Integer, IValue> safeGarbageCollector(Set<Integer> symTableAddr, Map<Integer, IValue> heap)
    {
        Set<Integer> reachable = reachableAddress(symTableAddr, heap);

        return heap.entrySet().stream()
                .filter(e -> reachable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Set<Integer> reachableAddress(Set<Integer> roots, Map<Integer, IValue> heap)
    {
        Set<Integer> reachable = new HashSet<>(roots);
        boolean changed = true;
        while(changed)
        {
            List<IValue> values = heap.entrySet().stream()
                    .filter(e -> reachable.contains(e.getKey()))
                    .map(e -> e.getValue())
                    .toList();

            Set<Integer> found = getAddrFromSymTable(values);
            changed = reachable.addAll(found);
        }

        return reachable;
    }
}








