package platsnip;

import platsnip.model.GameModel;
import platsnip.model.State;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import platsnip.view.OpenGLView;
import platsnip.view.View;

public class Game {

    private GameModel gameModel;

    private View view;

    private static long timerTicksPerSecond = Sys.getTimerResolution();

    private boolean gameRunning = true;

    public Game() {
        view = new OpenGLView();
        view.initialize();
        gameModel = new GameModel();
    }

    void gameLoop() {
        long timeAccumulator = 0L;
        long time = 0L;
        long dt = 10L; // todo: rewrite to physics resolution and not hardcoded number in ms
        long currentTime = getTime();

        State state = gameModel.createNew();
        while (gameRunning) {
            long newTime = getTime();
            long frameTime = newTime - currentTime;
            currentTime = newTime;

            timeAccumulator += frameTime;

            while (timeAccumulator >= dt) {
                state = gameModel.integrate(state, time, dt);
                timeAccumulator -= dt;
                time += dt;
            }

            view.render(state);

            Display.update();
        }

        Display.destroy();
    }

    public static long getTime() {
        return (Sys.getTime() * 1000) / timerTicksPerSecond;
    }
}