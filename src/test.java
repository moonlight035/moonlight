import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    public static void main(String[] args) {
        List<String> data = new ArrayList<>();
        data.add("abc=123${ab}123");
        data.add("ab=1231");
        data.add("a=123${abc}wer");
        new test().test(3,data);
    }

    public void test(int num, List<String> data){
        Map<String,String> infoMap = new HashMap<>();
        String lastKey = data.get(num-1).substring(0,data.get(num-1).indexOf("="));
        for (int i = 0; i < num; i++) {
            String[] info = data.get(i).split("=");
            String key = info[0];
            String value = info[1];
            Pattern pattern = Pattern.compile("\\$\\{.*\\}");
            Matcher matcher = pattern.matcher(value);
            while(matcher.find()){
                String group = matcher.group();
                group = group.replaceAll("(\\$\\{)|\\}","");
                if(infoMap.get(group)!=null){
                    value = value.replaceAll("\\$\\{.*\\}",infoMap.get(group));
                }
            }
            infoMap.put(key,value);
        }
        System.out.println(infoMap.get(lastKey));
    }
}
