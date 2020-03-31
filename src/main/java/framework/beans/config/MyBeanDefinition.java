package framework.beans.config;

import lombok.Data;

@Data
public class MyBeanDefinition {

    private String beanClassName;
    private boolean lazyInit = false;
    private String factoryBeanName;
    private boolean isSingleton = true;

}

