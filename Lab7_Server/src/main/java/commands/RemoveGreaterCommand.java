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
                dbWriter.deleteMultipleEntitiesFromDB(composeIdsToDelete(id));
                removeCorrespondingIds(id);
                message = "Элементы успешно удалены из БД! БД и коллекция синхронизированы.";
            }
            else
                message = "В коллекции нет элемента с id = " + id + "!";
        }
        else
            message = "Необходимо задать аргумент!";

        return new Response(request.getCommand(), message);
    }

    boolean idIsFound(Integer id) {
        ArrayList<Map.Entry<String, Movie>> elements = collectionManager.getCollectionElements();
        for (Map.Entry<String, Movie> element : elements) {
            if (element.getValue().getId().equals(id))
                return true;
        }
        return false;
    }

    Set<Integer> composeIdsToDelete(Integer id) {
        Set<Integer> idsToDelete = new HashSet<>();
        ArrayList<Map.Entry<String, Movie>> elements = collectionManager.getCollectionElements();
        for (Map.Entry<String, Movie> element : elements) {
            if (element.getValue().getId() > id) {
                idsToDelete.add(element.getValue().getId());
            }
        }
        return idsToDelete;
    }

    void removeCorrespondingIds(Integer id) {
        Iterator<Map.Entry<String, Movie>> iterator = collectionManager.getCollectionElements().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Movie> element = iterator.next();
            if (element.getValue().getId() > id) {
                collectionManager.deleteMovieById(element.getValue().getId());
            }
        }
    }

    public String getDescription() {
        return "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный";
    }
}