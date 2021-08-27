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
    Set<Integer> filterByCommandMeaning(Set<Integer> notFilteredIds, Integer id) {
        Set<Integer> filteredIds = new HashSet<>();
        for (Integer possibleId : notFilteredIds) {
            if (possibleId < id) {
                filteredIds.add(possibleId);
            }
        }
        return filteredIds;
    }

    @Override
    void removeCorrespondingCollectionIds(Set<Integer> filteredIds) {
        for (Integer id : filteredIds) {
            collectionManager.deleteMovieById(id);
        }
    }

    @Override
    public String getDescription() {
        return "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный";
    }
}
