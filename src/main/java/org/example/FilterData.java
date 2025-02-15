package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class FilterData {
    ObjectMapper objectMapper = new ObjectMapper();
    String[] strArr;
    List<Infos> totalList = new ArrayList<Infos>();
    List<Infos> filterList = new ArrayList<Infos>();
    HashSet<String> log2_shipNames = new HashSet<>();
    HashSet<Infos> filterSet = new HashSet<>();
    HashMap<String, List<Infos>> map = new HashMap<>();
    List<String> keys = new ArrayList<>();


    public FilterData(String path) {
        try {
            //读取文件
            String content = FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
//            System.out.println(content);
            strArr = content.split("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Infos> getTotalList() throws JsonProcessingException {
        if (totalList.isEmpty()) {
            for (String s : strArr) {
                Infos i1 = objectMapper.readValue(s, Infos.class);
                totalList.add(i1);
            }
        }

        return totalList;
    }

    public void getLog2ShipNames() throws JsonProcessingException {
        if (log2_shipNames.isEmpty()) {
            for (String s : strArr) {
                Infos i1 = objectMapper.readValue(s, Infos.class);
                if (i1.getLogId().equals("log2")) log2_shipNames.add(i1.getShipName());
            }
        }
        System.out.println("logID为log2的船：" + log2_shipNames);
    }

    public List<Infos> getFilterData() throws JsonProcessingException {
        if (filterList.isEmpty()) {
            if (totalList.isEmpty()) {
                filterList = getTotalList().stream().filter(m -> m.getLogId().equals("log1")).collect(Collectors.toList());
            } else {
                filterList = totalList.stream().filter(m -> m.getLogId().equals("log1")).collect(Collectors.toList());
            }
        }
        return filterList;
    }

    public HashSet<Infos> getFilterSet() throws JsonProcessingException {
        if (!filterSet.isEmpty()) return filterSet;

        if (filterList.isEmpty()) {
            filterSet = new HashSet<>(getFilterData());
        } else {
            filterSet = new HashSet<>(filterList);
        }

        return filterSet;
    }

    public HashMap<String, List<Infos>> getMap() throws JsonProcessingException {
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

    public List<String> getKeys() throws JsonProcessingException {
        if (!keys.isEmpty()) return keys;
        if (map.isEmpty()) {
            keys = new ArrayList<>(getMap().keySet());
        } else keys = new ArrayList<>(map.keySet());

        return keys;
    }


    public void writeToFilterFile(String path) throws IOException {
        if (filterList.isEmpty()) filterList = getFilterData();
        String data1 = objectMapper.writeValueAsString(filterList);

        //写入文件
        FileUtils.writeStringToFile(new File(path), data1, StandardCharsets.UTF_8);
        System.out.println("成功写入logID只含为log1的文件，数据量：" + filterList.size());
    }

    public void writeToFilterSetFile(String path) throws IOException {
        if (filterSet.isEmpty()) filterSet = getFilterSet();
        String data2 = objectMapper.writeValueAsString(filterSet);

        //写入文件
        FileUtils.writeStringToFile(new File(path), data2, StandardCharsets.UTF_8);
        System.out.println("成功写入去重后的未分组文件，数据量：" + filterSet.size());
    }

    public void writeToGroupFile() throws IOException {
        if (keys.isEmpty()) keys = getKeys();
        for (String key : keys) {
            List<Infos> data = map.get(key);
            data.sort(Comparator.comparing(Infos::getTimestamp));

            String data3 = objectMapper.writeValueAsString(data);
            String path = "src/main/resources/" + key + ".json"; //文件路径

            //写入文件
            FileUtils.writeStringToFile(new File(path), data3, StandardCharsets.UTF_8);
            System.out.println("成功写入分组文件" + key + ".json，数据量：" + data.size());

        }
    }
}
