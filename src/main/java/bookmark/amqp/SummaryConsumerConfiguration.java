/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmark.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Developer
 */
@Configuration
public class SummaryConsumerConfiguration extends RabbitMqConfiguration {

    protected final String scraperQueue = "scraper.queue";
	protected final String scraperExchange = "scraper.exchange";
	protected final String routingKey = "";

	@Autowired
	private SummaryResultHandler summaryResultHandler;

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		template.setRoutingKey(this.routingKey);
		template.setQueue(this.scraperQueue);
		template.setMessageConverter(jsonMessageConverter());
		return template;
	}

	@Bean
	public FanoutExchange scraperExchange(){
		return new FanoutExchange(this.scraperExchange);
	}

	@Bean
	public Queue scraperQueue() {
		return new Queue(this.scraperQueue);
	}

	@Bean
	public SimpleMessageListenerContainer listenerContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		container.setQueueNames(this.scraperQueue);
		container.setMessageListener(messageListenerAdapter());
		return container;
	}

	@Bean
	public MessageListenerAdapter messageListenerAdapter() {
		return new MessageListenerAdapter(summaryResultHandler, jsonMessageConverter());
	}


	@Bean
	public Binding queueBinding(){
		return BindingBuilder.bind(scraperQueue()).to(scraperExchange());
	}
}
