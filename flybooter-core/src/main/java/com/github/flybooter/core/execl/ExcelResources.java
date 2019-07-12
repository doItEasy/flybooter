package com.github.flybooter.core.execl;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author feixm
 */
@Target({ElementType.FIELD,ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ExcelResources {
    /**
     * 属性的标题名称
     * @return
     */
    String title() default "";
    /**
     * 在excel的顺序
     * @return
     */
    int order() default 9999;

    /**
     * 字段名
     * @return
     */
    String fieldName() default "";
}
