package framework.webmvc;

import lombok.Data;

import java.lang.reflect.Method;

@Data
public class MyHandlerMapping {
    private Object object;
    private Method method;
    private String url;
}
