package org.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        //读取原始json数据
        FilterData filterData = new FilterData("D:/code/testData/轮船定位数据.json");

        //获取logID为log2的船
        filterData.getLog2ShipNames();

        //输出只含log1的船的数据文件
        filterData.writeToFilterFile("src/main/resources/data1.json");

        //输出去重后的未分组文件
        filterData.writeToFilterSetFile("src/main/resources/data2.json");

        //输出分组文件
        filterData.writeToGroupFile();

        //每10条数据取一条，按照location信息绘制散点图和二维折线图
        HashMap<String, List<Infos>> map = filterData.getMap();
        List<String> keys = filterData.getKeys();
        List<Infos> data = map.get(keys.getFirst());

        //绘图
        Chart chart = new Chart();
        chart.draw(data);

    }

}

