package com.zk.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 对于非业务性质的校验，我们应该在请求未达到各个服务之前校验，这样即可以提高响应的速度，
 * 同时也降低了开发和测试难度（因为不用为每一个服务模块写冗余的校验逻辑）。因此这类校验
 * 在Zuul网关这层做比较好。
 *
 * Created by zhuk on 2018/3/26.
 */
@RestController
public class AccessFilter extends ZuulFilter {
    private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    /**
     * 过滤器的类型，它决定过滤器在请求的那个生命周期中执行，这里的"pre"，代表请求在被路由之前执行
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器的执行顺序，当请求在一个阶段中存在多个过滤器时，需要根据该方法的返回值来依次执行
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 根据该返回值，决定该过滤器是否需要被执行。这里我们直接返回true，因此对所有请求都生效
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的具体逻辑
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        logger.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());

        String accessToken = request.getParameter("accessToken");
        if (accessToken == null) {
            logger.warn("access token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }
        logger.info("access token ok");
        return null;
    }
}
