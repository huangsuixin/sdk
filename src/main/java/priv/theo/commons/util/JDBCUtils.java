package priv.theo.commons.util;

import org.apache.commons.collections.CollectionUtils;
import priv.theo.commons.exception.ConnectionException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static priv.theo.commons.util.Utils.*;

/**
 * @author huangsuixin
 * @version 1.0
 * @className JDBCUtils
 * @date 2019/2/25 16:53
 * @description JDBC工具
 * @program holder-saas-store-data-migration
 */
public class JDBCUtils {
    /** 驱动 */
    private static final String DRIVER_NAME = "";
    /** MYSQL 连接地址和端口 */
    private static final String MYSQL_HOST_PORT = "";

    static {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("找不到驱动程序类，加载驱动失败！", e);
        }
    }

    /**
     * 重载方法
     *
     * @param database       数据库
     * @param enterpriseGuid 企业guid
     * @return connection
     */
    public static Connection getConnection(String database, String enterpriseGuid) {
        return getConnection(database, enterpriseGuid, enterpriseGuid);
    }

    /**
     * 获取连接
     *
     * @param database 数据库
     * @param username 用户名
     * @param password password
     * @return connection
     */
    public static Connection getConnection(String database, String username, String password) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(MYSQL_HOST_PORT + database + "?useUnicode=true&characterEncoding=utf-8&serverTimezone=CTT&useSSL=false", username, password);
        } catch (SQLException e) {
            throw new ConnectionException("数据库连接失败！" + e.getMessage(), e);
        }
        return connection;
    }

    public static Connection getConnectionByHost(String mysqlHostPort, String database, String enterpriseGuid) {
        return getConnectionByHost(mysqlHostPort, database, enterpriseGuid, enterpriseGuid);
    }

    /**
     * 获取连接
     *
     * @param mysqlHostPort mysql的host和port，eg：localhost:3306
     * @param database      数据库
     * @param username      用户名
     * @param password      password
     * @return connection
     */
    public static Connection getConnectionByHost(String mysqlHostPort, String database, String username, String password) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(mysqlHostPort + database + "?useUnicode=true&characterEncoding=utf-8&serverTimezone=CTT&useSSL=false",
                    username,
                    password);
        } catch (SQLException e) {
            throw new RuntimeException("数据库连接失败!" + e.getMessage(), e);
        }
        return connection;
    }

    /**
     * 获取所有表名
     *
     * @param connection 连接
     * @param database   数据库
     * @return 库中所有表名集合
     */
    public static List<String> getTableNames(Connection connection, String database) {
        DatabaseMetaData metaData = null;
        if (null == connection) {
            throw new RuntimeException("connection is null");
        }
        List<String> result = new ArrayList<>();
        ResultSet tables = null;
        try {
            metaData = connection.getMetaData();
            tables = metaData.getTables(database, "%", "%", new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                result.add(tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            close(tables, connection);
        }

        return result;
    }

    /**
     * 获取表的列名
     *
     * @param connection 连接
     * @param database   数据库
     * @param table      表
     * @return 列名集合
     */
    public static List<String> getColumnNames(Connection connection, String database, String table) {
        DatabaseMetaData metaData = null;
        if (null == connection) {
            throw new RuntimeException("connection is null");
        }
        List<String> result = new ArrayList<>();
        ResultSet columns = null;
        try {
            metaData = connection.getMetaData();
            columns = metaData.getColumns(database, "%", table, null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                result.add(columnName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            close(columns, connection);
        }
        return result;
    }


    /**
     * 将rs结果转换成对象列表
     *
     * @param rs    jdbc结果集
     * @param clazz 对象的映射类
     * @return 封装了对象的结果列表
     */
    public static List map2Entity(ResultSet rs, Class clazz) throws SQLException {
        //结果集的元素对象
        ResultSetMetaData rsmd = null;
        String[] colNames = new String[0];
        rsmd = rs.getMetaData();

        //获取结果集的元素个数
        int count = rsmd.getColumnCount();
        colNames = new String[count];
        for (int i = 1; i <= count; i++) {
            colNames[i - 1] = rsmd.getColumnName(i);
        }

        //返回结果的列表集合
        List list = new ArrayList();
        //业务对象的属性数组
        Field[] fields = clazz.getDeclaredFields();
        // 对每一条记录进行操作

        while (rs.next()) {
            // 构造业务对象实体
            Object obj = null;
            try {
                obj = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                // log.info(e.getMessage(), e);
                System.out.println(e.getMessage());
                continue;
            }
            // 寻找该列对应的对象属性
            for (Field field : fields) {
                Object value = null;

                String colName = null;
                for (String colName1 : colNames) {
                    colName = colName1;
                    if (field.getName().equals(underline2Hump(colName))) {
                        break;
                    }
                    colName = null;
                }
                if (null == colName) {
                    continue;
                }

                // 如果类型是String
                if ("class java.lang.String".equals(field.getGenericType().toString())) {
                    value = rs.getString(colName);
                }
                if (field.getGenericType().toString().equals("class java.lang.Byte")) {
                    value = rs.getByte(colName);
                }
                // 如果类型是Long
                if (field.getGenericType().toString().equals("class java.lang.Long")) {

                    value = rs.getLong(colName);
                }

                // 如果类型是Integer
                if (field.getGenericType().toString().equals("class java.lang.Integer")) {
                    value = rs.getInt(colName);
                }

                // 如果类型是Double
                if (field.getGenericType().toString().equals("class java.lang.Double")) {
                    value = rs.getDouble(colName);
                }

                // 如果类型是Boolean 是封装类
                if (field.getGenericType().toString().equals("class java.lang.Boolean")) {
                    value = rs.getDouble(colName);
                }
                // 如果类型是Date
                if (field.getGenericType().toString().equals("class java.util.Date")) {
                    value = rs.getDate(colName);
                }
                // 如果类型是Short
                if (field.getGenericType().toString().equals("class java.lang.Short")) {
                    value = rs.getShort(colName);
                }
                // 如果类型是BigDecimal
                if (field.getGenericType().toString().equals("class java.math.BigDecimal")) {
                    value = rs.getBigDecimal(colName);
                }
                // 如果类型是Timestamp
                if (field.getGenericType().toString().equals("class java.sql.Timestamp")) {
                    value = rs.getTimestamp(colName).toLocalDateTime();
                }

                if (field.getGenericType().toString().equals("class java.time.LocalDateTime")) {
                    value = rs.getTimestamp(colName).toLocalDateTime();
                }


                //如果匹配进行赋值
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                try {
                    field.set(obj, value);
                } catch (IllegalAccessException e) {
                    // log.info(e.getMessage(), e);
                    System.out.println(e.getMessage());
                }
                field.setAccessible(flag);
            }
            list.add(obj);
        }
        return list;
    }

    /**
     * 执行查询sql
     *
     * @param database       数据库
     * @param enterpriseGuid 企业guid
     * @param sql            sql
     * @param clazz          返回封装的类
     * @return list
     */
    public static List executeQuerySql(String database, String enterpriseGuid, String sql, Class clazz) {
        Connection connection = JDBCUtils.getConnection(database, enterpriseGuid);
        List result = Collections.emptyList();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            result = JDBCUtils.map2Entity(resultSet, clazz);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
        return result;
    }

    /**
     * 批量插入
     *
     * @param database       数据库名称
     * @param enterpriseGuid 企业guid
     * @param tableName      表名
     * @param entityList     数据
     * @param entityClazz    数据class
     * @return 插入的行数
     */
    public static int batchInsert(String database, String enterpriseGuid, String tableName, List entityList, Class entityClazz) {
        int result = 0;

        Connection connection = JDBCUtils.getConnection(database, enterpriseGuid);
        PreparedStatement preparedStatement = null;

        StringBuilder sql = new StringBuilder();

        // 要插入的列名
        StringBuilder columnSql = new StringBuilder();
        // 要插入的字段值，其实就是？
        StringBuilder unknowMarkSql = new StringBuilder();

        Field[] fields = filterFiled(entityClazz, new String[]{"serialVersionUID", "id"});

        Method[] methods = new Method[fields.length];
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            columnSql.append(i == 0 ? "" : ",");
            columnSql.append(hump2Underline(field.getName()));

            unknowMarkSql.append(i == 0 ? "" : ",");
            unknowMarkSql.append("?");
            try {
                methods[i] = entityClazz.getDeclaredMethod(getMethodName(field));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        sql.append("insert into ");
        sql.append(tableName);
        sql.append(" (");
        sql.append(columnSql);
        sql.append(") VALUES(");
        sql.append(unknowMarkSql);
        sql.append(")");

        try {
            // 预编译
            preparedStatement = connection.prepareStatement(sql.toString());
            connection.setAutoCommit(false);

            for (int i = 0; i < entityList.size(); i++) {
                for (int j = 0; j < fields.length; j++) {
                    try {
                        Object invoke = methods[j].invoke(entityList.get(i));
                        preparedStatement.setObject(j + 1, invoke);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e.getMessage(), e);
                    } finally {
                        close(preparedStatement, connection);
                    }
                }
                preparedStatement.addBatch();

                // 每200条提交一次
                if ((i != 0 && i % 200 == 0) || i == entityList.size() - 1) {
                    int[] ints = preparedStatement.executeBatch();
                    connection.commit();
                    preparedStatement.clearBatch();
                    result += ints.length;
                }
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            close(preparedStatement, connection);
        }
    }

    /**
     * 批量插入不为null的属性值
     *
     * @param database       数据库名称
     * @param enterpriseGuid 企业guid
     * @param tableName      表名
     * @param entityList     数据
     * @param entityClazz    数据class
     * @return 插入的行数
     */
    public static int batchInsertSelective(String mysqlHost, String database, String enterpriseGuid, String tableName, List entityList, Class entityClazz) {
        if (CollectionUtils.isEmpty(entityList)) {
            return 0;
        }
        AtomicInteger result = new AtomicInteger();

        Connection connection = JDBCUtils.getConnectionByHost(mysqlHost, database, enterpriseGuid);
        Statement statement = null;
        // 要插入的列名
        StringBuilder columnSql = new StringBuilder();
        StringBuilder params = new StringBuilder();
        Field[] fields = filterFiled(entityClazz, new String[]{"serialVersionUID", "id"});
        StringBuilder sql = new StringBuilder();
        try {
            statement = connection.createStatement();
            connection.setAutoCommit(false);
            for (int i = 0; i < entityList.size(); i++) {
                Object o = entityList.get(i);

                for (int j = 0; j < fields.length; j++) {
                    Field field = fields[j];
                    try {
                        Method declaredMethod = entityClazz.getDeclaredMethod(getMethodName(field));
                        Object invoke = declaredMethod.invoke(o);
                        if (null == invoke) {
                            continue;
                        }

                        columnSql.append("`").append(hump2Underline(field.getName())).append("`");
                        columnSql.append(",");

                        if (invoke instanceof String) {
                            params.append("\'").append(((String) invoke).replace("\'", "")).append("\'");
                        } else {
                            params.append("\'").append(invoke).append("\'");
                        }
                        params.append(",");
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

                sql.append("insert into ");
                sql.append(tableName);
                sql.append(" (");
                sql.append(columnSql.substring(0, columnSql.length() - 1));
                sql.append(") VALUES(");
                sql.append(params.substring(0, params.length() - 1));
                sql.append(");");

                statement.addBatch(sql.toString());
                // log.debug("添加sql:{}", sql.toString());
                System.out.println("添加sql:" + sql.toString());

                sql.delete(0, sql.length());
                columnSql.delete(0, columnSql.length());
                params.delete(0, params.length());
                // 每200条提交一次
                if ((i != 0 && i % 200 == 0) || i == entityList.size() - 1) {
                    int[] ints = statement.executeBatch();
                    connection.commit();
                    statement.clearBatch();
                    result.addAndGet(ints.length);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            close(statement, connection);
        }
        return result.get();
    }


}
