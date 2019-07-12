package com.github.flybooter.autoconfigure.dlock;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 同步锁拦截器
 *
 * @author feixm
 * @date 2019/3/2
 */
@Slf4j
public class DLockInterceptor implements MethodInterceptor {
    private final Map<String, Expression> keyCache = new ConcurrentHashMap<>(64);
    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    private final ExpressionParser expressionParser = new SpelExpressionParser();

    public DLockInterceptor() {
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        DLock sync = method.getAnnotation(DLock.class);
        if (sync == null) {
            return invocation.proceed();
        }
        Expression expression;
        if (StringUtils.isNotBlank(sync.value())) {
            expression = getExpression(sync.value());
        } else {
            expression = getExpression("#root.classMethod");
        }
        EvaluationContext evaluationContext = createEvaluationContext(invocation, method);
        String toLockKey = expression.getValue(evaluationContext, String.class);
        int timeoutSeconds = sync.timeoutSeconds();

        //do lock


        return invocation.proceed();

//        try (AutoReleaseLock lock = lockCache.tryLock(toLockKey, timeoutSeconds, TimeUnit.SECONDS)) {
//            if (lock != null) {
//                try {
//                    return invocation.proceed();
//                } finally {
//                    lockCache.remove(toLockKey);
//                }
//            } else {
//                throw new IllegalStateException("获取redis锁失败,key:" + toLockKey);
//            }
//        }
    }

    private EvaluationContext createEvaluationContext(MethodInvocation invocation, Method method) {
        return new MethodBasedEvaluationContext(createRootObject(invocation), method, invocation.getArguments(), parameterNameDiscoverer);
    }

    private DLockExpressionRootObject createRootObject(MethodInvocation invocation) {
        return new DLockExpressionRootObject(invocation.getThis(), invocation.getMethod(), invocation.getArguments());
    }

    private Expression getExpression(String key) {
        Expression expression = keyCache.get(key);
        if (expression != null) {
            return expression;
        }
        expression = expressionParser.parseExpression(key);
        keyCache.putIfAbsent(key, expression);
        return expression;
    }

    static class DLockExpressionRootObject {
        private Object target;
        private Method method;
        private Object[] arguments;

        public DLockExpressionRootObject(Object target, Method method, Object[] arguments) {
            this.target = target;
            this.method = method;
            this.arguments = arguments;
        }

        public String getClassMethod() {
            return target.getClass().getName() + "." + method.getName();
        }

        public Object getTarget() {
            return target;
        }

        public Method getMethod() {
            return method;
        }

        public String getClassName() {
            return target.getClass().getName();
        }

        public String getMethodName() {
            return method.getName();
        }

        public Object[] getArguments() {
            return arguments;
        }
    }

}
