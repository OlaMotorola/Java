package org.starmap.view;

import org.starmap.model.Constellation;
import org.starmap.model.Star;
import org.starmap.utils.FileManagementController;

public class FileManagementView {

    private final FileManagementController fileManagementController;

    public FileManagementView(FileManagementController fileManagementController) {
        this.fileManagementController = fileManagementController;
    }

    public void loadStarsFromFile(String filePath) {
        // Wywołanie metody wczytującej gwiazdy z pliku z kontrolera
        List<Star> stars = fileManagementController.loadDataFromFile(filePath);

        // Możesz dalej obsłużyć te gwiazdy np. wyświetlić, przekazać do innych modułów itp.
    }

    public void saveStarsToFile(String filePath, List<Star> stars) {
        // Wywołanie metody zapisującej gwiazdy do pliku z kontrolera
        fileManagementController.saveDataToFile(filePath, stars);

        // Potwierdzenie zapisu lub inne działania
    }
}
