package com.deepcre.common.annotation;

import java.lang.annotation.*;


/**
 * @author alvis-yiang
 * @create 2022-02-11 10:12 AM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Excel {
    String value() default "";
}
