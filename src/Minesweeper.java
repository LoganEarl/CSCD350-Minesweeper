
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public class Minesweeper {
    public static void main(String... args) throws IOException {
        String input;
        if (args.length == 1 && !args[0].isEmpty())
            input = Files.readString(Path.of(args[0]));
        else
            input = new String(System.in.readAllBytes());

        Field.Builder builder = new Field.Builder();
        Collection<Field> allFields = builder.fromInput(input);
        int counter = 1;
        for (Field f : allFields) {
            if(f != null) {
                System.out.printf("\nField #%d:\n", counter++);
                System.out.println(f);
            }
        }
    }
}