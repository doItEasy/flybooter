package com.github.flybooter.autoconfigure.dlock;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.lang.reflect.Method;

/**
 * 切面配置
 *
 * @author feixm
 * @date 2019/3/8
 */
@Configuration
public class DLockConfiguration {

    @Bean
    public AbstractBeanFactoryPointcutAdvisor syncAdvisor() {
        AbstractBeanFactoryPointcutAdvisor advisor = new AbstractBeanFactoryPointcutAdvisor() {
            @Override
            public Pointcut getPointcut() {
                return new StaticMethodMatcherPointcut() {
                    @Override
                    public boolean matches(Method method, Class<?> aClass) {
                        return method.getAnnotation(DLock.class) != null;
                    }
                };
            }
        };
        advisor.setAdvice(new DLockInterceptor());
        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return advisor;
    }
}
