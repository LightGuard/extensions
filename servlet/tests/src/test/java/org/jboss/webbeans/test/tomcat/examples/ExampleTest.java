package org.jboss.webbeans.test.tomcat.examples;

import org.jboss.testharness.impl.packaging.Artifact;
import org.jboss.webbeans.test.AbstractWebBeansTest;
import org.jboss.webbeans.test.harness.ServletLifecycleContainersImpl;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Artifact
public class ExampleTest extends AbstractWebBeansTest
{
   
   @Override
   @BeforeClass
   public void beforeClass() throws Throwable
   {
      getCurrentConfiguration().setStandaloneContainers(new ServletLifecycleContainersImpl());
      super.beforeClass();
   }
   
   @Test
   public void testGameGenerator() throws Exception {
     
     new RunInDependentContext()
     {
        
        @Override
        protected void execute() throws Exception
        {
           Game game1 = getCurrentManager().getInstanceByType(Game.class);
           Game game2 = getCurrentManager().getInstanceByType(Game.class);
           assert game1!=game2;
           assert game1.getNumber()!=game2.getNumber();
           Generator gen1 = getCurrentManager().getInstanceByType(Generator.class);
           Generator gen2 = getCurrentManager().getInstanceByType(Generator.class);
           assert gen1.getRandom()!=null;
           assert gen1.getRandom()==gen2.getRandom();
        }
        
     }.run();
   }

   @Test
   public void testSentenceTranslator() throws Exception {
      
      new RunInDependentContext()
      {
         
         @Override
         protected void execute() throws Exception
         {
            TextTranslator tt1 = getCurrentManager().getInstanceByType(TextTranslator.class);
            try 
            {
               tt1.translate("hello world");
               assert false;
            }
            catch (UnsupportedOperationException uoe)
            {
               //expected
            }
         }
         
      }.run();
   }
   
}