package commands;

import collection.CollectionManager;
import database.DBWriter;
import entities.Movie;
import request.Request;
import response.Response;

import java.util.*;

/**
 * Класс команды remove_lower.
 * Описание команды: удалить из коллекции все элементы, меньшие, чем заданный
 * (удаляются те элементы, id которых меньше, чем заданный)
 */
public class RemoveLowerCommand extends RemoveGreaterCommand implements ServerCommand {
    /**
     * @param collectionManager менеджер коллекции
     */
    RemoveLowerCommand(CollectionManager collectionManager, DBWriter dbWriter) {
        super(collectionManager, dbWriter);
    }

    @Override
    Set<Integer> composeIdsToDelete(Integer id) {
        Set<Integer> idsToDelete = new HashSet<>();
        ArrayList<Map.Entry<String, Movie>> elements = collectionManager.getCollectionElements();
        for (Map.Entry<String, Movie> element : elements) {
            if (element.getValue().getId() < id) {
                idsToDelete.add(element.getValue().getId());
            }
        }
        return idsToDelete;
    }

    @Override
    void removeCorrespondingIds(Integer id) {
        Iterator<Map.Entry<String, Movie>> iterator = collectionManager.getCollectionElements().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Movie> element = iterator.next();
            if (element.getValue().getId() < id) {
                collectionManager.deleteMovieById(element.getValue().getId());
            }
        }
    }

    @Override
    public String getDescription() {
        return "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный";
    }
}
