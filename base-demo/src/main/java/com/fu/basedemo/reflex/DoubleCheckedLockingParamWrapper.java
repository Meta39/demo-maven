package com.fu.basedemo.reflex;

import com.fu.basedemo.designpattern.singleton.DoubleCheckedLockingParam;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通过反射绕过私有访问并缓存绕过私有访问的 Constructor 进而创建实例，ConcurrentHashMap 缓存创建的实例，解决第三方是单例，我们需要多例的问题。
 */
public class DoubleCheckedLockingParamWrapper {
    public static final ConcurrentHashMap<String, DoubleCheckedLockingParam> DOUBLE_CHECKED_LOCKING_PARAM_CACHE = new ConcurrentHashMap<>();
    private static final Constructor<DoubleCheckedLockingParam> cacheConstructor;

    static {
        try {
            cacheConstructor = DoubleCheckedLockingParam.class.getDeclaredConstructor(String.class,String.class,String.class);
            cacheConstructor.setAccessible(true);// 绕过私有访问
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过反射绕过私有访问，缓存绕过私有访问的 Constructor 进而创建多实例，如果没有实例，则创建并把实例放入 ConcurrentHashMap 缓存，有则直接从缓存获取。
     */
    public static DoubleCheckedLockingParam getInstance(String username, String password, String version) {
        try {
            //1.创建 ConcurrentHashMap key
            String key = username + ":" + password + ":" + version;
            //2.根据 key 获取 ConcurrentHashMap 对象
            DoubleCheckedLockingParam doubleCheckedLockingParam = DOUBLE_CHECKED_LOCKING_PARAM_CACHE.get(key);
            //3.根据 key 获取 ConcurrentHashMap 对象是否为空
            if (doubleCheckedLockingParam == null) {
                //4.1如果为空就进行实例化
                doubleCheckedLockingParam = cacheConstructor.newInstance(username, password, version);
                //4.1.1把实例放入缓存
                DOUBLE_CHECKED_LOCKING_PARAM_CACHE.put(key, doubleCheckedLockingParam);
                //4.1.2 如果对象还有其它可以设置的参数可以在后面进行设置
                //如：修改 version = 2.0.1 等
            }
            //4.2如果不为空，说明缓存了对象，直接返回。
            return doubleCheckedLockingParam;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
