package priv.theo.commons.util;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author terry
 * @date 18-5-25
 */
public class IDUtils {

    /**
     * 10
     */
    private static final int TEN = 10;

    /**
     * 100
     */
    private static final int HUNDRED = 100;

    /**
     * 上一次时间戳
     */
    private static long lastTimeMill = -1L;

    /**
     * 序列号
     */
    private static long sequence = 0L;

    /**
     * 最大序列号
     */
    private static final int MAX_SEQUENCE = 999;

    /**
     * 生成唯一Id
     *
     * @return 唯一Id
     */
    public static String nextId() {

        long currentTimeMill;

        String sequenceStr;

        synchronized (IDUtils.class) {

            currentTimeMill = currentTimeMill();

            if (currentTimeMill == lastTimeMill) {
                sequence = (sequence+1) & MAX_SEQUENCE;
                if (sequence == 0L) {
                    currentTimeMill = nextTimeMill();
                }
            } else {
                sequence = 0L;
            }

            if (sequence < TEN) {
                sequenceStr = String.format("00%d", sequence);
            } else if (sequence < HUNDRED) {
                sequenceStr = String.format("0%d", sequence);
            } else {
                sequenceStr = String.valueOf(sequence);
            }

            lastTimeMill = currentTimeMill;
        }

        int machineId = ThreadLocalRandom.current().nextInt(TEN);

        return String.format("%d%s%d", currentTimeMill, sequenceStr, machineId).substring(2);
    }

    /**
     * 当前timeMill
     *
     * @return
     */
    private static long currentTimeMill() {
        String now = LocalDateTime.now().toString();
        String replaceAll = RegexUtils.getReplaceAll(now, "[^\\d]+", "");
        return Long.valueOf(replaceAll);
    }

    /**
     * 下一个timeMill
     *
     * @return
     */
    private static long nextTimeMill() {
        long curTimeMill = currentTimeMill();
        while (curTimeMill <= lastTimeMill) {
            curTimeMill = currentTimeMill();
        }
        return curTimeMill;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 25; i++) {
            System.out.println(nextId());
        }
    }
}
