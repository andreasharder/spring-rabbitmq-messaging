/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmark.amqp;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Developer
 */
@Configuration
public class TaskProducerConfiguration extends RabbitMqConfiguration {
    
    protected final String tasksExchange = "tasks.exchange";
    protected final String routingKey = "";


    @Bean
    public RabbitTemplate rabbitTemplate()
    {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setRoutingKey(this.routingKey);
        template.setExchange(this.tasksExchange);
        template.setMessageConverter(jsonMessageConverter());

        return template;
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(tasksExchange);
    }
}
