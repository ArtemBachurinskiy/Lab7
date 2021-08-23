package input;

import output.OutputManager;
import entities.MovieGenre;

import java.io.IOException;

/**
 * Класс, предназначенный для чтения полей Movie из консоли.
 */
public class ConsoleMovieReader implements MovieReader {
    private InputManager inputManager;
    private OutputManager outputManager;
    private Validator validator;

    /**
     * @param inputManager менеджер ввода данных
     * @param outputManager менеджер вывода данных
     * @param validator объект типа Validator, предназначенный для валидации введённых полей
     */
    public ConsoleMovieReader(InputManager inputManager, OutputManager outputManager, Validator validator) {
        this.inputManager = inputManager;
        this.outputManager = outputManager;
        this.validator = validator;
    }

    @Override
    public String readName() {
        String name;
        outputManager.printlnMessage("Введите поле name: ");
        while (true) {
            try {
                name = inputManager.readLine();
                name = name.trim();
                if (name.isEmpty()) {
                    outputManager.printlnErrorMessage("Полю name не может быть пустой строкой! Повторите ввод.");
                    continue;
                }
                break;
            }
            catch (IOException e) {}
        }
        return name;
    }

    @Override
    public Integer readCoordinateX(){
        Integer x;
        outputManager.printlnMessage("Введите координату x: ");
        while (true) {
            try {
                x = Integer.parseInt(inputManager.readLine().trim());
                if (validator.validateCoordinateX(x))
                    break;
            } catch (NumberFormatException | IOException e) {
                outputManager.printlnErrorMessage("Координата x должна быть значением типа Integer! Повторите ввод.");
            }
        }
        return x;
    }

    @Override
    public Double readCoordinateY() {
        Double y;
        outputManager.printlnMessage("Введите координату y: ");
        while (true) {
            try {
                y = Double.parseDouble(inputManager.readLine().trim());
                if (validator.validateCoordinateY(y))
                    break;
            } catch (NumberFormatException | IOException e) {
                outputManager.printlnErrorMessage("Координата y должна быть значением типа Double! Повторите ввод.");
            }
        }
        return y;
    }

    @Override
    public int readOscarsCount() {
        int oscarsCount;
        outputManager.printlnMessage("Введите поле oscarsCount: ");
        while (true) {
            try {
                oscarsCount = Integer.parseInt(inputManager.readLine().trim());
                if (validator.validateOscarsCount(oscarsCount))
                    break;
            } catch (NumberFormatException | IOException e) {
                outputManager.printlnErrorMessage("Поле oscarsCount должно быть значением типа int! Повторите ввод.");
            }
        }
        return oscarsCount;
    }

    @Override
    public long readGoldenPalmCount() {
        long goldenPalmCount;
        outputManager.printlnMessage("Введите поле goldenPalmCount: ");
        while (true) {
            try {
                goldenPalmCount = Long.parseLong(inputManager.readLine().trim());
                if (validator.validateGoldenPalmCount(goldenPalmCount))
                    break;
            } catch (NumberFormatException | IOException e) {
                outputManager.printlnErrorMessage("Поле goldenPalmCount должно быть значением типа long! Повторите ввод.");
            }
        }
        return goldenPalmCount;
    }

    @Override
    public String readTagline() throws IOException {
        String tagline;
        outputManager.printlnMessage("Введите поле tagline: ");
        while (true) {
            tagline = inputManager.readLine();
            tagline = tagline.trim();
            if (tagline.isEmpty()) {
                tagline = null;
                outputManager.printlnMessage("Полю tagline присвоено значение null.");
                break;
            }
            else if (validator.validateTagline(tagline))
                break;
        }
        return tagline;
    }

    @Override
    public MovieGenre readGenre() {
        MovieGenre genre;
        outputManager.printlnMessage("Введите поле genre: " + '\n' + "(возможные варианты ввода: ACTION, DRAMA, ADVENTURE, THRILLER)");
        while (true) {
            try {
                genre = MovieGenre.valueOf(inputManager.readLine().trim());
                break;
            } catch (IllegalArgumentException | IOException e) {
                outputManager.printlnErrorMessage("Возможные варианты ввода: ACTION, DRAMA, ADVENTURE, THRILLER. Повторите ввод.");
            }
        }
        return genre;
    }
}
