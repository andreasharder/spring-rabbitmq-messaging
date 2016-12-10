/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmark.amqp;

import org.springframework.stereotype.Component;

/**
 *
 * @author Developer
 */

@Component
public class TaskProducer {

    private TaskProducerConfiguration taskProducerConfiguration;

    public TaskProducer(TaskProducerConfiguration taskProducerConfiguration) {
        this.taskProducerConfiguration = taskProducerConfiguration;
    }

    public void sendNewTask(TaskMessage taskMessage)
    {
        System.out.println("Sending new task...");
        taskProducerConfiguration.rabbitTemplate().convertAndSend(taskMessage);
    }
}
