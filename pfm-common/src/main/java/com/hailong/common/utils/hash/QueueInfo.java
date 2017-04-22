package com.hailong.common.utils.hash;

public class QueueInfo {
  private String queueName;
  private int weight;
  public static final int DEFAULT_WEIGHT = 1;

  public QueueInfo() {}
  
  public QueueInfo(String queueName) {
      this.queueName = queueName;
      this.weight = DEFAULT_WEIGHT;
  }
  
  public QueueInfo(String queueName, int weight) {
    this.queueName = queueName;
    this.weight = weight;
  }

  public int getWeight() {
    return this.weight;
  }
  
  public String getQueueName(){
      return this.queueName;
  }

}
