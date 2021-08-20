package collection;

import database.DBManager;
import entities.Movie;
import request.Request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Класс, содержащий коллекцию объектов типа Movie и методы по управлению ей.
 */
public class CollectionManager {
    private Hashtable<String, Movie> Movies;
    private final LocalDate creationDate;
    private DBManager dbManager;

    public CollectionManager(DBManager dbManager) {
        this.dbManager = dbManager;
        Movies = new Hashtable<>();
        creationDate = LocalDate.now();
    }

    /**
     * Метод, который помещает новый элемент в коллекцию.
     * @param request запрос, из которого берётся ключ и объект типа Movie.
     */
    public void insertMovie(Request request) {
        Movies.put(request.getArgument(), request.generateMovie(dbManager.generateSequenceId()));
    }

    /**
     * Метод, который помещает новый элемент в коллекцию.
     * @param key ключ данного объекта
     * @param movie объект типа Movie
     */
    public void insertMovie(String key, Movie movie) {
        Movies.put(key, movie);
    }

    /**
     * @return размер коллекции.
     */
    public int getCollectionSize() {
        return Movies.size();
    }

    /**
     * @return тип коллекции.
     */
    public String getCollectionType() {
        return Movies.getClass().toString();
    }

    /**
     * @return дату создания коллекции.
     */
    public String getCollectionCreationDate() {
        return creationDate.toString();
    }

    /**
     * @return ArrayList элементов, хранящихся в коллекции.
     */
    public ArrayList<Map.Entry<String, Movie>> getCollectionElements () {
        return new ArrayList<>(Movies.entrySet());
    }

    /**
     * @return Set ключей коллекции.
     */
    public Set<String> getCollectionKeySet() {
        return Movies.keySet();
    }

    /**
     * Очищает коллекцию.
     */
    public void clearCollection() {
        Movies.clear();
    }

    /**
     * Автоматически генерирует уникальное поле id.
     * Новый id всегда на 1 больше (+1), чем максимальное id в коллекции.
     * @return сгенерированный id
     */
    private int generateFreeId() {
        Integer id = 0;
        Set<String> set = Movies.keySet();
        for (String string : set) {
            if (id < Movies.get(string).getId())
                id = Movies.get(string).getId();
        }
        id++;
        return id;
    }

    /**
     * Удаляет объект из коллекции по его ключу.
     * @param key ключ элемента, который нужно удалить из коллекции
     * @return true если элемент успешно удалён,
     *         false в противном случае.
     */
    public boolean remove_key(String key) {
        if (Movies.containsKey(key)) {
            Movies.remove(key);
            return true;
        } else
            return false;
    }

    /**
     * @return Поток объектов Movie, хранящихся в коллекции.
     */
    public Stream<Movie> getMoviesStream() {
        return Movies.values().stream();
    }
}
