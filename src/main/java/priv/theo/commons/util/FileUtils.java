package priv.theo.commons.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author huangsuixin
 * @version 1.0
 * @className FileUtils
 * @date 2019/2/28 15:08
 * @description 文件工具
 * @program holder-saas-tools-store
 */
public class FileUtils {
    public static List<String> readEnterpriseGuidFromText(File file) {
        List<String> result = new ArrayList<>();
        FileReader fileReader = null;
        BufferedReader reader = null;
        try {
            fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String readLine;
            while ((readLine = reader.readLine()) != null) {
                result.add(readLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            Utils.close(reader, fileReader);
        }
        return result;
    }

}
