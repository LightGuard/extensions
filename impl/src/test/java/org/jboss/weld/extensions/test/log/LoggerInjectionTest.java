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

package org.jboss.weld.extensions.test.log;

import static org.jboss.weld.extensions.test.util.Deployments.baseDeployment;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.impl.LastMessageLoggerFactory;

/**
 * All the tests related to the @Logger binding type and injection.
 * 
 * @author David Allen
 */
@RunWith(Arquillian.class)
public class LoggerInjectionTest
{
   @Inject
   Sparrow sparrow;

   @Inject
   Finch finch;

   @Deployment
   public static Archive<?> deployment()
   {
      return baseDeployment().addPackage(LoggerInjectionTest.class.getPackage()).addPackage(LastMessageLoggerFactory.class.getPackage());
   }

   @Test
   public void testBasicLogInjection()
   {
      LastMessageLoggerFactory.INSTANCE.getLogger("").reset();
      sparrow.generateLogMessage();
      assert LastMessageLoggerFactory.INSTANCE.getLogger("").getLastMessage() != null;
      assert LastMessageLoggerFactory.INSTANCE.getLogger("").getLastMessage().equals("Sparrow");
   }

   @Test
   public void testCategorySpecifiedLogger()
   {
      LastMessageLoggerFactory.INSTANCE.getLogger("").reset();
      finch.generateLogMessage();
      assert LastMessageLoggerFactory.INSTANCE.getLogger("").getLastMessage() != null;
      assert LastMessageLoggerFactory.INSTANCE.getLogger("").getLastMessage().equals("Finch");
   }
}
