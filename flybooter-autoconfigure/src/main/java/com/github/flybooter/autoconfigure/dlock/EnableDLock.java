package com.github.flybooter.autoconfigure.dlock;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用分布式同步功能
 *
 * @author feixm
 * @date 2019/3/2
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(value = {DLockConfiguration.class})
public @interface EnableDLock {
}
