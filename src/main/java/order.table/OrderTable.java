package order.table;

import java.util.TreeMap;

public class OrderTable {
    private final TreeMap<Integer, Integer> askMap = new TreeMap<>();
    private final TreeMap<Integer, Integer> bidMap = new TreeMap<>();

    public TreeMap<Integer, Integer> getAskMap() {
        return askMap;
    }

    public TreeMap<Integer, Integer> getBidMap() {
        return bidMap;
    }
}
