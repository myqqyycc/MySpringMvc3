package framework.webmvc;

import framework.annotations.MyController;
import framework.context.MyApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyDispatcherServlet extends HttpServlet {
    private MyApplicationContext context = null;
    private List<MyHandlerMapping> handlerMappings = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        context = new MyApplicationContext(config.getInitParameter("contextConfigLocation"));
        initStrategies(context);

    }

    private void initStrategies(MyApplicationContext context) {
        //多文件上传的组件
        initMultipartResolver(context);
        //初始化本地语言环境
        initLocaleResolver(context);
        //初始化模板处理器
        initThemeResolver(context);
        //handlerMapping
        initHandlerMappings(context);
        //初始化参数适配器
        initHandlerAdapters(context);
        //初始化异常拦截器
        initHandlerExceptionResolvers(context);
        //初始化视图预处理器
        initRequestToViewNameTranslator(context);
        //初始化视图转换器
        initViewResolvers(context);
        //
        initFlashMapManager(context);
    }

    private void initMultipartResolver(MyApplicationContext context) {

    }

    private void initLocaleResolver(MyApplicationContext context) {

    }

    private void initThemeResolver(MyApplicationContext context) {

    }

    private void initHandlerMappings(MyApplicationContext context) {
        context.

    }

    private void initHandlerAdapters(MyApplicationContext context) {
        for (String beanName : context.getBeanDefinitionMap().keySet()) {
            Object object = context.getBean(beanName);
            if (!object.getClass().isAnnotationPresent(MyController.class)) {
                continue;
            }

        }


    }

    private void initHandlerExceptionResolvers(MyApplicationContext context) {

    }

    private void initRequestToViewNameTranslator(MyApplicationContext context) {

    }

    private void initViewResolvers(MyApplicationContext context) {

    }

    private void initFlashMapManager(MyApplicationContext context) {

    }
}
