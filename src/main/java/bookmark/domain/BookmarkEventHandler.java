/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmark.domain;

import bookmark.amqp.TaskMessage;
import bookmark.amqp.TaskProducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import java.util.Date;

/**
 *
 * @author Developer
 */

@RepositoryEventHandler(Bookmark.class)
public class BookmarkEventHandler {
    
    @Autowired
    private TaskProducer taskProducer;
    
    @HandleBeforeCreate
    public void handleBookmarkCreate(Bookmark bookmark)
    {
        bookmark.setCreated(new Date());
    }
    
    @HandleAfterCreate
    public void handleAfterBookmarkCreate(Bookmark bookmark)
    {
        final TaskMessage taskMessage = new TaskMessage();
        taskMessage.setUrl(bookmark.getUrl());

        taskProducer.sendNewTask(taskMessage);
    }
}
