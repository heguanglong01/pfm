package com.hailong.common.thread;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PFMThreadPoolExecutor extends ThreadPoolExecutor {
	public static final Logger logger = LoggerFactory.getLogger(PFMThreadPoolExecutor.class);
	public static PFMThreadPoolExecutor pool;
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
	public PFMThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MINUTES, new SynchronousQueue<Runnable>(),
				new PFMThreadFactory(), new PFMRejectedExecutionHandler());
		pool=this;
	}

	public void destory() {
		if (this != null) {
			shutdownNow();
		}
	}


	// 线程执行之前运行
	@Override
	protected void beforeExecute(Thread t, Runnable r) {
//		statuCount.addAndGet(1);
//		logger.info("thread before Execute:"+t.getName());
	}

	// 线程执行之后运行
	@Override
	protected void afterExecute(Runnable r, Throwable t) {
//		endCount.addAndGet(1);
//		logger.info("thread after Execute:"+r.getClass().getName());
	}

	// 整个线程池停止之后
	@Override
	protected void terminated() {
		logger.info("dmc DMCThreadPoolExecutor thread stop");
	}

	// 测试构造的线程池
	public static void main(String[] args) {
		new PFMThreadPoolExecutor(0, Integer.MAX_VALUE, 60);
		for (int i = 1; i < 10; i++) {
			PFMThreadPoolExecutor.pool.execute(new Thread("heguanlgong"+i) {
				@Override
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		while (true) {
			logger.info("corePoolSize->" + PFMThreadPoolExecutor.pool.getCorePoolSize() + ", maximumPoolSize->"
					+ PFMThreadPoolExecutor.pool.getMaximumPoolSize() + ", activeCount->"
					+ PFMThreadPoolExecutor.pool.getActiveCount() + ", poolSize->"
					+ PFMThreadPoolExecutor.pool.getPoolSize() + ", taskCount->"
					+ PFMThreadPoolExecutor.pool.getTaskCount() + ", largestPoolSize->"
					+ PFMThreadPoolExecutor.pool.getLargestPoolSize() + ", completedTaskCount->"
					+ PFMThreadPoolExecutor.pool.getCompletedTaskCount());
			try {
				Thread.sleep(10000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
