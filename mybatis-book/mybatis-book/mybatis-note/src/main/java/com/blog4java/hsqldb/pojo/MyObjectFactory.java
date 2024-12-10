package com.blog4java.hsqldb.pojo;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

public class MyObjectFactory extends DefaultObjectFactory {
    @Override
    public <T> T create(Class<T> type) {
        System.out.println("MyObjectFactory create");
        return super.create(type);
    }
}
