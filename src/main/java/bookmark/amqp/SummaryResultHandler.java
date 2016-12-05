/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmark.amqp;

import bookmark.domain.Bookmark;
import bookmark.domain.BookmarkRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Developer
 */
@Component
class SummaryResultHandler {
    
    @Autowired
    private BookmarkRepository bookmarkRepository;

    public void handleMessage(SummaryMessage summaryMessage)
    {
        System.out.println("Received summary: " + summaryMessage.getSummary());
        final String url = summaryMessage.getUrl();
        final List<Bookmark> bookmarks = bookmarkRepository.findByUrl(url);
        
        if (bookmarks.isEmpty())
        {
            System.out.println("No bookmark of url: " + url + " found.");
        }
        else
        {
            for (Bookmark bookmark : bookmarks)
            {
                bookmark.setSummary(summaryMessage.getSummary());
                bookmarkRepository.save(bookmarks);
                System.out.println("updated bookmark: " + url);
            }
        }
    }
}
