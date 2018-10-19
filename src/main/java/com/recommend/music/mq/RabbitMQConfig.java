package com.recommend.music.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@Slf4j
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.host}")
    private String host;
    private String virtualhost;
    private RabbitAdmin rabbitAdmin;

    @Bean
    public org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory(){
        log.debug("username:%s,pass:%s,host:%s",username,password,host);
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(StringUtils.isEmpty(virtualhost)?"/":virtualhost);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin(){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        this.rabbitAdmin = rabbitAdmin;
        return rabbitAdmin;
    }

    @Bean
    DirectExchange contractDirectExchange(){
        DirectExchange contractDirectExchange = new DirectExchange(MqConfig.EXCHANGE,true,false);
        rabbitAdmin.declareExchange(contractDirectExchange);
        return contractDirectExchange;
    }

    /**
     * 广播
     * @return
     */
//    @Bean
//    FanoutExchange contractFanoutExchange(){
//        FanoutExchange fanoutExchange = new FanoutExchange("",true,false);
//        rabbitAdmin.declareExchange(fanoutExchange);
//        return fanoutExchange;
//    }

    @Bean
    Queue queueTenant(){
        Queue queue = new Queue(MqConfig.QUEUE,false);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    Binding bindingExchangeContract(Queue queue,DirectExchange exchange){
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(MqConfig.ROUTING_KEY);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

//    @Bean
//    Binding bindingFanoutExchangeContract(Queue queue,FanoutExchange exchange){
//        Binding binding = BindingBuilder.bind(queue).to(exchange);
//        return binding;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public RabbitAdmin getRabbitAdmin() {
        return rabbitAdmin;
    }

    public void setRabbitAdmin(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }
}
