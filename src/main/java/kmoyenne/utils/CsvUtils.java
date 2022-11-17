package kmoyenne.utils;

import kmoyenne.Barycentre;
import kmoyenne.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvUtils {

    public static List<Barycentre> extractBarycentres() {
        List<Barycentre> toReturn = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File("./resources/groups.csv"))){
            scanner.next(); // skip first line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                //Scan the line for tokens
                try (Scanner rowScanner = new Scanner(line)) {
                    rowScanner.useDelimiter(";");
                    String id = null;
                    int x = 0, y = 0;
                    while (rowScanner.hasNext()) {
                        String cell = rowScanner.next();
                        if (id == null) {
                            id = cell;
                        } else {
                            String[] coordinates = cell.split("[<>,]");
                            x = Integer.parseInt(coordinates[1]);
                            y = Integer.parseInt(coordinates[2]);
                        }
                    }
                    if (id != null) {
                        toReturn.add(new Barycentre(id, x,  y));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return toReturn;
    }

    public static List<Point> extractData() {
        List<Point> toReturn = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File("./resources/dataSimple.csv"))){
            scanner.next(); // skip first line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                //Scan the line for tokens
                try (Scanner rowScanner = new Scanner(line)) {
                    rowScanner.useDelimiter(";");
                    if (rowScanner.hasNext()) {
                        int largeur = 0, prix = 0;
                        largeur = Integer.parseInt(rowScanner.next());
                        prix = Integer.parseInt(rowScanner.next());
                        toReturn.add(new Point(largeur, prix));
                    }

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return toReturn;
    }
}
