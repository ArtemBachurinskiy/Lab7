package commands;

import database.DBWriter;
import request.Request;
import response.Response;

/**
 * Класс команды save.
 * Описание команды: сохранить коллекцию в базу данных
 */
public class SaveCommand implements ServerCommand {
    private DBWriter dbWriter;

    public SaveCommand (DBWriter dbWriter) {
        this.dbWriter = dbWriter;
    }

    @Override
    public Response execute (Request request) {
        String message = dbWriter.writeCollectionToDB();
        return new Response(request.getCommand(), message);
    }

    @Override
    public String getDescription() {
        return "save : сохранить коллекцию в базу данных";
    }
}
