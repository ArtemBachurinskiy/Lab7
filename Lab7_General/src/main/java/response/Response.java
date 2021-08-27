package response;

import java.io.Serializable;

/**
 * Класс, представляющий из себя ответ, получаемый от сервера.
 */
public class Response implements Serializable {
    private String command;
    private String message;
    private boolean userLoggedIn;

    public Response(String command, String message, boolean userLoggedIn) {
        this.command = command;
        this.message = message;
        this.userLoggedIn = userLoggedIn;
    }

    public String getCommand() {
        return command;
    }

    public String getMessage() {
        return message;
    }

    public boolean getUserLoggedIn() {
        return userLoggedIn;
    }
}
