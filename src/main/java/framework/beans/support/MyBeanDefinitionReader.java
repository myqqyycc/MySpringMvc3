package framework.beans.support;


import framework.annotations.MyController;
import framework.annotations.MyService;
import framework.beans.MyBeanDefinitionRegistry;
import framework.beans.config.MyBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MyBeanDefinitionReader {
    private List<String> registyBeanClasses = new ArrayList<String>();

    private Properties config = new Properties();

    private String[] configResources;
    private MyBeanDefinitionRegistry context;


    //固定配置文件中的key，相对于xml的规范
    private final String SCAN_PACKAGE = "mvc.package";


    public MyBeanDefinitionReader(String[] configResources, MyBeanDefinitionRegistry context) {
        this.configResources = configResources;
        this.context = context;
    }

    public void loadBeanDefinitions() {
        loadConfig();
        doScanner(config.getProperty(SCAN_PACKAGE));
        initBeanDefinitions();
    }

    private void initBeanDefinitions() {
        try {
            for (String className : registyBeanClasses) {
                Class<?> beanClass = Class.forName(className);
                //如果是一个接口，是不能实例化的
                //用它实现类来实例化
                if (beanClass.isInterface() || !(beanClass.isAnnotationPresent(MyController.class) || beanClass.isAnnotationPresent(MyService.class))) {
                    continue;
                }
                String beanName = beanClass.isAnnotationPresent(MyController.class) ? beanClass.getAnnotation(MyController.class).value() : beanClass.getAnnotation(MyService.class).value();
                if ("".equals(beanName)) {
                    beanName = toLowerFirstCase(beanClass.getSimpleName());
                }
                MyBeanDefinition beanDefinition = new MyBeanDefinition();
                beanDefinition.setFactoryBeanName(beanName);
                beanDefinition.setBeanClassName(className);

                context.registerBeanDefinition(beanName, beanDefinition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void doScanner(String scanPackage) {
        File classPath = new File(decoder(this.getClass().getResource("/" + scanPackage.replaceAll("\\.", "/")).getFile()));
        for (File file : classPath.listFiles()) {
            if (file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                String className = (scanPackage + "." + file.getName().replace(".class", ""));
                registyBeanClasses.add(className);
            }
        }
    }

    private void loadConfig() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(this.configResources[0].replace("classpath:", ""));
        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String toLowerFirstCase(String simpleName) {
        byte[] bytes = simpleName.getBytes();

        if ('A' <= bytes[0] && bytes[0] <= 'Z') {
            bytes[0] += 32;
            return new String(bytes);
        }
        return simpleName;
    }

    private String decoder(String str) {
        try {
            return URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
}
