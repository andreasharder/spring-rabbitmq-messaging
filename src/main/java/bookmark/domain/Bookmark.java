/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmark.domain;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Developer
 */
public class Bookmark {
    
    @Id
    private String id;

    private String url;
    private String note;
    
    @DateTimeFormat()
    private Date created;
     
    public String getUrl() {
            return url;
    }

    public void setUrl(String url) {
            this.url = url;
    }

    public String getNote() {
            return note;
    }

    public void setNote(String note) {
            this.note = note;
    }
    
    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }
}
