package effective.commondesign;

public class CommonDesignTest {
    /*
    * 本章节主要讨论java语言的具体细节，讨论局部变量的处理
    * 控制结构、类库用法、以及两种不是语言本身提供的机制（
    * reflection natvie method,反射机制和本体方法的用法）
    * 最后就是优化和命名习惯。
    * */
    /*第45条：将局部变量的作用域最小化*/

    /*较早的程序设计语言，例如c语言，要求局部变量必须在一个代码块的开头
    * 进行声明，出于习惯有些程序员还是继续这样做，这几习惯应该改正，再次提醒
    * java允许你在任何出现语句的地方声明。*/

    /*
    * 第46条，for-each 循环优先于传统的for 循环
    * 任何实现iterator 接口的对象都可以用for each
    * 1. 简洁
    * 2。 预防bug
    *
    *有三种情况不能用到for-each
    * 1. 过滤
    * 2。转换
    * 3。平行迭代
    * */


}
