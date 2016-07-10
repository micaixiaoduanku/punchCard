package remote.com.example.huangli.punchcard.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangli on 16/7/2.
 */
public class StringUtil {
    public static Integer[] parseStrToArray(String str){
        String[] str_array = str.split(",");
        List<Integer> intList = new ArrayList<>();
        for (String s : str_array){
            intList.add(Integer.parseInt(s));
        }
        return (Integer[]) intList.toArray();
    }
}
