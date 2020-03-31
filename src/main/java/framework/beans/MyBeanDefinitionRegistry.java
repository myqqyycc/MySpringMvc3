package framework.beans;

import framework.beans.config.MyBeanDefinition;

public interface MyBeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, MyBeanDefinition beanDefinition) throws Exception;

    MyBeanDefinition getBeanDefinition(String beanName)  ;
}
