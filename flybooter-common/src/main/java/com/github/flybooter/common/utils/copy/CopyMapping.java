package com.github.flybooter.common.utils.copy;

import java.lang.annotation.*;

/**
 * Created by feixm
 */
@Target(value = {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CopyMapping {

    String alias() default "";

}
