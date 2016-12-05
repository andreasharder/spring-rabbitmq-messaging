/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmark.amqp;

/**
 *
 * @author Developer
 */
public class SummaryMessage {
    
    String summary;   
    String url;
    
    public String getSummary(){
        return this.summary;
    }
    
    public void setSummary(String summary){
        this.summary = summary;
    }
    
    public String getUrl(){
        return this.url;
    }
    
    public void setUrl(String url){
        this.url = url;
    }
}
