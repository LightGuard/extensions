/**
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.webbeans.environment.se.util;

/**
 * Reflection utilities
 * 
 * @author Pete Muir
 * 
 */
public class Reflections
{
   
   private Reflections(String name)
   {
   }
   
   public static <T> T newInstance(String className, Class<T> expectedType) throws InstantiationException, IllegalAccessException, ClassNotFoundException
   {
      return loadClass(className, expectedType).newInstance();
   }
   
   public static <T> Class<? extends T> loadClass(String className, Class<T> expectedType) throws ClassNotFoundException
   {
      if (Thread.currentThread().getContextClassLoader() != null)
      {
         return Thread.currentThread().getContextClassLoader().loadClass(className).asSubclass(expectedType);
      }
      else
      {
         return Class.forName(className).asSubclass(expectedType);
      }
   }
   
   public static ClassLoader getClassLoader()
   {
      if (Thread.currentThread().getContextClassLoader() != null)
      {
         return Thread.currentThread().getContextClassLoader();
      }
      else
      {
         return Reflections.class.getClassLoader();
      }
   }
   
}