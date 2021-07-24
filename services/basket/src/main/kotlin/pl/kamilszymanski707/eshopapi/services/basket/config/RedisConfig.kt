//package pl.kamilszymanski707.eshopapi.services.basket.config
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.data.redis.connection.RedisConnectionFactory
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
//import org.springframework.data.redis.core.RedisTemplate
//
//@Configuration
//internal class RedisConfig {
//
//    @Bean
//    fun connectionFactoryBean(): RedisConnectionFactory =
//        JedisConnectionFactory()
//
//    @Bean
//    fun redisTemplateBean(): RedisTemplate<*, *> {
//        val template = RedisTemplate<ByteArray, ByteArray>()
//        template.setConnectionFactory(connectionFactoryBean())
//        return template
//    }
//}