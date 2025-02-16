package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class FilterData {
    List<Infos> totalList = new ArrayList<Infos>();
    List<Infos> filterList = new ArrayList<Infos>();
    HashSet<String> log2_shipNames = new HashSet<>();
    HashSet<Infos> filterSet = new HashSet<>();
    HashMap<String, List<Infos>> map = new HashMap<>();


    public FilterData(List<Infos> totalList) {
        this.totalList = totalList;
    }


    public void getLog2ShipNames() {
        if (log2_shipNames.isEmpty()) {
            for (Infos s : totalList) {
                if (s.getLogId().equals("log2")) log2_shipNames.add(s.getShipName());
            }
        }
        System.out.println("logID为log2的船：" + log2_shipNames);
    }

    public List<Infos> getFilterData() {
        if (!filterList.isEmpty()) return filterList;
        filterList = totalList.stream().filter(m -> m.getLogId().equals("log1")).collect(Collectors.toList());
        return filterList;
    }

    public HashSet<Infos> getFilterSet() {
        if (!filterSet.isEmpty()) return filterSet;

        if (filterList.isEmpty()) {
            filterSet = new HashSet<>(getFilterData());
        } else {
            filterSet = new HashSet<>(filterList);
        }

        return filterSet;
    }

    public HashMap<String, List<Infos>> getMap() {
        if (!map.isEmpty()) return map;

        List<Infos> list3 = filterSet.isEmpty() ? new ArrayList<>(getFilterSet()) : new ArrayList<>(filterSet);
        for (Infos s : list3) {
            if (map.containsKey(s.getShipId())) {
                map.get(s.getShipId()).add(s);
            } else {
                List<Infos> l = new ArrayList<>();
                l.add(s);
                map.put(s.getShipId(), l);
            }
        }

        return map;
    }

}
