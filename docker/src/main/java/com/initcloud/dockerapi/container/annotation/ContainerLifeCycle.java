package com.initcloud.dockerapi.container.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.initcloud.dockerapi.container.enums.ContainerLifeCycleStrategy;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface ContainerLifeCycle {
	ContainerLifeCycleStrategy strategy() default ContainerLifeCycleStrategy.CREATE;
}
