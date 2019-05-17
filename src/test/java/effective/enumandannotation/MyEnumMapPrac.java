package effective.enumandannotation;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class MyEnumMapPrac {

    Herb[] garden;
    @Before
    public void before(){
       garden = new Herb[]{
                new Herb("a",Herb.Type.ANNUAL),
                new Herb("b",Herb.Type.ANNUAL),
                new Herb("c",Herb.Type.BIENNIAL),
                new Herb("d",Herb.Type.PERENNIAL),
                new Herb("e",Herb.Type.BIENNIAL),
                new Herb("f",Herb.Type.PERENNIAL)
        };

    }

    @Test //this is a bad practive of ordinal,数组索引不好的实践。
    public void sortOut(){
        Set<Herb>[] herbsByType = (Set<Herb>[]) new Set[Herb.Type.values().length];
        for(int i = 0; i < herbsByType.length; i++){
            herbsByType[i] = new HashSet<Herb>();
        }
        for(Herb h : garden){
            herbsByType[h.getType().ordinal()].add(h);
        }
        for (int i = 0; i < herbsByType.length ; i++){
            System.out.printf("%s: %s%n", Herb.Type.values()[i], (Set<Herb>)herbsByType[i]);

        }
    }
    /*
     * 以上的方法隐藏很多问题，因为数组不能与范型兼容，程序需要进行为受检
     * 转换，并且不能正确无误进行编译。因为数组不知道它的索引代表着什么，
     * 你必须手工标注这些索引的输出，但是这种方法最严重的问题在于，当你
     * 访问一个按照枚举组数进行索引的数组时候，使用正确的int值就是你的职责
     * int不能提供枚举类型的安全。你如果使用了错误值，程序就会悄悄完成错误的
     * 工作，幸运的话会有ArrayIndexOutOfBoundException异常
     *
     * */

    /*
     * 幸运的是，有一种更好的方法可以达到同样的效果。数组实际上充当的是从枚举到值的映射
     * 因此可能还要用到map. 更加具体的说，有一种非常快速的map实现专门用于枚举健。
     * */

    @Test // good practise
    public void sortOutByEnumMap(){//
        Map<Herb.Type, Set<Herb>> herbByType = new EnumMap<Herb.Type, Set<Herb>>(Herb.Type.class);
        for(Herb.Type t :  Herb.Type.values()){
            herbByType.put(t, new HashSet<Herb>());
        }
        for(Herb h : garden){
            herbByType.get(h.getType()).add(h);
        }
        System.out.println(herbByType);
    }

    @Test
    public void getTrans(){
        System.out.println(Phase.Transation.getTransition(Phase.GAS,Phase.LIQUID));
    }
}

class Herb{
    public static enum Type{ ANNUAL, PERENNIAL, BIENNIAL}

    private final String name;
    private final Type type;

    Herb(String name, Type type){
        //构造函数没有返回值，所以连void也不能加
        this.name = name;
        this.type = type;
    }

    public String toString(){
        return name;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }
}

/*
第33条，用enumMap代替序数索引
* 下面是个很perfect的enumMap的应用。对于维护很友好。
* 初始化阶段过度map的代码看起来有点复杂，但是还不算太糟糕。
* 当想要添加新的阶段， plasma或者离子气体。只有两个过度阶段
* 与之有关。必须给 phase添加一种新的常量，给Phase.Transition
* 添加两种新的常量。用一种新的16元素数组取代原来的9元素的版本。我们只要
* 添加常量，程序自动处理其他所有事情。
* 几乎没有出错的机会
* 清楚性，安全性，易维护性。
* 总而言之，不要用序数来索引数组，使用enumMap.
* 如果是多纬度的，使用EnumMap<...,EnumMap<..>>
* */
enum Phase{

    SOLID,GAS,LIQUID;

    enum Transation{
        MELT(SOLID,LIQUID),BOIL(LIQUID,GAS),
        FREEZE(LIQUID,SOLID),CONDENSE(GAS, LIQUID),
        SUBLIME(SOLID, GAS),DEPOSIT(GAS,SOLID);

        private Phase src;
        private Phase dst;

        Transation(Phase src, Phase dst){
            this.dst = dst;
            this.src = src;
        }

        private static final Map<Phase, Map<Phase, Transation>> m=
                new EnumMap<Phase, Map<Phase, Transation>>(Phase.class);
        static{
            for (Phase p : Phase.values())
                m.put(p,new EnumMap<Phase, Transation>(Phase.class));
            for (Transation t : Transation.values())
                m.get(t.src).put(t.dst,t);
        }
        public static Transation getTransition(Phase src, Phase dst){
            return  m.get(src).get(dst);
        }
    }
}

/*第34条：用接口模拟可伸缩的枚举。*/
