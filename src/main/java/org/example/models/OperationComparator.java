package org.example.models;

import java.util.Comparator;
import java.util.Map;

public class OperationComparator implements Comparator<Operation> {
    private final Map<String, Integer> partPriorities;

    public OperationComparator(Map<String, Integer> partPriorities) {
        this.partPriorities = partPriorities;
    }

    @Override
    public int compare(Operation o1, Operation o2) {
        int priorityCompare = Integer.compare(partPriorities.get(o1.getPartName()), partPriorities.get(o2.getPartName()));
        if (priorityCompare != 0) {
            return priorityCompare;
        }
        return Integer.compare(o1.getDuration(), o2.getDuration());
    }
}
