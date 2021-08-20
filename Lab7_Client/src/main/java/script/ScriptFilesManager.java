package script;

import java.util.ArrayList;

/**
 * Класс, предназначенный для управлнеения файлами-скриптами
 */
public class ScriptFilesManager {
    private ArrayList<String> usedScriptFiles = new ArrayList<>();

    /**
     * Метод, который возвращает ArrayList использованных файлов-скриптов во избежание рекурсии.
     * @return ArrayList использованных файлов-скриптов
     */
    public ArrayList<String> getUsedScriptFiles() {
        return usedScriptFiles;
    }
}