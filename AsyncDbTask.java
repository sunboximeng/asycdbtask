package com.huawei.mutidatasource.async;

import java.util.List;
import java.util.concurrent.Callable;

public class AsyncDbTask<T> implements Callable<Integer> {
    private final AsyncInsert<T> method;
    private final String schema;
    private final List<T> list;

    public AsyncDbTask(AsyncInsert<T> method, String schema, List<T> list) {
        this.method = method;
        this.schema = schema;
        this.list = list;
    }

    @Override
    public Integer call() throws Exception {
        return method.insert(schema, list);
    }
}
