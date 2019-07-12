package com.github.flybooter.autoconfigure.dlock;

import java.lang.annotation.*;

/**
 * 被{@link DLock}注解的方法,通过设置{@link #value()}作为锁,在被调用时会被同步,可以解决分布式并发系列的问题。
 *
 * @author feixm
 * @date 2019/3/2
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DLock {
    /**
     * 锁的名字,使用Spring Expression Language (SpEL) 表达式根据方法上的参数动态计算该值,
     * 可以多个,表示要同时获得多个锁才能进入被调用的方法,但要注意相互死锁的问题。
     * <p>内置变量:
     * <ul>
     * <li>{@code #root.classMethod} 类限定名加方法名(默认)</li>
     * <li>{@code #root.className} 类限定</li>
     * <li>{@code #root.methodName} 方法名</li>
     * </ul>
     */
    String value() ;

    int timeoutSeconds() default 3;
}
