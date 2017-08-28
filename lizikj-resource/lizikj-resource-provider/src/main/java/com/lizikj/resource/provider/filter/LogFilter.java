package com.lizikj.resource.provider.filter;

import com.alibaba.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogFilter implements Filter {

    private final static Logger logger = LoggerFactory.getLogger(LogFilter.class);

    private static final String BEFORE_LOG_MSG = "local service[{}.{}][{}].\nReceive request:{}.";

    private static final String AFTER_LOG_MSG = "local service [{}.{}].\nReturn response:[{}]";



    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        
        before(invoker, invocation);
        Result result = invoker.invoke(invocation);
        after(invoker, invocation, result.getValue());
        return result;
    }

    private void before(Invoker<?> invoker, Invocation invocation) {
        try {
            logger.info(BEFORE_LOG_MSG, invoker.getInterface().getSimpleName(), invocation.getMethodName(),
                    invoker.getUrl().getAddress(), invocation.getArguments());
        } catch (Exception ex) {
        }
    }

    private void after(Invoker<?> invoker, Invocation invocation, Object response) {
        try {
            logger.info(AFTER_LOG_MSG, invoker.getInterface().getSimpleName(), invocation.getMethodName(), response);
        } catch (Exception ex) {
        }
    }

}
