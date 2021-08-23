package collection;

import database.DBManager;
import entities.Movie;

import java.time.LocalDate;
import java.util.*;
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
     * Важно! Метод возвращает не саму коллекцию Movies, а только лишь её элементы.
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
     * Удаляет объект из коллекции по его ключу.
     * @param key ключ элемента, который нужно удалить из коллекции
     */
    public void remove_key(String key) {
        Movies.remove(key);
    }

    public void deleteMovieById(int id) {
        Iterator<Map.Entry<String, Movie>> iterator = Movies.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Movie> entry = iterator.next();
            if (entry.getValue().getId().equals(id))
                iterator.remove();
        }
    }

    /**
     * @return Поток объектов Movie, хранящихся в коллекции.
     */
    public Stream<Movie> getMoviesStream() {
        return Movies.values().stream();
    }

    public Movie getMovieByKey(String key) {
        if (Movies.containsKey(key))
            return Movies.get(key);
        return null;
    }
}
