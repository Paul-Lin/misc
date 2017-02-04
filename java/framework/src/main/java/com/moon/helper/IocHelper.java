package com.moon.helper;

import com.moon.annotation.Inject;
import com.moon.util.ReflectionUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 依赖注入助手类
 * Created by Paul on 2017/2/3.
 *
 */
public final class IocHelper {
    static {
        // 获取所有的Bean类与Bean实例之间的映射关系（简称BeanMap）
        Map<Class<?>,Object> beanMap=BeanHelper.getBeanMap();
        if(MapUtils.isNotEmpty(beanMap)){
            // 遍历Bean Map
            beanMap.entrySet().forEach(beanEntry->{
                // 从Bean Map 中获取Bean类与Bean实例
                Class<?> beanClass=beanEntry.getKey();
                Object beanInstance=beanEntry.getValue();
                // 获取Bean类定义的所有成员变量(简称Bean Field)
                Field[] beanFields=beanClass.getDeclaredFields();
                if(ArrayUtils.isNotEmpty(beanFields)){
                    // 遍历Bean Field
                    Stream.of(beanFields).forEach(beanField->{
                        // 判断当前Bean Field是否带有Inject注解
                        if(beanField.isAnnotationPresent(Inject.class)){
                            // 在Bean Map中获取Bean Field对应的实例
                            Class<?> beanFieldClass=beanField.getType();
                            Object beanFieldInstance=beanMap.get(beanFieldClass);
                            if(Objects.nonNull(beanFieldInstance)){
                                // 通过反射初始化BeanField的值
                                ReflectionUtil.setField(beanInstance,beanField,beanFieldInstance);
                            }
                        }
                    });
                }
            });
        }
    }
}
