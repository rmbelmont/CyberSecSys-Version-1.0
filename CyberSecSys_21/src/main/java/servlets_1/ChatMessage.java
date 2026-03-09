package servlets_1;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatMessage {
    
    private String sender;
    private String content;
    private String timestamp;
    private LocalDateTime dateTime;
    
    public ChatMessage(String sender, String content) {
        this.sender = sender;
        this.content = content;
        this.dateTime = LocalDateTime.now();
        this.timestamp = this.dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
    
    public ChatMessage(String sender, String content, LocalDateTime dateTime) {
        this.sender = sender;
        this.content = content;
        this.dateTime = dateTime;
        this.timestamp = dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
    
    public String getSender() {
        return sender;
    }
    
    public void setSender(String sender) {
        this.sender = sender;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.timestamp = dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
    
    public String getFormattedDateTime() {
        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
    
    public String getFormattedDate() {
        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    
    public String getFormattedTime() {
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
    
    public boolean isFromUser() {
        return "user".equals(sender);
    }
    
    public boolean isFromBot() {
        return "bot".equals(sender);
    }
    
    @Override
    public String toString() {
        return "ChatMessage{" +
                "sender='" + sender + '\'' +
                ", content='" + content + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        ChatMessage that = (ChatMessage) obj;
        
        if (!sender.equals(that.sender)) return false;
        if (!content.equals(that.content)) return false;
        return dateTime.equals(that.dateTime);
    }
    
    @Override
    public int hashCode() {
        int result = sender.hashCode();
        result = 31 * result + content.hashCode();
        result = 31 * result + dateTime.hashCode();
        return result;
    }
}