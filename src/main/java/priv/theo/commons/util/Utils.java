package priv.theo.commons.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.holder.saas.tools.store.business.manage.constant.Constant.*;

/**
 * @author huangsuixin
 * @version 1.0
 * @className Utils
 * @date 2019/2/27 11:18
 * @description 工具
 * @program holder-saas-tools-store
 */
@Slf4j
public class Utils {
    public static Integer byte2Integer(Byte b) {
        if (null == b) {
            return null;
        }
        return Integer.valueOf(b);
    }

    public static BigDecimal integer2BigDecimal(Integer integer) {
        if (null == integer) {
            return null;
        }
        return BigDecimal.valueOf(integer);
    }

    /**
     * 下划线命名转换为驼峰命名
     *
     * @param underline 下划线命名
     * @return 驼峰命名
     */
    public static String underline2Hump(String underline) {
        StringBuilder result = new StringBuilder();
        String[] a = underline.split("_");
        for (String s : a) {
            if (!underline.contains("_")) {
                result.append(s);
                continue;
            }
            if (result.length() == 0) {
                result.append(s.toLowerCase());
            } else {
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 驼峰命名转换为下划线命名
     *
     * @param hump 驼峰命名
     * @return 下划线命名
     */
    public static String hump2Underline(String hump) {
        StringBuilder sb = new StringBuilder(hump);
        //定位
        int temp = 0;
        if (!hump.contains("_")) {
            for (int i = 0; i < hump.length(); i++) {
                if (Character.isUpperCase(hump.charAt(i))) {
                    sb.insert(i + temp, "_");
                    temp += 1;
                }
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 获取get方法名
     *
     * @param field 属性
     * @return get方法
     */
    public static String getMethodName(Field field) {

        String name = field.getName();
        return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    /**
     * 过滤掉指定属性名的属性
     *
     * @param clazz           指定的class
     * @param filterFiledName 需要过滤的属性名
     * @return 过滤后的属性数组
     */
    public static Field[] filterFiled(Class clazz, String[] filterFiledName) {

        Field[] declaredFields = clazz.getDeclaredFields();
        List<Field> fieldList = new ArrayList<>(declaredFields.length - filterFiledName.length);
        for (Field declaredField : declaredFields) {
            // 过滤掉serialVersionUID属性 和 id字段
            boolean flag;
            int bound = filterFiledName.length;
            flag = IntStream.range(0, bound).anyMatch(i -> filterFiledName[i].equals(declaredField.getName()));
            if (flag) {
                continue;
            }
            fieldList.add(declaredField);
        }
        return fieldList.toArray(new Field[fieldList.size()]);
    }

    /**
     * 关闭连接
     *
     * @param closeables closeables
     */
    public static void close(AutoCloseable... closeables) {
        for (AutoCloseable closeable : closeables) {
            if (null != closeable) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    log.error("关闭连接失败！", e);
                }
            }
        }
    }



    /**
     * 获取trading数据的数据库名
     *
     * @param enterpriseGuid 企业guid
     * @return dish数据库全名
     */
    public static String getTradingDatabaseName(String enterpriseGuid) {
        return TRADING_DATABASE_PREFIX + enterpriseGuid + DATABASE_SUFFIX;
    }

    /**
     * 获取business_manage数据的数据库名
     *
     * @param enterpriseGuid 企业guid
     * @return item数据库全名
     */
    public static String getBusinessManageDatabaseName(String enterpriseGuid) {
        return BUSINESS_MANAGE_DATABASE_PREFIX + enterpriseGuid + DATABASE_SUFFIX;
    }
}
