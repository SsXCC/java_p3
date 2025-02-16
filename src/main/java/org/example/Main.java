package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String path = "src/main/resources/轮船定位数据.json";
        List<Infos> totalList = FileUtils.readLines(new File(path), StandardCharsets.UTF_8)
                .stream().map(s -> {
                    try {
                        return objectMapper.readValue(s, Infos.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();

        //读取原始json数据
        FilterData filterData = new FilterData(totalList);
        List<Infos> filterList = filterData.getFilterData();
        HashSet<Infos> filterSet = filterData.getFilterSet();
        HashMap<String, List<Infos>> map = filterData.getMap();

        WriteData writeData = new WriteData(filterList, filterSet, map);

        //获取logID为log2的船
        filterData.getLog2ShipNames();

        //输出只含log1的船的数据文件
        writeData.writeToFilterFile("src/main/resources/data1.json");

        //输出去重后的未分组文件
        writeData.writeToFilterSetFile("src/main/resources/data2.json");

        //输出分组文件
        writeData.writeToGroupFile();

        //每10条数据取一条，按照location信息绘制散点图和二维折线图
        List<String> keys = new ArrayList<>(map.keySet());
        List<Infos> data = map.get(keys.getFirst());

        //绘图
        Chart chart = new Chart();
        chart.draw(data);

    }

}

