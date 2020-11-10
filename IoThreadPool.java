package com.huawei.mutidatasource.async;

import com.huawei.mutidatasource.entity.TestUser;
import com.huawei.mutidatasource.mapper.TestUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


@Configuration
public class IoThreadPool {
    @Autowired
    private TestUserMapper mapper;

    // 入库线程池提供监控功能！
    // 用来等待所有入库任务执行完毕
    private static final Map<Long, List<Future<Integer>>> monitor = new ConcurrentHashMap<>();

    private static final ExecutorService workers = Executors.newFixedThreadPool(4);

    public static <T> void execute(long taskId, AsyncDbTask<T> task) {
        Future<Integer> future = workers.submit(task);
        monitor.computeIfAbsent(taskId, k -> new ArrayList<>()).add(future);
    }

    public static void waitAllComplete(long taskId) throws ExecutionException, InterruptedException {
        for (Future<Integer> future : IoThreadPool.monitor.get(taskId)) {
            future.get();
        }
    }

    public void test() throws ExecutionException, InterruptedException {
        long taskId = 2000002876;
        List<TestUser> list = new ArrayList<>();
        TestUser user = new TestUser(6, "aaa", 500);
        list.add(user);
        AsyncDbTask<TestUser> task = new AsyncDbTask<>(mapper::insertTestUser, "nr_" + 2000002876, list);
        IoThreadPool.execute(taskId, task);
        IoThreadPool.waitAllComplete(taskId);
    }
}
