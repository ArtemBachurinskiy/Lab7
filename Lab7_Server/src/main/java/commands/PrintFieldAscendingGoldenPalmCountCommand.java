package commands;

import collection.CollectionManager;
import request.Request;
import response.Response;

/**
 * Класс команды print_field_ascending_golden_palm_count.
 * Описание команды: вывести значения поля goldenPalmCount всех элементов в порядке возрастания
 */
public class PrintFieldAscendingGoldenPalmCountCommand implements ServerCommand {
    private CollectionManager collectionManager;

    /**
     * @param collectionManager менеджер коллекции
     */
    PrintFieldAscendingGoldenPalmCountCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        StringBuilder message = new StringBuilder();

        collectionManager.getMoviesStream()
                .sorted((movie1, movie2) -> (int) (movie1.getGoldenPalmCount() - movie2.getGoldenPalmCount()))
                .map(movie -> String.valueOf(movie.getGoldenPalmCount()).concat("\n"))
                .forEachOrdered(message::append);
        return new Response(request.getCommand(), message.toString());
    }

    public String getDescription() {
        return "print_field_ascending_golden_palm_count : вывести значения поля goldenPalmCount всех элементов в порядке возрастания";
    }
}
