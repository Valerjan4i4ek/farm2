import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Arrays;
import static java.lang.System.out;

public class Game {

    private final static String JSON_FILE_NAME = "src/plants.json";

    private final Scanner scanner;

    private final List<Plant> plants;
    private final List<Field> fields;

    private int cash;
    //public static MySQLClass2 mySQLClass2 = new MySQLClass2();
    public MySQLCache cache;

    public static void main(String[] args) throws FileNotFoundException {
        List<Plant> plantList = jsonToPlants(JSON_FILE_NAME);
        for (int i = 0; i < plantList.size(); i++) {
            out.println((i + 1) + " " + plantList.get(i));
        }

        new Game(jsonToPlants(JSON_FILE_NAME), 100, 8).start();
    }

    private static List<Plant> jsonToPlants(String fileName) throws FileNotFoundException {
        return Arrays.asList(new Gson().fromJson(new FileReader(fileName), Plant[].class));
    }

    public Game(List<Plant> plants, int cash, int fieldSize) {
        this.cash = cash;
        this.plants = plants;
        this.scanner = new Scanner(System.in);
        this.fields = new CopyOnWriteArrayList<>();
        for (int i = 0; i < fieldSize; i++) fields.add(new Field());
        cache = new MySQLCache();
    }

    public void start() {
        while (true) {
            out.println(fields);
            out.println("YOUR CASH : " + cash);
            out.println("Enter cell for your plant (1-8)");
            validField(scanner.nextLine()).ifPresent(fieldNumber -> {
                Field field = fields.get(fieldNumber);
                out.println("Enter plants number (1-" + plants.size() + ")");
                if (field.isEmpty()) {
                    validPlant(scanner.nextLine()).ifPresent(plantNumber -> {
                        Plant plant = plants.get(plantNumber);
                        execute(() -> getHarvest(fieldNumber), plant.getTime());
                        cash -= plant.getSeedPrice();
                        fields.set(fieldNumber, field.updateField(plant));
                        cache.addPlant(fieldNumber, plant.getName());
                    });
                } else {
                    cash += field.getHarvestPrice();
                    fields.set(fieldNumber, field.updateField());
                    cache.addPlant(fieldNumber, "stalo pusto");
                }
            });
        }
    }

    private Optional<Integer> validPlant(String userInput) {
        try{
            Integer number = Integer.valueOf(userInput)-1;
            if (number < 0 || number >= plants.size()) return printAndReturnOptional("OLOLO! YOU WERE ABROAD");
            if (cash < plants.get(number).getSeedPrice()) return printAndReturnOptional("YOU HAVEN'T ENOUGH MONEY");
            return Optional.of(number);
        }catch(NumberFormatException e){
            return printAndReturnOptional("OLOLO! INCORRECT INPUT");
        }
    }

    private Optional<Integer> validField(String userInput) {
        try{
            Integer number = Integer.valueOf(userInput)-1;
            if (isFieldFull()) return printAndReturnOptional("JUST WAIT");
            if (number < 0 || number >= fields.size()) return printAndReturnOptional("OLOLO! YOU WERE ABROAD");
            if (fields.get(number).isFull()) return printAndReturnOptional("OLOLO! FIELD NOT EMPTY");
            return Optional.of(number);
        }catch(NumberFormatException e){
            return printAndReturnOptional("OLOLO! INCORRECT INPUT");
        }
    }

    private Optional printAndReturnOptional(String arg) {
        out.println(arg);
        return Optional.empty();
    }

    public void  getHarvest(int fieldNumber) {
        fields.set(fieldNumber, fields.get(fieldNumber).updateField());
        out.println(fields);
    }

    private void execute(Runnable task, long delaySec) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                task.run();
                timer.cancel();
            }
        };
        timer.schedule(timerTask, delaySec * 1000);
    }

    private boolean isFieldFull() {
        return !fields.stream().anyMatch(cell->cell.isEmpty());
    }

}
