package commands;

import entities.Movie;
import collection.CollectionManager;
import request.Request;
import response.Response;

import java.util.Comparator;

/**
 * Класс команды show.
 * Описание команды: вывести в стандартный поток вывода все элементы коллекции в строковом представлении
 */
public class ShowCommand implements ServerCommand {
    private CollectionManager collectionManager;

    /**
     * @param collectionManager менеджер коллекции
     */
    ShowCommand (CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        StringBuilder message = new StringBuilder();

        if (collectionManager.getCollectionSize() == 0)
            message.append("Коллекция пуста.");
        else {
            message.append("Элементы коллекции : \n");
            collectionManager.getMoviesStream()
                            .sorted(Comparator.comparing(Movie::getName))
                            .forEachOrdered(movie -> message.append("key [").append(movie.getName()).append("] -> ").append(movie).append("\n"));
        }
        return new Response(request.getCommand(), message.toString(), true);
    }

    public String getDescription() {
        return "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}