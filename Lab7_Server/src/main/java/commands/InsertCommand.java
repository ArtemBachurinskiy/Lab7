package commands;

import collection.CollectionManager;
import request.Request;
import response.Response;

/**
 * Класс команды insert.
 * Описание команды: добавить новый элемент с заданным ключом
 */
public class InsertCommand implements ServerCommand {
    private CollectionManager collectionManager;

    /**
     * @param collectionManager менеджер коллекции
     */
    InsertCommand (CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        StringBuilder message = new StringBuilder();
        if (!request.getArgument().isEmpty()) {
            collectionManager.insertMovie(request);
            message.append("Элемент успешно добавлен!");
        }
        else
            message.append("Необходимо задать аргумент!");
        return new Response(request.getCommand(), message.toString());
    }

    public String getDescription() {
        return "insert null {element} : добавить новый элемент с заданным ключом";
    }
}