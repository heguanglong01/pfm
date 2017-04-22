package com.hailong.common.utils.hash;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class QueueRoutingHash {

  private static Hashing algo = Hashing.MURMUR_HASH;
  
  public static void init(List<QueueInfo> queueList,TreeMap<Long, QueueInfo> nodes) {
    for (int i = 0; i != queueList.size(); ++i) {
      final QueueInfo queueInfo = queueList.get(i);
      for (int n = 0; n < 160 * queueInfo.getWeight(); n++) {
        nodes.put(algo.hash(queueInfo.getQueueName() + "*" + queueInfo.getWeight() + n), queueInfo);
      }
    }
  }

  public static QueueInfo getQueueInfo(byte[] key,TreeMap<Long, QueueInfo> nodes) {
    SortedMap<Long, QueueInfo> tail = nodes.tailMap(algo.hash(key));
    if (tail.isEmpty()) {
      return nodes.get(nodes.firstKey());
    }
    return tail.get(tail.firstKey());
  }

  public static QueueInfo getQueueInfo(String key,TreeMap<Long, QueueInfo> nodes) {
    return getQueueInfo(SafeEncoder.encode(key),nodes);
  }
  
  public static void main(String[] args){
      List<QueueInfo> list = new ArrayList<QueueInfo>();
      for(int i=0; i<10; i++){
          QueueInfo q = new QueueInfo("1");
          list.add(q);
      }
      
//      QueueRouting.init(list);
  }
}
