package framework.context;

import framework.annotations.MyAutoWired;
import framework.beans.MyBeanFactory;
import framework.beans.MyBeanWrapper;
import framework.beans.config.MyBeanDefinition;
import framework.beans.support.MyBeanDefinitionReader;
import framework.factory.MyDefaultListableBeanFactory;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MyApplicationContext extends MyDefaultListableBeanFactory implements MyBeanFactory {
    private String[] configResources;
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    public MyApplicationContext(String... configResources) {
        this.configResources = configResources;
        refresh();
    }

    private void refresh() {
        MyBeanDefinitionReader reader = new MyBeanDefinitionReader(configResources, this);
        reader.loadBeanDefinitions();

        doAutoWired();
    }

    private void doAutoWired() {
        for (Map.Entry<String, MyBeanDefinition> beanDefinition : super.getBeanDefinitionMap().entrySet()) {
            if (beanDefinition.getValue().isLazyInit()) {
                continue;
            }
            getBean(beanDefinition.getKey());
        }

    }

    @Override
    public Object getBean(String name) {
        Object bean = singletonObjects.get(name);

        if (bean == null) {
            MyBeanWrapper myBeanWrapper = instantiateBean(name, getBeanDefinition(name));
            populateBean(name, getBeanDefinition(name), myBeanWrapper);
            bean=myBeanWrapper.getWrappedInstance();
        }

        return bean;
    }

    private void populateBean(String beanName, MyBeanDefinition myBeanDefinition, MyBeanWrapper myBeanWrapper) {
        Object bean = myBeanWrapper.getWrappedInstance();
        for (Field field : myBeanWrapper.getWrappedClass().getDeclaredFields()) {
            MyAutoWired annotation = field.getAnnotation(MyAutoWired.class);
            if (annotation == null) {
                continue;
            }
            field.setAccessible(true);

            String autoWiredName = "".equals(annotation.value()) ? toLowerFirstCase(field.getType().getSimpleName()) : annotation.value();
            Object object = getBean(autoWiredName);

            //如果配置了beanName又 没有找到，则报错！跳过
            if (object == null) {
                System.out.println("未找到指定的beanName：" + autoWiredName);
                continue;
            }
            try {
                field.setAccessible(true);
                field.set(bean, object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private MyBeanWrapper instantiateBean(String beanName, MyBeanDefinition myBeanDefinition) {
        Object object = singletonObjects.get(beanName);
        if (object == null) {
            try {
                object = Class.forName(myBeanDefinition.getBeanClassName()).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("beanName:{},object:{}", beanName, object);
            singletonObjects.put(beanName, object);
            singletonObjects.put(myBeanDefinition.getFactoryBeanName(), object);
        }
        return new MyBeanWrapper(object);
    }

    private String toLowerFirstCase(String simpleName) {
        byte[] bytes = simpleName.getBytes();

        if ('A' <= bytes[0] && bytes[0] <= 'Z') {
            bytes[0] += 32;
            return new String(bytes);
        }
        return simpleName;
    }

}
