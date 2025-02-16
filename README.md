要求：

1、读取文件"轮船定位数据.json"，以List<String>存数据到内存，要求具体实现类为ArrayList

建议：可以用JDK自带工具类Files;或者引入第三方工具包Apache/commons-io，使用其中的FileUtils类。用以简化IO操作。

检查：检查读取后数据行数，应该有12000条数据。

2、每一行数据都是一条json，这是常用的数据格式，建议了解掌握。将每一条数据格式化，并以对象的形式保存为List<SomeObject>

建议：建议采用行业标准json解析第三方包Jackson，建议采用"对象数据绑定"方式，导入第三方包jackson-databind来完成转换

建议：对象数据绑定方式解析json需要构建对应与该json格式的数据对象，给一个参考如下（自行调整）：

public class ShipNavigationInfo {
    @JsonProperty("ship-id")
    private String shipId;
    @JsonProperty("ship-name")
    private String shipName;
    private ShipLocation location;
    private Long timestamp;
    @JsonProperty("order-no")
    private Integer orderNo;
    @JsonProperty("log-id")
    private String logId;
}

public class ShipLocation {
    private BigDecimal longitude;
    private BigDecimal latitude;
}

数据对象必须要有空构造器和所有属性的get、set方法，否则jackson不能正确解析

（如果用的IDE为IntelJ，可以在数据对象中右键根据模板生成空构造器和所有属性的get、set方法）

检查：转换为数据对象后，数量应该保持一致，仍为12000

3、数据本来都是由探测器log1记录的，由于各种原因混进了1000条log2记录的数据，把这1000条数据过滤掉。

建议：首次学习先创建新列表，遍历列表把合格数据加入新列表的方式实现（logId为"log1"的是合格数据）。（熟练以后可以考虑采用list.stream.filter.toList方式过滤数据。）

检查：过滤后新的List中应该有数据11000条，并且不存在logId为"log2"的数据

检查：log2记录的是哪一条船的数据？

4、同样是各种复杂的原因，某条船的数据被重复记录了一次，把这些重复的数据过滤掉。

建议：可以考虑创建一个HashSet<SomeObject>，然后把List<SomeObject>的数据全部放入Set中完成去重。

必须重写SomeObject的HashCode()和equals()方法，要求ship-id、timestamp和log-id相同则为相同数据

（如果用的IDE为IntelJ，可以在数据对象中右键根据模板重写这两个方法，细节可能要微调）

检查：去重后得到的List，应该有10000条数据

5、把数据按照ship-id分组，数据按照timestamp升序排列，并且转换为JSON保存数据到文件{ship-id}.json中。

建议：可以借助HashMap<String，List<SomeObject>>完成分组。

借助List.sort方法完成升序排列。

通过jackson-databind将SomeObject转换为JSON形式的String。最终得到List<String>

可以用JDK自带工具类Files;或者引入第三方工具包Apache/commons-io，使用其中的FileUtils类完成写文件操作

检查：最终应该得到10个文件，每个文件1000条数据，数据按order-no升序排列。符合条件说明该步骤基本正确。

6（可选）、在第5步中得到的文件，每10条数据取一条，按照location信息绘制散点图和二维折线图

建议：可采用第三方包JFreeChart来绘制图，应该有100个点，自己看一下生成图的效果

7（可选）、假设地球是平的（不要纠结这个，为了简单起见，不然很难计算），估算船1在整个数据周期内走的总路程。

（假设船在两点间总是对角线移动，可以简单以经纬度变化的平方和开根号代表距离）

建议：可以循环遍历数据计算，最后相加。熟练以后可以考虑用List.stream.reduce实现

附加说明：

关于数据格式及意义：

{
   "location":{
      "longitude":-89.62411,
      "latitude":0.29857
   },
   "timestamp":1739252353970,
   "ship-id":"ship-test-3",
   "ship-name":"船3",
   "order-no":732,
   "log-id":"log1"
}

location: 轮船当前位置，包含经度和纬度信息，保留五位小数
timestamp：数据采集时间戳
ship-id：该轮船的id号，一般具有唯一性
ship-name：轮船名，不一定唯一（在这一批数据是唯一的）
order-no:数据序号，按照时间戳升序排列，用来检查数据是否按照时间戳升序
log-id:探测器id，用来标记该数据由哪一个探测器上传
