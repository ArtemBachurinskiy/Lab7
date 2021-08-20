package response;

import java.io.Serializable;

/**
 * Класс, представляющий из себя ответ, получаемый от сервера.
 */
public class Response implements Serializable {
    private String command;
    private String message;

    public Response(String command, String message) {
        this.command = command;
        this.message = message;
    }

    public String getCommand() {
        return command;
    }

    public String getMessage() {
        return message;
    }
}
