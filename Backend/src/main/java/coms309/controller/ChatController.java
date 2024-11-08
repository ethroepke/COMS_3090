package coms309.controller;

import coms309.entity.GroupChat;
import coms309.entity.Message;
import coms309.entity.User;
import coms309.entity.UserProfile;
import coms309.repository.GroupChatRepository;
import coms309.repository.MessageRepository;
import coms309.repository.UserProfileRepository;
import coms309.service.ChatService;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@RestController("/chat/{username}/{group_chat_id}")
public class ChatController {
    private static ChatService cs;

    private static GroupChatRepository groupChatRepository;

    private static UserProfileRepository up;
    @Autowired
    private void setMessageRepository(ChatService chatService , GroupChatRepository grepo, UserProfileRepository userProfileRepository){
        cs= chatService;
        groupChatRepository = grepo;
        up = userProfileRepository;
    }
    private static Map<Session , String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username , @PathParam("group_chat_id") Long groupChatId) throws IOException {

        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username,session);


        getChatHistory(session, groupChatId);

    }


    @OnMessage
    public void onMessage(Session session, String message, @PathParam("group_chat_id") Long groupChatId) {
        String username = sessionUsernameMap.get(session);
        sendMessageToGroupChat(username, groupChatRepository.findById(groupChatId).get(), message);
        cs.addMessageToGroupChat(groupChatId, message, username);
    }

    private void sendMessageToGroupChat(String username, GroupChat gp, String message) {
        List<UserProfile> users = up.findAllByGroupChats(gp);
        try {
            for (UserProfile u: users) {
                if (usernameSessionMap.containsKey(u.getUsername())) {
                    Session curSession = usernameSessionMap.get(u.getUsername());
                    curSession.getBasicRemote().sendText(username + ": " + message);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

        private void getChatHistory(Session session, Long groupChatId) throws IOException {
        List<Message> msg = cs.getChatHistory(groupChatId);
        for (Message m: msg) {
            session.getBasicRemote().sendText(m.getSender() + ": " + m.getContent());
        }
    }
}
