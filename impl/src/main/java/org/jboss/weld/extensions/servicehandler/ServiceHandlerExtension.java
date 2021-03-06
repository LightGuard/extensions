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
package org.jboss.weld.extensions.servicehandler;

import static org.jboss.weld.extensions.util.AnnotationInspector.getMetaAnnotation;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.jboss.weld.extensions.bean.BeanBuilder;

/**
 * This extension automatically implements interfaces and abstract classes.
 * 
 * @author Stuart Douglas
 * 
 */
class ServiceHandlerExtension implements Extension
{
   Set<Bean<?>> beans = new HashSet<Bean<?>>();
   
   <X> void processAnnotatedType(@Observes ProcessAnnotatedType<X> event, BeanManager beanManager)
   {
      ServiceHandler annotation = getMetaAnnotation(event.getAnnotatedType(), ServiceHandler.class);
      if (annotation != null)
      {
         Class<?> handlerClass = annotation.value();
         try
         {
            BeanBuilder<X> builder = new BeanBuilder<X>(beanManager);
            builder.defineBeanFromAnnotatedType(event.getAnnotatedType());
            builder.setBeanLifecycle(new ServiceHandlerBeanLifecycle(event.getAnnotatedType().getJavaClass(), handlerClass, beanManager));
            builder.setToString("Generated @ServiceHandler for [" + builder.getJavaClass() + "] with qualifiers [" + builder.getQualifiers() + "] handled by " + handlerClass);
            beans.add(builder.create());
         }
         catch (IllegalArgumentException e)
         {
            throw new RuntimeException(e);
         }
      }
   }

   void afterBeanDiscovery(@Observes AfterBeanDiscovery event)
   {
      for (Bean<?> bean : beans)
      {
         event.addBean(bean);
      }
   }
}
