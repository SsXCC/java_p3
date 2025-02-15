package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.math.BigDecimal;
import java.util.List;

public class Chart {
    public void draw(List<Infos> data) {
        XYSeries dataSeries = new XYSeries("InternetExplorer");
        XYSeriesCollection dataset = new XYSeriesCollection();
        String key = "";
        for (int i = 9; i < data.size(); i += 10) {
            BigDecimal longitude = data.get(i).getLocation().getLongitude();
            BigDecimal latitude = data.get(i).getLocation().getLatitude();
            dataSeries.add(longitude, latitude);
            key = data.get(i).getShipId();
        }

        dataset.addSeries(dataSeries);
        // 创建JFreeChart对象
        JFreeChart chart1 = ChartFactory.createXYLineChart(
                "Example", // 图标题
                "latitude", // x轴标题
                "longitude", // y轴标题
                dataset, //数据集
                PlotOrientation.VERTICAL, //图表方向
                false, true, false);

        JFreeChart chart2 = ChartFactory.createScatterPlot(
                "Example", // 图标题
                "latitude", // x轴标题
                "longitude", // y轴标题
                dataset //数据集
        );


        // 利用awt进行显示
        ChartFrame chartFrame1 = new ChartFrame(key, chart1);
        chartFrame1.pack();
        chartFrame1.setVisible(true);

        ChartFrame chartFrame2 = new ChartFrame(key, chart2);
        chartFrame2.pack();
        chartFrame2.setVisible(true);
    }
}
