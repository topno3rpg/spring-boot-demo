import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Admin on 2016/10/28.
 */
public class Test {

    public static void main(String[] args) {
        File file = new File("G:\\tmp\\ip.txt");
        BufferedReader reader = null;
        Map<String, Set<String>> map = new HashMap<String, Set<String>>();
        Set<String> svSet = new HashSet<String>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                String[] strArr = tempString.split(",");
                String sv = strArr[0];
                String ip = strArr[1];
                svSet.add(sv);
                if (map.containsKey(ip)) {
                    Set set = map.get(ip);
                    set.add(sv);
                } else {
                    Set<String> set = new HashSet<String>();
                    set.add(sv);
                    map.put(ip, set);
                }
                line++;
            }
            System.out.println("服务数：" + svSet.size());
            System.out.println("服务器数：" + map.size());
            Set<String> keys = map.keySet();
            for (String key : keys) {
                System.out.println(key);
                Set<String> svs = map.get(key);
                for (String sv : svs) {
                    System.out.println("      " + sv);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e1) {
                }
            }
        }
    }

}
