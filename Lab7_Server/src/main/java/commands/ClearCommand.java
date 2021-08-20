package commands;

import collection.CollectionManager;
import request.Request;
import response.Response;

/**
 * Класс команды clear.
 * Описание команды: очистить коллекцию
 */
public class ClearCommand implements ServerCommand {
    private CollectionManager collectionManager;

    /**
     * @param collectionManager менеджер коллекции
     */
    ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        collectionManager.clearCollection();
        return new Response(request.getCommand(), "Коллекция успешно очищена!");
    }

    public String getDescription() {
        return "clear : очистить коллекцию";
    }
}
