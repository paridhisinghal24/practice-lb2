package com.lb;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.lb.service.LoadBalancerContext;
import com.lb.service.RoundRobinLoadBalancer;

public class LoadBalancerContextTest {
	//@InjectMocks
	

	@Mock
	ServerManager serverManager;

	@BeforeEach
	void before() {
		MockitoAnnotations.initMocks(this);
		//MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testRoundRobin_loadBalancer_Return12() {
		Map<String, Server> serverMap = new HashMap<String, Server>();
		serverMap.put("1", new Server("1", "Server1"));
		serverMap.put("2", new Server("2", "Server2"));
		LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new RoundRobinLoadBalancer(serverManager));
		Mockito.when(serverManager.getServerMap()).thenReturn(serverMap);
		Assertions.assertEquals("1", loadBalancerContext.getServer().getIpAddress() );
		Assertions.assertEquals("2", loadBalancerContext.getServer().getIpAddress() );

	}

	@Test
	void testRoundRobin_LoadBalancerMultithreading_Return12() throws InterruptedException, ExecutionException {
		Map<String, Server> serverMap = new HashMap<String, Server>();
		serverMap.put("1", new Server("1", "Server1"));
		serverMap.put("2", new Server("2", "Server2"));
		serverMap.put("3", new Server("3", "Server3"));
		Mockito.when(serverManager.getServerMap()).thenReturn(serverMap);
		LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new RoundRobinLoadBalancer(serverManager));

		Callable<Server> call1 = () ->
        {
        	return loadBalancerContext.getServer();
        };
        
        Callable<Server> call2 = () ->
        {
        	return loadBalancerContext.getServer();
        };
        
        FutureTask<Server> futureTask1 = new FutureTask<>(call1);
        FutureTask<Server> futureTask2 = new FutureTask<>(call2);
        
        Thread thread1 = new Thread(futureTask1);
        Thread thread2 = new Thread(futureTask2);

        thread1.start();
        thread2.start();
        thread1.join();

        Server result1 = futureTask1.get();
        Server result2 = futureTask2.get();
        
        
		Assertions.assertEquals("1", result1.getIpAddress() );
		Assertions.assertEquals("2", result2.getIpAddress() );

	}
}
