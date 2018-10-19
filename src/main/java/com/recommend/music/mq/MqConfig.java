package com.recommend.music.mq;

public interface MqConfig {
    String EXCHANGE="test-exe";
    String QUEUE="balance";
    String ROUTING_KEY="balance";
    String EXCHANGE_TYPE="direct";
}
