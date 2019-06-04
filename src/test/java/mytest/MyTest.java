package mytest;

import com.google.common.collect.Lists;
import io.swagger.models.auth.In;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MyTest {


    @Test
    public void stringSplit(){
        String keywords=" *<,asd fda asf 123 asd13";
        List<String> keywordsTokens = Lists.newArrayList(keywords.split("\\W+"));
        List<Integer> indexes = new ArrayList<>();
        for(String item : keywordsTokens){
            if(item.length() == 0)
            {
                indexes.add(keywordsTokens.indexOf(item));
            }
        }
        for(int i : indexes){
            keywordsTokens.remove(i);
        }
        System.out.println(keywordsTokens.size());
        System.out.println(keywordsTokens);
    }


    @Test
    public void testIntegerChange(){
        int i;
        double x = 999999999999999999995555555.00d;
        i = (int) x;
        i++;
        System.out.println(i);

    }

}
