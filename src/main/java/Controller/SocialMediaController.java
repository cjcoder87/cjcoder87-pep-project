package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {
    // original code below
    /**
     * In order for the test cases to work, you will need to write the endpoints in
     * the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * 
     * @return a Javalin app object which defines the behavior of the Javalin
     *         controller.
     */
    // public Javalin startAPI() {
    // Javalin app = Javalin.create();
    // app.get("example-endpoint", this::exampleHandler);

    // return app;
    // }

    /**
     * This is an example handler for an example endpoint.
     * 
     * @param context The Javalin Context object manages information about both the
     *                HTTP request and response.
     */
    // private void exampleHandler(Context context) {
    // context.json("sample text");
    // }

    // example above

    // original code above

    // my code starting, assisted by flight Mini Project

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        // start account logic
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * Method defines the structure of the Javalin Flights API. Javalin methods will
     * use handler methods
     * to manipulate the Context object, which is a special object provided by
     * Javalin which contains information about
     * HTTP requests and can generate responses. There is no need to change anything
     * in this method.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerAccountHandler);
        app.post("/login", this::loginAccountHandler);
        app.post("/messages", this::addMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);

        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", ctx -> this.updateMessageTextByIdHandler(ctx));
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountIdHandler);
        return app;
    }

    private void registerAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount == null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(addedAccount));
        }
    }

    private void loginAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account inputAccount = mapper.readValue(ctx.body(), Account.class);
        Account validAccount = accountService.loginAccount(inputAccount);
        if (validAccount == null) {
            ctx.status(401);
        } else {
            ctx.json(validAccount);
        }

    }

    // start message logic

    private void addMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = this.messageService.addMessage(message);
        if (addedMessage == null) {
            ctx.status(400);
        } else {
            ctx.json(addedMessage);
        }

    }

    private void getAllMessagesHandler(Context ctx) {
        ctx.json(messageService.getAllMessages());
    }

    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = this.messageService.getMessageById(messageId);
        System.out.println(message);

        if (message != null) {
            ctx.json(message);
        } else {
            ctx.result("");
        }
    }

    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = this.messageService.deleteMessageById(messageId);

        if (deletedMessage != null) {
            ctx.json(deletedMessage);
        } else {
            ctx.result("");
        }
    }

    private void updateMessageTextByIdHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));

        try {
            // Expecting: { "message_text": "new text here" }
            Map<String, String> body = new ObjectMapper().readValue(ctx.body(),
                    new TypeReference<Map<String, String>>() {
                    });
            String newMessageBody = body.get("message_text");

            Message updatedMessage = this.messageService.updateMessageTextById(messageId, newMessageBody);

            if (updatedMessage != null) {
                ctx.json(updatedMessage);
            } else {
                ctx.status(400); // Invalid input or message not found
            }
        } catch (Exception e) {
            ctx.status(400);
        }
    }

    private void getMessagesByAccountIdHandler(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByAccountId(accountId);
        ctx.json(messages); // Will be empty list [] if no messages
    }

}