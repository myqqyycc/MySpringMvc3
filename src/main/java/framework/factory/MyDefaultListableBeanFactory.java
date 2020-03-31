package framework.factory;

import framework.beans.MyBeanDefinitionRegistry;
import framework.beans.config.MyBeanDefinition;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyDefaultListableBeanFactory implements MyBeanDefinitionRegistry {
    @Getter
    private final Map<String, MyBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    @Override
    public void registerBeanDefinition(String beanName, MyBeanDefinition beanDefinition) throws Exception {
        if (beanDefinitionMap.containsKey(beanName)) {
            throw new Exception("已注册");
        }
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public MyBeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitionMap.get(beanName);
    }
}
