package com.hailong.common.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomThreadPoolExecutor extends ThreadPoolExecutor {
	/**
	 * 线程池初始化方法
	 * 
	 * corePoolSize 核心线程池大小----1 maximumPoolSize 最大线程池大小----3 keepAliveTime
	 * 线程池中超过corePoolSize数目的空闲线程最大存活时间----30+单位TimeUnit TimeUnit
	 * keepAliveTime时间单位----TimeUnit.MINUTES workQueue 阻塞队列----new
	 * ArrayBlockingQueue<Runnable>(5)====5容量的阻塞队列 threadFactory 新建线程工厂----new
	 * CustomThreadFactory()====定制的线程工厂 rejectedExecutionHandler
	 * 当提交任务数超过maxmumPoolSize+workQueue之和时,
	 * 即当提交第41个任务时(前面线程都没有执行完,此测试方法中用sleep(100)),
	 * 任务会交给RejectedExecutionHandler来处理 1 3 30
	 */
	public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MINUTES, new SynchronousQueue<Runnable>(),
				new PFMThreadFactory(), new PFMRejectedExecutionHandler());
		
//		pool = new ThreadPoolExecutor(
//				0, Integer.MAX_VALUE,
//                60L, TimeUnit.SECONDS,
//                new SynchronousQueue<Runnable>(),
//				new CustomThreadFactory(),
//				new CustomRejectedExecutionHandler());
		
	/*	new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
		
		this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
	             Executors.defaultThreadFactory(), defaultHandler);*/
	}
	public void destory() {
		if (this != null) {
			shutdownNow();
		}
	}
  
	public ExecutorService getCustomThreadPoolExecutor() {
		return this;
	}
}
