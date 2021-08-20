package commands;

import collection.CollectionManager;
import request.Request;
import response.Response;

import java.util.Iterator;
import java.util.Set;

/**
 * Класс комнады remove_greater.
 * Описание команды: удалить из коллекции все элементы, превышающие заданный.
 * Чтобы узнать, какие элементы мы удалим, нужно сначала использовать команду show.
 */
public class RemoveGreaterCommand implements ServerCommand {
    private CollectionManager collectionManager;

    /**
     * @param collectionManager менеджер коллекции
     */
    RemoveGreaterCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    //todo: lambda
    @Override
    public Response execute(Request request) {
        String message = "";

        if (!request.getArgument().isEmpty())
        {
            boolean found = false;
            Set<String> set = collectionManager.getCollectionKeySet();
            for (String string : set)
                if (string.equals(request.getArgument())) {
                    found = true;
                    break;
                }
            if (found) {
                boolean removed = false;
                Iterator<String> iterator = set.iterator();
                while (iterator.hasNext()) {
                    String string = iterator.next();
                    if (!request.getArgument().equals(string)) {
                        iterator.remove();
                        removed = true;
                    } else break;
                }
                if (removed)
                    message = "Элементы успешно удалены!";
            }
            else
                message = "В коллекции нет элемента с ключом \'" + request.getArgument() + "\'!";
        }
        else
            message = "Необходимо задать аргумент!";

        return new Response(request.getCommand(), message);
    }

    public String getDescription() {
        return "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный";
    }
}
