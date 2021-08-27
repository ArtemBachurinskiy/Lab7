package commands;

import collection.CollectionManager;
import database.DBReader;
import database.DBWriter;
import request.Request;
import response.Response;

import java.util.Set;

/**
 * Класс команды clear.
 * Описание команды: очистить коллекцию
 */
public class ClearCommand implements ServerCommand {
    private CollectionManager collectionManager;
    private DBWriter dbWriter;
    private DBReader dbReader;

    ClearCommand(CollectionManager collectionManager, DBWriter dbWriter, DBReader dbReader) {
        this.collectionManager = collectionManager;
        this.dbWriter = dbWriter;
        this.dbReader = dbReader;
    }

    @Override
    public Response execute(Request request) {
        String message;
        Set<Integer> idsToDelete = dbWriter.composeUserIdsToDelete(request.getUsername());
        if (idsToDelete != null) {
            dbWriter.deleteMultipleEntitiesFromDB(idsToDelete);
            collectionManager.clearCollection();
            dbReader.readDBEntitiesTable();
            message = "Из таблицы БД успешно удалены все эл-ты пользователя '" + request.getUsername() + "'! БД и коллекция синхронизированы.";
        } else
            message = "Не удалось очистить таблицу БД!";
        return new Response(request.getCommand(), message, true);
    }

    public String getDescription() {
        return "clear : очистить коллекцию";
    }
}
