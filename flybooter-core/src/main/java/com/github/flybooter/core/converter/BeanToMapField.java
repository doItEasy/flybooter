package com.github.flybooter.core.converter;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface BeanToMapField {
    /**
     * 属性的显示名
     *
     * @return
     */
    String displayName() default "";

    /**
     * 在excel的顺序
     *
     * @return
     */
    int order() default 9999;
}
