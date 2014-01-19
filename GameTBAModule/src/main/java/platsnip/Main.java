package platsnip;

import java.io.IOException;

public class Main {
    public static void main(String argv[]) throws IllegalAccessException, InstantiationException, IOException {
        Game g = new Game();
        g.gameLoop();
    }
}