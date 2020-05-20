package leetcode;

import java.util.HashMap;
import java.util.Map;

public class LeetCode13 {

    Map<Character,Integer> relation = new HashMap<>();
    {
        relation.put('I',1);
        relation.put('V',5);
        relation.put('X',10);
        relation.put('L',50);
        relation.put('C',100);
        relation.put('D',500);
        relation.put('M',1000);
    }

    public int romanToInt(String s) {
        int num=0;
        for (int i = 0; i < s.length(); i++) {
            if(i<s.length()-1&&relation.get(s.charAt(i))<relation.get(s.charAt(i+1)))
                num-=relation.get(s.charAt(i));
            else
                num+=relation.get(s.charAt(i));
        }
        return num;
    }
}
