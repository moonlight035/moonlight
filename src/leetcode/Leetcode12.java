package leetcode;

import java.util.HashMap;
import java.util.Map;

public class Leetcode12 {

    Map<Integer,Character> relation = new HashMap<>();
    int[] values = {1,5,10,50,100,500,1000};
    {
        relation.put(1,'I');
        relation.put(5,'V');
        relation.put(10,'X');
        relation.put(50,'L');
        relation.put(100,'C');
        relation.put(500,'D');
        relation.put(1000,'M');
    }

    public String intToRoman(int num) {
        int[] result = new int[7];
        for(int i=6 ; i>=0 ; i-- ) {
            result[i] = num/values[i];
            num = num%values[i];
        }
        StringBuilder s = new StringBuilder();
        for(int i=0 ; i<7 ; i++){
            if(result[i]==4){
                if(result[i+1]!=0){
                    s.append(relation.get(values[i+2])+""+relation.get(values[i]));
                }else{
                    s.append(relation.get(values[i+1])+""+relation.get(values[i]));
                }
                i++;
            }else{
                for(int j=0 ; j<result[i] ; j++)
                    s.append(relation.get(values[i]));
            }
        }
        StringBuilder shift = new StringBuilder();
        for(int i=s.length()-1 ; i>=0 ; i--)
            shift.append(s.charAt(i));
        return shift.toString();
    }
}
