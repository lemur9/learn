package com.blog4java.hsqldb;

import com.blog4java.common.DbUtils;
import com.blog4java.hsqldb.pojo.MyObjectFactory;
import com.blog4java.hsqldb.pojo.UserEntity;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.executor.loader.ProxyFactory;
import org.apache.ibatis.executor.loader.ResultLoaderMap;
import org.apache.ibatis.executor.loader.javassist.JavassistProxyFactory;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.jdbc.SqlRunner;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.hsqldb.jdbc.JDBCDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Date;
import java.util.*;

/**
 * Mybatis常用工具类
 * 1.SQL
 * 2.ScriptRunner
 * 3.SqlRunner
 * 4.MetaObject
 * 5.MetaClass
 * 6.ObjectFactory
 * 7.ProxyFactory
 */
public class MybatisBasicExample {


    @Before
    public void initData() {
        DbUtils.initData();
    }


    /**
     * SQL工具类：动态构建SQL语句
     */
    @Test
    public void testSQL() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = new SQL() {{
                SELECT("id, create_time as createTime, name, password, phone, nick_name as nickName");
                FROM("user");
                WHERE("nick_name = 'User1'");
            }}.toString();

            connection = DriverManager.getConnection("jdbc:hsqldb:mem:mybatis", "sa", "");
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                    String name = resultSet.getMetaData().getColumnLabel(i + 1);
                    String value = resultSet.getString(i + 1);
                    System.out.print(name + "=" + value);
                    if (i + 1 < resultSet.getMetaData().getColumnCount()) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * ScriptRunner工具类：用于读取脚本文件中的SQL语句并执行。
     * 默认支持（DDL：定义结构、DML：操作数据、DQL：查询数据）
     * DDL 和 DML 会正常执行。
     * DQL 的结果不会返回给调用方，而是由数据库本身执行完成。例如，SELECT * FROM users; 不会直接返回查询结果到 Java 程序。
     */
    @Test
    public void testScriptRunner() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:hsqldb:mem:mybatis", "sa", "");
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            String script = "insert into user (create_time, name, password, phone, nick_name) values('2010-10-26 10:20:30', 'User15', 'test', '18700001111', 'User15');";
            StringReader reader = new StringReader(script);

            scriptRunner.runScript(reader);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * SqlRunner：快速执行sql
     * closeConnection()：用于关闭Connection对象。
     * selectOne(String sql, Object… args)：执行SELECT语句，SQL语句中可以使用占位符，如果SQL中包含占位符，则可变参数用于为参数占位符赋值，该方法只返回一条记录。若查询结果行数不等于一，则会抛出SQLException异常。
     * selectAll(String sql, Object… args)：该方法和selectOne()方法的作用相同，只不过该方法可以返回多条记录，方法返回值是一个List对象，List中包含多个Map对象，每个Map对象对应数据库中的一行记录。
     * insert(String sql, Object… args)：执行一条INSERT语句，插入一条记录。
     * update(String sql, Object… args)：更新若干条记录。
     * delete(String sql, Object… args)：删除若干条记录。
     * run(String sql)：执行任意一条SQL语句，最好为DDL语句。
     */
    @Test
    public void testSqlRunner() {
        SqlRunner sqlRunner = null;
        try {
            Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:mybatis", "sa", "");
            sqlRunner = new SqlRunner(connection);
            String sql = "select * from user where name = ?";
            Map<String, Object> user1 = sqlRunner.selectOne(sql, "User1");
            for (String key : user1.keySet()) {
                System.out.println(key + "=" + user1.get(key));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (sqlRunner != null) {
                sqlRunner.closeConnection();
            }
        }
    }


    /**
     * MetaObject：使用反射机制获取和操作对象的属性，值
     * 通常使用SystemMetaObject.forObject方法快速获取MetaObject对象
     */
    @Test
    public void testMetaObject() {
        UserEntity userEntity = new UserEntity().load();
//        MetaObject metaObject = MetaObject.forObject(userEntity, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
        MetaObject metaObject = SystemMetaObject.forObject(userEntity);

        metaObject.setValue("name", "limu");
        System.out.println(metaObject.getValue("name"));
    }

    /**
     * MetaClass：用于获取类相关的信息
     * MetaClass可以获取Getter/Setter方法对应的Invoker对象，从而使用Getter/Setter方法
     */
    @Test
    public void testMetaClass() {
        try {
            MetaClass metaClass = MetaClass.forClass(UserEntity.class, new DefaultReflectorFactory());
            System.out.println(metaClass.getGetterType("name"));

            Invoker nameInvoker = metaClass.getGetInvoker("name");
            String name = (String) nameInvoker.invoke(new UserEntity(1L, "Lemur", new Date(System.currentTimeMillis()), "123456", "19890989876", "Lemur"), null);
            System.out.println(name);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ObjectFactory：用于创建普通对象实例的工厂接口，通常用于在映射SQL结果时，创建映射对象(例如 POJO 类或 Map)
     * 自定义ObjectFactory
     * 1.继承 DefaultObjectFactory 类
     * 2.重写 DefaultObjectFactory 类的 create 方法
     * 3.在MyBatis主配置文件中通过 <objectFactory> 标签配置自定义的 ObjectFactory
     */
    @Test
    public void testObjectFactory() {
        SqlSessionManager sqlSessionManager = null;
        try {

            //region 使用ObjectFactory创建普通对象
            DefaultObjectFactory defaultObjectFactory = new DefaultObjectFactory();
            UserEntity userEntity = defaultObjectFactory.create(UserEntity.class).load();
            System.out.println(userEntity);
            //endregion


            //region 使用自定义的ObjectFactory
            // 创建数据源
            UnpooledDataSource unpooledDataSource = new UnpooledDataSource(JDBCDriver.class.getClassLoader(), "org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:mybatis", "sa", "");

            // 构建environment，指定当前运行环境
            Environment environment = new Environment("default", new JdbcTransactionFactory(), unpooledDataSource);

            // 构建配置类
            Configuration configuration = new Configuration(environment);
            configuration.setObjectFactory(new MyObjectFactory());

            // 获取类型注册器
            // TypeHandlerRegistry：用于建立Java类型、JDBC类型与TypeHandler之间的对应关系
            final TypeHandlerRegistry registry = configuration.getTypeHandlerRegistry();

            // 建立Java实体属性与数据库字段之间的映射关系
            final ResultMap rm = new ResultMap.Builder(configuration, "defaultResultMap", UserEntity.class, new
                    ArrayList<ResultMapping>() {
                        {
                            add(new ResultMapping.Builder(configuration, "id", "id", registry.getTypeHandler(long.class)).build());
                            add(new ResultMapping.Builder(configuration, "name", "name", registry.getTypeHandler(String.class)).build());
                            add(new ResultMapping.Builder(configuration, "password", "password", registry.getTypeHandler(String.class)).build());
                            add(new ResultMapping.Builder(configuration, "nickName", "nick_name", registry.getTypeHandler(String.class)).build());
                            add(new ResultMapping.Builder(configuration, "phone", "phone", registry.getTypeHandler(String.class)).build());
                            add(new ResultMapping.Builder(configuration, "createTime", "create_time", registry.getTypeHandler(Date.class)).build());
                        }
                    }).build();

            // 构建SQL配置信息
            MappedStatement mappedStatement = new MappedStatement.Builder(configuration, "selectList", new StaticSqlSource(configuration, "select * from user"), SqlCommandType.SELECT).resultMaps(new ArrayList<ResultMap>() {
                {
                    add(rm);
                }
            }).build();
            configuration.addMappedStatement(mappedStatement);

            // 获取sqlSession管理器
            sqlSessionManager = SqlSessionManager.newInstance(new SqlSessionFactoryBuilder().build(configuration));

            // 开启sqlSession
            sqlSessionManager.startManagedSession();

            // 调用Executor执行查询操作
            List<UserEntity> userEntityList = sqlSessionManager.selectList("selectList");

            for (UserEntity user : userEntityList) {
                System.out.println(user);
            }
            //endregion
        } finally {
            if (sqlSessionManager != null) {
                sqlSessionManager.close();
            }
        }
    }

    /**
     * ProxyFactory：用于实现MyBatis的懒加载功能
     *      当开启懒加载后，MyBatis在关联查询（如 one-to-one 或 one-to-many）时创建Mapper映射结果对象后，会通过ProxyFactory创建映射结果对象的懒加载代理对象
     */
    @Test
    public void testProxyFactory() {
        // 创建ProxyFactory对象
        ProxyFactory proxyFactory = new JavassistProxyFactory();

        UserEntity user = new UserEntity().load();

        ObjectFactory objectFactory = new DefaultObjectFactory();

        // 调用ProxyFactory对象的createProxy（）方法创建代理对象
        Object proxyOrder = proxyFactory.createProxy(user
                , new ResultLoaderMap()
                , new Configuration()
                , objectFactory
                , Arrays.asList(Long.class, String.class, Date.class, String.class, String.class, String.class)
                , Arrays.asList(user.getId(), user.getName(), user.getCreateTime(), user.getPassword(), user.getPhone(), user.getNickName())
        );

        System.out.println(proxyOrder.getClass());
        System.out.println(((UserEntity) proxyOrder).getName());
        System.out.println(((UserEntity) proxyOrder).getCreateTime());
    }

}
