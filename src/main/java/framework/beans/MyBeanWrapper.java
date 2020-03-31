package framework.beans;

import lombok.Getter;

@Getter
public class MyBeanWrapper {
    private Object wrappedInstance;
    private Class<?> wrappedClass;

    public MyBeanWrapper(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
        this.wrappedClass = wrappedInstance.getClass();
    }
}
