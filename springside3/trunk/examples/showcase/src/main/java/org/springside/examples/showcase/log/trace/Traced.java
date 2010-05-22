/*
 * $HeadURL$
 * $Id$
 * Copyright (c) 2009 by Drutt, all rights reserved.
 */

package org.springside.examples.showcase.log.trace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识一个方法将通过AOP Traced.
 * 
 * @see TraceLogAspect
 * 
 * @author George
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Traced {
}
