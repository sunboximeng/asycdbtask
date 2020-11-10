package com.huawei.mutidatasource.async;


import java.util.List;

@FunctionalInterface
public interface AsyncInsert<T> {
    int insert(String schema, List<T> list);
}
