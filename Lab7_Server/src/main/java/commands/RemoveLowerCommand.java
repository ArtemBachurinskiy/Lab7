package commands;

import collection.CollectionManager;
import request.Request;
import response.Response;

import java.util.Iterator;
import java.util.Set;

/**
 * Класс команды remove_lower.
 * Описание команды: удалить из коллекции все элементы, меньшие, чем заданный
 * Чтобы узнать, какие элементы мы удалим, нужно сначала использовать команду show.
 */
public class RemoveLowerCommand implements ServerCommand {
    private CollectionManager collectionManager;

    /**
     * @param collectionManager менеджер коллекции
     */
    RemoveLowerCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    //todo: lambda
    @Override
    public Response execute(Request request) {
        // удалить из коллекции все элементы, меньшие, чем заданный,
        // т.е. ниже него по списку (узнать какие это эл-ты мы сможем выполнив команду show())
        StringBuilder message = new StringBuilder();

        if (!request.getArgument().isEmpty())
        {
            Set<String> setToRetain = collectionManager.getCollectionKeySet();
            boolean found = false;
            boolean removed = false;

            Iterator<String> iterator = setToRetain.iterator();
            while (iterator.hasNext()) {
                String string = iterator.next();
                if (request.getArgument().equals(string))
                    found = true;
                if (found && !request.getArgument().equals(string)) {
                    iterator.remove();
                    removed = true;
                }
            }
            if(!found)
                message.append("В коллекции нет элемента с ключом \'")
                        .append(request.getArgument())
                        .append("\'!");
            if(removed)
                message.append("Элементы успешно удалены!");
        }
        else
            message.append("Необходимо задать аргумент!");

        return new Response(request.getCommand(), message.toString());
    }

    public String getDescription() {
        return "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный";
    }
}
