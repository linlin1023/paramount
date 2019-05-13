package effective;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EffeTest {
    public void swap(List<?> list, int i, int j ){
        //list.set(i, list.set(j, list.get(i)));
        //上面这个语句是不能编译的
        //当list的类型为List<?> 你不能把null之外的任何值
        //放到List<?>中去
        swapHelper(list, i, j);
    }
    private <E> void  swapHelper(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }
}
//PECS rules
interface GetMax{
    public  <T extends Comparable<T>> T max(List<T> list);
    /*
    * 没有通配符的情况下，入参的类型和返回值的类型是必须一样的。
    * 下面这种是带有通配符的类型，入参和返回的类型可以是子类父类延伸出去的类型*/

    public  <T extends Comparable<? super T>>  T maxAnother(List<? extends T>  list );

    /*
    * 从上一个到下一个声明用了PECS 规则转换了两次，最直接是运用参数list,它产生T实例，因此将类型从 List<T>
    * 改成List<? extends T>, 更加灵活的是运用到类型参数T，这是我第一次遇到将通配符运用到类型参数，最初T
    * 被用来指定扩展Comparable<T>, 但是T的compareable消费了实例，并且产生顺序关系的整数值。因此，参数话类型
    * comparable<T>被有限制通配符Comparable<? super T> 取代。comparable始终是消费者，因此使用的时候始终是
    * Comparable<? super T> 优先于 Comparable<T> */
}
class GetMaxImpl implements GetMax{
    @Override
    public <T extends Comparable<T>> T max(List<T> list) {
        return null;
    }
    @Override
    public <T extends Comparable<? super T>> T maxAnother(List<? extends T> list) {
        //Iterator<T> iterator = list.iterator();
        //这一条是不会通过编译的。
        // 因为list不是List<T>因此它的iterator方法不会返回
        //iterator<T>. 它返回的T的某个子类型的一个iterator，
        //因此我们用它代替iterator声明，它使用了一个有限制的通配符类型
        Iterator<? extends T> i = list.iterator();
        T result = i.next();
        while(i.hasNext()){
            T t = i.next();
            if( t.compareTo(result) > 0 ){
                result = t;
            }
        }
        return result;
    }
    /*
    * 如果类型参数只在方法声明中出现一次，就可以用通配符渠道，
    * 如果是无限制的类型参数就用无限制的通配符，
    * 如果是有限制的类型参数就用有限制的通配符。*/

    /*
    * 1.5 引入了两个新的引用类型家族，一种新的类型称做枚举类型。
    * 一种新的接口叫做注解类型，下面讨论两种新的类型的最佳实践。
    * 在枚举类型没有出现的时候，我们习惯用int enum parrern
    * 类型，static final int类型常量表示枚举。
    * 缺点：需要自己弄命名空间，没有可靠方法遍历正确枚举组的常
    * 量和知道其大小，每次修改必须重新编译，没法翻译成可打印字符串。
    *
    * 或者string enum pattern,
    * 缺点：性能不好，因为依赖于字符串的比较操作，可能会导致初级
    * 用户把字符串常量硬编码到客户端代码中去
    * 所以enum出现了
    * */

    enum Apple {FUJI, PIPPIN, GRANNY_SMITH};
    enum Orange {NAVEL, TEMPLE, BLOOD};
    /*
    * java enum本质上是int值
    * java 枚举类型的思想：
    * 通过公有的静态的final的域为每个枚举常量导出实例类。因为没有
    * 可以访问的构造器，枚举类型是正真的final.因为客户端既不能
    * 创建枚举类型的实例，也不能对它进行扩展，因此很可能没有实例，
    * 而只有声明过的枚举常量。换句话说，枚举是实例受控的。他们是
    * 单例的泛型化。本质上是单元素枚举。
    * */
}