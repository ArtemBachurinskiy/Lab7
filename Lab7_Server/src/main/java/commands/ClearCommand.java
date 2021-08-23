package commands;

import collection.CollectionManager;
import database.DBWriter;
import request.Request;
import response.Response;

/**
 * Класс команды clear.
 * Описание команды: очистить коллекцию
 */
public class ClearCommand implements ServerCommand {
    private CollectionManager collectionManager;
    private DBWriter dbWriter;

    /**
     * @param collectionManager менеджер коллекции
     */
    ClearCommand(CollectionManager collectionManager, DBWriter dbWriter) {
        this.collectionManager = collectionManager;
        this.dbWriter = dbWriter;
    }

    @Override
    public Response execute(Request request) {
        String message;
        boolean cleared = dbWriter.clearDBEntitiesTable();
        if (cleared) {
            message = "Таблица БД успешно очищена! БД и коллекция синхронизирваны.";
            collectionManager.clearCollection();
        } else
            message = "Не удалось очистить таблицу БД!";
        return new Response(request.getCommand(), message);
    }

    public String getDescription() {
        return "clear : очистить коллекцию";
    }
}
