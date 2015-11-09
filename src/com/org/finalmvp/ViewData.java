package com.org.finalmvp;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 依赖注入的接口，属性定义必须是public
 * @author hld
 */
@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewData {
	int id() default 0;
}