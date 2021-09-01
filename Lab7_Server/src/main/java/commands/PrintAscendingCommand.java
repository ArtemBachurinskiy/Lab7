package commands;

import entities.Movie;
import collection.CollectionManager;
import request.Request;
import response.Response;

import java.util.Comparator;

/**
 * Класс команды print_ascending.
 * Описание: вывести элементы коллекции в порядке возрастания
 */
public class PrintAscendingCommand implements ServerCommand {
    private CollectionManager collectionManager;

    PrintAscendingCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        StringBuilder message = new StringBuilder();
        message.append("Выводим элементы в порядке возрастания (по полю id): \n");
        collectionManager.getMoviesStream()
                .sorted(Comparator.comparingInt(Movie::getId))
                .map(movie -> movie.toString().concat("\n"))
                .forEachOrdered(message::append);
        return new Response(request.getCommand(), message.toString(), true);
    }

    public String getDescription() {
        return "print_ascending : вывести элементы коллекции в порядке возрастания";
    }
}
