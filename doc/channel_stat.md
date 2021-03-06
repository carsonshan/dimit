Channel Runtime Statistics
================================
通道运行时统计

## 统计功能
### 通道发送返回码    
- 发送成功率 严重错误告警 成功率低于xx%告警

### 发送耗时       
- 发送效率 响应时间高于xx毫秒告警

### 通道接收返回码    
- 接通成功率 成功率低于xx%告警 接通率低于xx%告警

### 发送接收时间差    
- 状态报告延迟率 超时未返回报告告警  TODO 待定


## 实现原理B
### ChannelStat统计 (发送、接收相同)
- 快照线程 + 循环数组
- 快照线程每秒(间隔可配)产生一个ChannelStat快照, 循环放入定长数组(数组长度可配)
- 若放入位置为i,那么比较i和i-1位置的ChannelStat快照，可以得出当前的tps、succTps、avgTime

### ChannelTotalStat统计 原理同上



## 实现原理A 不使用 B方案更好
### 统计发送
- ChannelWrapper提供更新统计数据的方法
- ChannelSendStat记录每个通道运行到现在的基础数据，如

```java
    string channel = 3;     // 通道ID
    uint64 succCount = 4;   // 总发送成功数  
    uint64 succTime = 5;    // 总发送成功时间
    uint64 failCount = 6;   // 总发送失败数
    uint64 failTime = 7;    // 总发送失败时间
```

- 每秒生成一个ChannelSendStat的快照，放入快照队列
- 统计线程每隔3秒(可配置)从快照队列取出数据，统计间隔期内的发送情况如下
    - 发送成功率
    - 发送平均响应时间
- 统计结果处理
    - 计算发送成功率
    - 计算发送成功的平均耗时 //不太关心
    - 运行时调整ChannelWrapper的priority值(此值决定通道的选取优先级，越大优先选择这个通道发送)
        - 异常自动切换: priority值小于1，视为不可用通道，将更新通道状态为ChannelStatus.INVALID, 由zk通知更新 （以后由管理界面重新开启）
            
### 统计回调
- ChannelWrapper提供更新统计数据的方法
- ChannelRecvStat记录每个通道运行到现在的基础数据，如
 
```java
    string channel = 3;     // 通道ID
    uint64 succCount = 4;   // 总接收成功数
    uint64 failCount = 5;   // 总接收失败数
```

- 实现原理如 `统计发送`

### 发送、接收比较 TODO 是否做待定 实现较复杂
- 将集群里的`ChannelSendStat` `ChannelRecvStat` 由汇总线程将快照数据上传到redis的对应通道队列里
    - 一个通道对应redis的2个队列，发送统计队列和接收统计队列
- 一个统计线程(Dimiter的master角色)每隔60秒从队列里获取快照，比对期间的发送、接收总数差
    - 差值大于50%发送量，或者接收数量为0时，将通道设置为ChannelStatus.INVALID







