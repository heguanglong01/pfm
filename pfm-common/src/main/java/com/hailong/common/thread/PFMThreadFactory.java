package com.hailong.common.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class PFMThreadFactory implements ThreadFactory {
	private AtomicInteger count = new AtomicInteger(0);
	@Override
	public Thread newThread(Runnable r) {
		   Thread t = new Thread(r); 
           String threadName = r.getClass().getName() + count.addAndGet(1);    
           t.setName(threadName);    
           return t;   
	}

}
