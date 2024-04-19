package com.xisui.springbootweb.common;

import com.xisui.springbootweb.service.PersonService;
import com.xisui.springbootweb.service.ReplacementSavePerson;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.stereotype.Component;

@Component
public class PackBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    // 在这里你可以对现有的Bean对象进行修改操作
  }
  @Override
  public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
    // 在这里你可以动态的注册bean对象

    BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(ReplacementSavePerson.class).getBeanDefinition() ;
    registry.registerBeanDefinition("replacementSavePerson", beanDefinition) ;

    AbstractBeanDefinition beanDefinition2 = BeanDefinitionBuilder.rootBeanDefinition(PersonService.class).getBeanDefinition();
    ReplaceOverride replace = new ReplaceOverride("savePerson", "replacementSavePerson") ;
    replace.addTypeIdentifier("String");
    beanDefinition2.getMethodOverrides().addOverride(replace) ;
    registry.registerBeanDefinition("personService", beanDefinition2) ;
  }
}
