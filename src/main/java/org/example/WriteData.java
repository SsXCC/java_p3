package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class WriteData {
    ObjectMapper objectMapper = new ObjectMapper();
    List<Infos> filterList = new ArrayList<Infos>();
    HashSet<Infos> filterSet = new HashSet<>();
    HashMap<String, List<Infos>> map = new HashMap<>();
    List<String> keys = new ArrayList<>();

    public WriteData(List<Infos> filterList, HashSet<Infos> filterSet, HashMap<String, List<Infos>> map) {
        this.filterList = filterList;
        this.filterSet = filterSet;
        this.map = map;
        this.keys = new ArrayList<>(map.keySet());
    }

    public void writeToFilterFile(String path) throws IOException {
        List<String> data = filterList.stream().map(s -> {
            try {
                return objectMapper.writeValueAsString(s);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).toList();
        //写入文件
        FileUtils.writeLines(new File(path), data, false);
        System.out.println("成功写入logID只含为log1的文件，数据量：" + filterList.size());
    }

    public void writeToFilterSetFile(String path) throws IOException {
        List<String> data = filterSet.stream().map(s -> {
            try {
                return objectMapper.writeValueAsString(s);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).toList();
        //写入文件
        FileUtils.writeLines(new File(path), data, false);
        System.out.println("成功写入去重后的未分组文件，数据量：" + filterSet.size());
    }

    public void writeToGroupFile() throws IOException {
        for (String key : keys) {
            List<Infos> sortData = map.get(key);
            sortData.sort(Comparator.comparing(Infos::getTimestamp));
            List<String> data = sortData.stream().map(s -> {
                try {
                    return objectMapper.writeValueAsString(s);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }).toList();

            String path = "src/main/resources/" + key + ".json"; //文件路径
            //写入文件
            FileUtils.writeLines(new File(path), data, false);
            System.out.println("成功写入分组文件" + key + ".json，数据量：" + data.size());

        }
    }
}
