package com.xiao.java.proxy.basic;

import com.xiao.java.utils.Log;

import java.lang.reflect.*;

/**
 * 动态代理原理
 * 通过运行时生成一个名为 $Proxy0的代理对象，改代理对象实现目标接口的所有方法，
 * 包含一个 InvocationHandler 为参数的构造方法，
 * InvocationHandler h
 * $Proxy0每个方法中调用都会转交给 h.invoke(this,method,args[])
 *
 * 使用方法：
 *      Book book = （ Book ）Proxy.newProxyInstance( classLoader, proxyInter, invocationHandler)
 *      内部实现包含 ：
 *      1、ProxyClass proxyClass = Proxy.getProxyClass
 *      2、Constructor constructor = proxyClass.getConstructor(InvocationHandler.class)
 *      3、Book book = constructor.newInstance(new InvocationHandler(){
 *          //外部通过book调用的方法  最终都会转交到 h.invoke 方法调用上
 *          public Object invoke(Object proxy, Method method, Object[] args){
 *
 *          }
 *      })
 *      //调用代理方法
 *      4、book.xxx()
 *
 */
public class ProxyDemo {

    public static final String TAG = "BASIC_DEMO";

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

//        通过这行代码可以将再运行期通过动态代理生成的代理类保存下来
//        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        Class bookProxyClazz = Proxy.getProxyClass(Book.class.getClassLoader(), Book.class);

        // 获取 $Proxy0.class
        Constructor constructor = bookProxyClazz.getConstructor(InvocationHandler.class);

        // 通过反射 创建 $Proxy0 对象  由于 $Proxy0 是 Book的子类   可强转
        Book book = (Book) constructor.newInstance(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                return "是否需要获取这本书  我是代理商 现在给你了！ " + args[0];
            }
        });

        Log.print(TAG, bookProxyClazz.getName());

        Log.print(TAG,book.getBook("Java虚拟机深入理解"));
    }


}
