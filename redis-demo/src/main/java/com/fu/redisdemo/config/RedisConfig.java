package com.fu.redisdemo.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.List;

/**
 * Redis配置文件
 */
@SuppressWarnings("unchecked")
@Configuration
@ConfigurationProperties("spring.redis.cluster")
public class RedisConfig {
    private List<String> nodes;
    //    @Value("spring.redis.password")
    private String password;

    /**
     * 把存入Redis的数据转化为<String,json>
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * Redis自定义序列化方式为json。推荐使用如下方式注入：
     * 1、@Autowired
     * 2、private RedisTemplate redisTemplate;
     */
    /*@Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());//使用json序列化
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }*/

    /**
     * Redisson分布式锁配置（个人感觉不是很好用，而且集群模式下不推荐，因为会抛出：远程主机强迫关闭了一个现有的连接异常。）
     */
    @Bean
    public Redisson redisson() {
        Config config = new Config();
        String node;
        for (int i = 0; i < nodes.size(); i++) {
            node = nodes.get(i);
            //要把ip地址替换成redis:// or rediss:// (for SSL connection)
            node = node.replaceAll(node, "redis://" + node);
            //把替换后的节点放入list集合
            nodes.set(i, node);
        }
        //Redis单点模式（单点模式下推荐使用Redisson，因为加锁解锁比较简单，不用写lua脚本。）
//        config.useSingleServer()
//                .setDatabase(0)
////                .setPassword(password)
//                .setAddress("redis://localhost:6379");
        //Redis集群模式（集群模式下不推荐，因为会抛出：远程主机强迫关闭了一个现有的连接异常。）
        config.useClusterServers()
//                .setPassword(password)
                .setNodeAddresses(nodes);
        return (Redisson) Redisson.create(config);
    }

    //-----------------------set/get----------------------------


    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }
}

