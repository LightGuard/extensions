/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.weld.extensions.defaultbean;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.spi.AfterBeanDiscovery;

/**
 * Annotation that signifies that a bean should only be registered if no other
 * instance with the same type and qualifiers is registered. The bean only has
 * the type specified in the type() attribute.
 * 
 * A default bean must be a Managed Bean.
 * 
 * IMPORTANT: Producers, Disposes and Observes on the bean class will not be
 * registered, and will not work
 * 
 * In some ways this is similar to the functionality provided by
 * {@link Alternative} however there are some important distinctions
 * <ul>
 * <li>No XML is required, if an alternative implementation is available it is
 * used automatically</li>
 * <li>The bean is registered across all modules, not on a per module basis</li>
 * </ul>
 * 
 * It is also important to note that beans registered in the
 * {@link AfterBeanDiscovery} event may not been see by this extension
 * 
 * @author Stuart Douglas
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface DefaultBean
{
   /**
    * The type of the bean. If another bean is found with this type and the same
    * qualifiers this bean will not be installed.
    * 
    * This bean will only be installed with the type specified here, not
    * 
    */
   public Class<?> type();
}
