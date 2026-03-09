package servlets_1;
import java.util.ArrayList;
import java.util.List;

public class ChatHistoryData {
    private List<ChatMessage> messages;
    
    public ChatHistoryData() {
        this.messages = new ArrayList<>();
    }
    
    public List<ChatMessage> getMessages() {
        return messages;
    }
    
    public void addMessage(String sender, String content) {
        messages.add(new ChatMessage(sender, content));
    }
    
    public void clear() {
        messages.clear();
    }
}