package priv.theo.commons.util;

import java.util.regex.Pattern;

/**
 * @author silence
 * @version 1.0
 * @className StringUtils
 * @date 2018/10/04 上午11:33
 * @description String工具类
 * @program sdk
 */
public class StringUtils {
    private static final String FOLDER_SEPARATOR = "/";

    private static final Pattern INT_PATTERN = Pattern.compile("^\\d+$");

    public static boolean isEmpty(String s) {
        return null == s || s.isEmpty();
    }


    /**
     * note 如果参数只包含纯粹的whitespace字符串,将返回true
     */
    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

    /**
     * 判断给定的参数是否包含实际的字符串
     */
    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除首尾两端空格
     */
    public static String trimPreTailWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
        }
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 删除所有空格
     */
    public static String trimAllWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (!Character.isWhitespace(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 对给定的字符串首尾加上单引号
     */
    public static String quote(String str) {
        if (str == null) {
            return str;
        }
        StringBuilder builder = new StringBuilder("'");
        builder.append(str).append("'");
        return builder.toString();
    }


    /**
     * 从给定的path路径中提取出文件名 "mypath/myfile.txt" -> "myfile.txt"
     */
    public static String getFilename(String path) {
        if (path == null) {
            return null;
        }
        int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
        return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
    }

    /**
     * 判断给定的字符串是否为数字形式
     */
    public static boolean isNumber(String str) {
        if (isEmpty(str)) {
            return false;
        }
        return INT_PATTERN.matcher(str).matches();
    }

    /**
     * 自定义字符串拼接
     *
     * @param regex
     * @param parms
     * @return
     */
    public static String getStr(String regex, Object... parms) {
        StringBuilder format = new StringBuilder("%s");
        for (int i = 0; i < parms.length - 1; i++) {
            format.append(regex).append("%s");
        }
        return String.format(format.toString(), parms);
    }


    /**
     * 将字符串的首字母转为大写
     */
    public static String capitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }

        final int firstCodepoint = str.codePointAt(0);
        final int newCodePoint = Character.toTitleCase(firstCodepoint);
        if (firstCodepoint == newCodePoint) {
            // already capitalized
            return str;
        }

        // cannot be longer than the char array
        final int newCodePoints[] = new int[strLen];
        int outOffset = 0;
        // copy the first codepoint
        newCodePoints[outOffset++] = newCodePoint;
        for (int inOffset = Character.charCount(firstCodepoint); inOffset < strLen; ) {
            final int codepoint = str.codePointAt(inOffset);
            // copy the remaining ones
            newCodePoints[outOffset++] = codepoint;
            inOffset += Character.charCount(codepoint);
        }
        return new String(newCodePoints, 0, outOffset);
    }

    /**
     * 判断是否为电话号码
     * @param str
     * @return
     */
    public static boolean isPhoneNumber(String str) {
        return StringUtils.isNumber(str) && str.length() == 11;
    }

    /**
     * 去除字符串首尾出现的某个字符.
     *
     * @param source  源字符串.
     * @param element 需要去除的字符.
     * @return String.
     */
    private static String trimFirstAndLastChar(String source, char element) {
        if (isEmpty(source)) {
            return null;
        }
        boolean beginIndexFlag = true;

        boolean endIndexFlag = true;
        do {
            int beginIndex = source.indexOf(element) == 0 ? 1 : 0;
            int endIndex = source.lastIndexOf(element) + 1 == source.length() ? source.lastIndexOf(element) : source.length();
            source = source.substring(beginIndex, endIndex);
            beginIndexFlag = (source.indexOf(element) == 0);
            endIndexFlag = (source.lastIndexOf(element) + 1 == source.length());
        } while (beginIndexFlag || endIndexFlag);
        return source;
    }

}
