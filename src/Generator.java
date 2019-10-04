import java.util.Scanner;

public class Generator {
    public static void main(String... args) {
        Scanner kb = new Scanner(System.in);
        String input = "";
        Field.Builder builder = new Field.Builder();

        while (!input.equals("q")) {
            input = kb.nextLine();
            String[] parms = input.split(" ");
            try {
                if (parms.length == 3) {
                    int width = Integer.parseInt(parms[0]);
                    int height = Integer.parseInt(parms[1]);
                    double minePercent = Double.parseDouble(parms[2]);
                    System.out.println(builder.fromRandom(width, height, minePercent).getRawField());
                }
            } catch (NumberFormatException ignored) {

            }
        }
    }
}
