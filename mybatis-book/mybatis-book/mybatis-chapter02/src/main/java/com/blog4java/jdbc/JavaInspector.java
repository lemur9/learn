package com.blog4java.jdbc;

import lombok.Data;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * java内省机制
 */
@Data
public class JavaInspector {
    private String conn;

    public static void inspectDataSource(Object dataSource) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(dataSource.getClass());
            for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
                System.out.println("Property: " + pd.getName());
                if (pd.getReadMethod() != null) {
                    System.out.println("Value: " + pd.getReadMethod().invoke(dataSource));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JavaInspector dataSource = new JavaInspector();
        dataSource.setConn("1111");
        inspectDataSource(dataSource);
    }

}
