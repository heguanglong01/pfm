package com.hailong.common.utils.hash;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class QueueRouting {

  private static TreeMap<Long, QueueInfo> nodes;
  private static Hashing algo = Hashing.MURMUR_HASH;
  
  public static void init(List<QueueInfo> queueList) {
    nodes = new TreeMap<Long, QueueInfo>();

    for (int i = 0; i != queueList.size(); ++i) {
      final QueueInfo queueInfo = queueList.get(i);
      for (int n = 0; n < 160 * queueInfo.getWeight(); n++) {
        nodes.put(algo.hash(queueInfo.getQueueName() + "*" + queueInfo.getWeight() + n), queueInfo);
      }
    }
  }

  public static QueueInfo getQueueInfo(byte[] key) {
    SortedMap<Long, QueueInfo> tail = nodes.tailMap(algo.hash(key));
    if (tail.isEmpty()) {
      return nodes.get(nodes.firstKey());
    }
    return tail.get(tail.firstKey());
  }

  public static QueueInfo getQueueInfo(String key) {
    return getQueueInfo(SafeEncoder.encode(key));
  }
  
  public static void main(String[] args){
      List<QueueInfo> list = new ArrayList<QueueInfo>();
      for(int i=0; i<10; i++){
          QueueInfo q = new QueueInfo("1");
          list.add(q);
      }
      
      QueueRouting.init(list);
  }
}
