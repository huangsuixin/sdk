package priv.theo.commons.util;

import java.util.Random;

/**
 * @author silence
 * @version 1.0
 * @className IdGenerator
 * @date 2018/10/05 下午5:09
 * @description id生成器
 * @program sdk
 */
public class IdGenerator {

    /**
     * 时间戳id生成
     * @return 当前系统时间戳(13位)
     */
    public static Long nextTimestampId() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        Random random = new Random();
        int i = random.nextInt(9999);
        System.out.println(i);

        String s = String.valueOf(i);
        int i1 = s.hashCode();
        System.out.println(i1);
        Long aLong = IdGenerator.nextTimestampId();

        System.out.println(aLong);

    }
}
