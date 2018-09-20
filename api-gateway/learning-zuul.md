# Spring-cloud-zuul

## 1、传统路由方式 - 多实例配置

```properties
#定义url的pattern，配合zuul.routes.<route>.service-id一起使用，若请求与该pattern符合，则将请求转到service-id配置的对应的服务上。
zuul.routes.api-a-url.path==/api-a-url/**
#zuul.routes.<route>.service-id的值是用户自定义的名称，需要配合ribbon.listOfServers参数实现服务与实例的维护。
zuul.routes.api-a-url.service-id=user-service
#由于zuul.routes.<route>.service-id指定的是服务器名称，默认情况下，ribbon会根据服务发现机制，来获取配置服务名对应的实例清单，但是该示例没有整合类似Eureka之类的服务治理框架，所以需要将该参数设置为false，否则配置的service-id获取不到对应实例的清单
ribbon.eureka.enable=false
#user-service.ribbon.listOfServers该参数内容与zuul.routes.<route>.service-id的配置相对应。开头的user-service对应zuul.routes.<route>.service-id的值，这两个参数相当于在该应用内部手工的维护了服务与实例的对应关系
user-service.ribbon.listOfServers=http://localhost:8080/,http://localhost:8081/
```

## 2、服务路由的默认规则：

虽然Eureka与Zuul的整合已经为我们省去了维护服务实例清单的工作，剩下的只需要再维护请求路径的匹配表达式与服务名的映射关系即可。但是在实际运用过程中会发现，大部分的配置路由规则几乎都会采用【服务名作为外部请求的前缀】，比如下面的例子，其中path路径的前缀使用了user-service，而对应的服务名称也是user-service

```properties
zuul.routes.user-service.path==/user-service/**
zuul.routes.user-service.service-id=user-service
```

对于这样的配置内容，我们总是希望自动化的完成。非常庆幸，Zuul默认地为所有Eureka服务实现了如上述示例中的映射关系。这会使得一些我们不希望对外开发的服务也可能被外部访问到。这时候我们可以使用zuul.ignored-services参数来设置一个【服务名匹配表达式】来定义不自动创建路由的规则。Zuul在自动创建路由的时候会根据表达式来进行判断，如果服务名匹配表达式，那么Zuul将跳过该服务，不为其创建路由规则。比如，设置为`zuul.ignored-services=*`的时候，Zuul将对所有的服务都不自动创建路由规则。在这种情况下，我们就要各个服务配置路由规则了

## 3、自定义路由映射规则

待完善



## 总结

- 它作为系统的统一入口，屏蔽了系统内部各个微服务的细节
- 它可以与服务治理框架结合，实现自动化的服务实例维护以及负载均衡的路由转发
- 它可以实现接口权限校验与微服务的业务逻辑解耦
- 通过服务网关中的过滤器，在各个生命周期中去校验请求的内容，将原本在对外服务层的校验前置，保证了微服务的无状态性，同时降低了微服务的测试难度，让服务本身更集中关注业务逻辑的处理

