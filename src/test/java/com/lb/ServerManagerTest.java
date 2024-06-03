package com.lb;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;

class ServerManagerTest {
	ServerManager serverManager = new ServerManager();

	ExecutorService executorService;

	@BeforeEach
	void beforetest() {
		executorService = Executors.newFixedThreadPool(3);
	}

	@AfterEach
	void aftertest() throws InterruptedException {
		executorService.shutdown();
		executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
	}

	@Disabled
	@RepeatedTest(10)
    void ServerManager_AddServerWithMultipleThreads_Return10() throws InterruptedException {
		test_adding_using_mutltithread();
		executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
		Assertions.assertEquals(10, serverManager.getServerMap().size());
    }

	@Disabled
	@RepeatedTest(10)
    void ServerManager_AddRemoveServerWithMultipleThreads_Return8() throws InterruptedException {
		test_adding_using_mutltithread();
		test_removing_using_mutltithread();
		executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
		Assertions.assertEquals(8, serverManager.getServerMap().size());
    }

	@RepeatedTest(10)
    void ServerManager_AddRemoveSameServerWithMT_Return2() throws InterruptedException {
		test_adding_same_server_using_mutltithread();
		executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
		Assertions.assertEquals(2, serverManager.getServerMap().size());
    }

	void test_adding_using_mutltithread() throws InterruptedException {
		for(int i = 0; i < 10; i++) {
            final int indexCopy = i;
			executorService.submit(()-> serverManager.addServer(new Server(String.valueOf(indexCopy), "Server" + indexCopy)));
		}
	}

	void test_removing_using_mutltithread() throws InterruptedException {
		executorService.submit(()-> serverManager.removeServer(new Server("1", "Server1")));
		executorService.submit(()-> serverManager.removeServer(new Server("9", "Server9")));
	}

	void test_adding_same_server_using_mutltithread() throws InterruptedException {
		for(int i = 0; i < 10; i++) {
            final int indexCopy = i;
			executorService.submit(()-> serverManager.addServer(new Server(String.valueOf(indexCopy%2), "Server" + indexCopy)));
		}
	}
}
