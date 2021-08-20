package input;

import java.io.IOException;

/**
 * Базовый интерфейс ввода данных.
 */
public interface InputManager {

    /**
     * Метод, который считывает строку данных.
     * @return строку данных.
     */
    String readLine() throws IOException;

    /**
     * Проверяет, имеется ли следующая строка для чтения.
     * @return true если есть следующая строка для чтения,
     *         false в противном случае.
     */
    boolean readyToRead() throws IOException;
}
