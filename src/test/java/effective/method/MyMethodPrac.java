package effective.method;

import java.math.BigInteger;

public class MyMethodPrac {
//如何处理参数和返回值，如何设计方法签名，如何为方法编写文档，
    //可用性，健壮性和灵活性

    /*
    * 绝大多数方法和构造器，对于传递给他们的参数值都会有限制，例如，索引值
    * 必须是非负数，对象的引用不能为null,我们在执行方法前就应该做参数检查
    *
    * 对于公有方法，要用javadoc的@throw标签在文档中说明违反参数数值限制时候
    * 会抛出的异常，通常为IllegalArgumentException, IndexOutOfBoundsException
    * 或者NullPointException,
    *
    */

}
class MyClass{

    /**
     * Returns a BigInteger whose value is (this mod m), this method
     * differs from the remainder method in that it always returns a
     * non-negative BigInteger
     * @param m the modulus, which must be positive
     * @return this mod m
     * @throws ArithmeticException if m is less than or equal to 0
     */

    public BigInteger mod(BigInteger m){
        if(m.signum() <= 0){
            throw new ArithmeticException("Modulus <= 0: " + m);
        }
        // do the computation
        return new BigInteger("1234");
    }

    /***
     * 对于未被导出的方法（unexported method），作为包的创建者，你可以控制这个方法将在那些
     * 情况下被调用，因此你可以，以应该确保只有将有效的参数值传递进来。因此，非公有的方法通常
     * 应该使用断言（assertion）来检查他们的参数。具体做法如下
     *
     */

    private static void sort(long a[], int offset, int length){
        assert a != null;
        assert offset >= 0 && offset <= a.length;
        assert  length >=0 && length <=a.length-offset;
        //do computation

    }

    /*
    * 对于有限参数，方法本身没有用到，却被保存起来工以后使用，检验这类参数的有效性，尤为重要，
    * 构造器的和普通方法的参数有效性检查很重要，可以避免构造出的对象违反了这个类的约束条件，可以
    * 简化调试工作。
    * */
}

