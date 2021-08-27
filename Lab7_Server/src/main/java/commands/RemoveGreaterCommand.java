package commands;

import collection.CollectionManager;
import database.DBWriter;
import entities.Movie;
import request.Request;
import response.Response;

import java.util.*;

/**
 * Класс комнады remove_greater.
 * Описание команды: удалить из коллекции все элементы, превышающие заданный
 * (удаляются те элементы, id которых больше, чем заданный)
 */
public class RemoveGreaterCommand implements ServerCommand {
    CollectionManager collectionManager;
    DBWriter dbWriter;

    /**
     * @param collectionManager менеджер коллекции
     */
    RemoveGreaterCommand(CollectionManager collectionManager, DBWriter dbWriter) {
        this.collectionManager = collectionManager;
        this.dbWriter = dbWriter;
    }

    //todo: lambda
    @Override
    public Response execute (Request request) {
        String message = "";
        Integer id = Integer.parseInt(request.getArgument());

        if (!request.getArgument().isEmpty()) {
            if (idIsFound(id)) {
                Set<Integer> notFilteredIds = dbWriter.composeUserIdsToDelete(request.getUsername());
                Set<Integer> filteredIds = filterByCommandMeaning(notFilteredIds, id);

                dbWriter.deleteMultipleEntitiesFromDB(filteredIds);
                removeCorrespondingCollectionIds(filteredIds);
                message = "Требуемые эл-ты пользователя '" + request.getUsername() + "' успешно удалены из БД! БД и коллекция синхронизированы.";
            }
            else
                message = "В коллекции нет элемента с id = " + id + "!";
        }
        else
            message = "Необходимо задать аргумент!";

        return new Response(request.getCommand(), message, true);
    }

    boolean idIsFound(Integer id) {
        ArrayList<Map.Entry<String, Movie>> elements = collectionManager.getCollectionElements();
        for (Map.Entry<String, Movie> element : elements) {
            if (element.getValue().getId().equals(id))
                return true;
        }
        return false;
    }

    Set<Integer> filterByCommandMeaning(Set<Integer> notFilteredIds, Integer id) {
        Set<Integer> filteredIds = new HashSet<>();
        for (Integer possibleId : notFilteredIds) {
            if (possibleId > id) {
                filteredIds.add(possibleId);
            }
        }
        return filteredIds;
    }

    void removeCorrespondingCollectionIds(Set<Integer> filteredIds) {
        for (Integer id : filteredIds) {
            collectionManager.deleteMovieById(id);
        }
    }

    public String getDescription() {
        return "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный";
    }
}