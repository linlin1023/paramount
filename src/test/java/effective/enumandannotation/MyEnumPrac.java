package effective.enumandannotation;

import org.junit.Test;

import java.math.BigDecimal;

public class MyEnumPrac {

@Test
public void getStringOfG(){
    Double G = 6.67300E-11;
    System.out.println(new BigDecimal(G).toPlainString());
}
/*有时候在枚举中覆盖toString方法很有效，可以测试一下下面覆盖了toString的枚举*/
@Test
public void testOperationString(){
    double x = Double.parseDouble("2.34");
    double y = Double.parseDouble("3.21");
    for (OperationWithString op : OperationWithString.values()){
        System.out.printf("%f %s %f = %f %n", x,op,y,op.apply(x,y));
    }
}

}
/*
* 枚举类型允许添加任意的方法和域，并实现任意接口，
* 他们提供所有Object 方法的高级实现，枚举实现了Comparable
* 和Serializable. 并针对枚举类型的可任意改变性设计了序列化
* 方法。
*
* */
/*特定于常量的枚举实现，constant-specific method implementation*/
enum Operation{
    PLUS { double apply(double x, double y){ return x+y; } },
    MINUX { double apply(double x, double y){ return x-y; } },
    TIMES { double apply(double x, double y){ return x*y; } },
    DIVIDE { double apply(double x, double y){ return x/y; } };
    abstract double apply(double x, double y);
}
/*特定于常量的方法实现可以与特定与常量的数据结合起来，例如：下面 Operation覆盖
* 了toString来返回通常与该操作关联的符号。*/
enum OperationWithString{
    PLUS("+") { double apply(double x, double y){ return x+y; } },
    MINUX("-") { double apply(double x, double y){ return x-y; } },
    TIMES("*") { double apply(double x, double y){ return x*y; } },
    DIVIDE("/") { double apply(double x, double y){ return x/y; } };
    private final String symbol;
    OperationWithString(String symbol){
        this.symbol = symbol;
    }
    @Override public String toString(){return symbol;}
    abstract double apply(double x, double y);
}

/*下面这段代码很简洁，从维护的角度看非常危险，假设添加一个新的元素到
* 这个枚举中去，或许是表示假期天数的特殊值，但是忘记给switch语句添加
* 相应的case, 程序依然可以编译。但是pay方法回将假期的工资的计算成与正常
* 工作日相同。*/
enum PayrollDay{
    MONDAY, TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY;
    private static final int HOURS_PER_SHIFT = 8;
    double pay(double hoursWorked, double payRate){
        double basePay = hoursWorked * payRate;
        double overtimePay;
        switch(this){  //switch on its value to share code,
            case SATURDAY: case  SUNDAY:
                overtimePay = hoursWorked * payRate /2;
                break;
            default:
                overtimePay = hoursWorked <= HOURS_PER_SHIFT ? 0 :
                        (hoursWorked - HOURS_PER_SHIFT)*payRate/2;
                break;
        }
        return overtimePay + basePay;
    }
}
/*对于上面维护危险的代码解决方法： 特定于常量的方法实现来安全的执行工资计算。但是可能必须
* 重复的计算每个常量的加班工资。或者将计算移动到两个辅助方法中去，（一个用来计算工作日
* 一个用来计算双休日。）并从每个常量调用相应的辅助方法。 但是任何一种解决方法都产生了不少
* 的模版代码，结果降低了可读性，并且增加了出错的几率*/
/*从正真想要解决的问题出发。我们想要的是每当添加一个枚举常量，就是强制选择一种加班的报酬
策略。
此时策略枚举可以发挥作用，虽然简洁性降低，但是灵活且安全
* */
enum PayrollDayNewOne{
    MONDAY(PayType.WEEKDAY),TUESDAY(PayType.WEEKDAY),
    WEDNESDAY(PayType.WEEKDAY),THURSDAY(PayType.WEEKDAY),
    FRIDAY(PayType.WEEKDAY),SATURDAY(PayType.WEEKEND),
    SUNDAY(PayType.WEEKEND);
    private final PayType payType;
    PayrollDayNewOne(PayType payType){
        this.payType = payType;
    }
    double pay(double workedHours, double payRate){
       return  this.payType.pay(workedHours,payRate);
    }

    private enum PayType{
        WEEKDAY {
                double overtimePay(double workedHours, double payRate){
                    return workedHours <= HOURS_PER_SHIFT ? 0 :
                            (workedHours - HOURS_PER_SHIFT)*payRate/2;
                }
            },
        WEEKEND{
            double overtimePay(double workedHours, double payRate){
                return workedHours*payRate/2;
            }
        };
        abstract double overtimePay(double workedHours, double payRate);
        private static final int HOURS_PER_SHIFT = 8;
        double pay(double workedHours, double payRate){
            double basePay = workedHours * payRate;
            return basePay + overtimePay(workedHours,payRate);
        }
    }
}
/*结论，我们可以使用策略枚举来代替switch来实现在枚举中特定于常量的行为。
如果多个枚举常量同时共享相同的行为，则考虑策略枚举。
* */
/*第31条，用实例域代替序数。 许多枚举天生就与一个单独的int值关联，所有的枚举都有一个ordinal方法，
* 它返回每个枚举常量在类型中的数字未知，*/
enum Ensemble{
    SOLO, DUET, TRIO, QUARTET, QUINTET,
    SEXTET, SEPTET, OCTET, NONET, DECTET;
    public int numberOfMusicians() {return this.ordinal() + 1;}
}
/*上面的枚举是维护的噩梦。当常量重新排序，当出现双四重奏与八重奏冲突，
还有三四重奏的12位演奏家导致11这个数字缺少常量匹配。
解决办法： 永远不要用枚举的序列值导出他们的关系。而是用一个实例域来保存。
*/
enum EnsembleNew{
    SOLO(1), DUET(2), TRIO(3), QUARTET(4), QUINTET(5),
    SEXTET(6), SEPTET(7), OCTET(8),DOUBLE_QUARTET(8),
    NONET(9), DECTET(10), TRIPLE_QUARTET(12);
    private final int numberOfMusicians;
    EnsembleNew(int num){ this.numberOfMusicians = num;}
    public int numberOfMusicians() {return numberOfMusicians;}
}
/* 最好完全避开使用ordinal, 因为他是专门为 EnumSet, EnumMap 设计的。*/

/*第31条： 用enumSet代替位域，*/

