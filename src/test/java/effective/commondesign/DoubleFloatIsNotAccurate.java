package effective.commondesign;

public class DoubleFloatIsNotAccurate {
    /**
     * double float类型主要为了科学和工程计算设计，
     * 他们执行二进制浮点运算，这是为了广泛的数值范围上提供
     * 比较精准的快速近视计算而精心设计的。
     *
     * 它们不适合精确结算的场合，所以货币计算尤其不能用。
     * 因为用他们表示0.1 或者任何10的负数次方的都是不可能。
     *
     * 解决办法使用BidDecimal或者int long进行货币计算。
     * BigDecimal缺点
     * 慢
     * 不方便
     *
     * 可以用int 或者long 代替，通过不计量单位该小来时数据变成整数
     * 9位十进制书 ---》 int
     * 18位十进制 ---》 long
     * 大于18位 ---》 BigDecimal
     *
     *
     */

}
