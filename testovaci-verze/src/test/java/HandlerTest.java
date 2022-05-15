import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.mockito.Mockito.*;

public class HandlerTest {

    Wizard wizard;
    Chest chest;
    Handler handler;
    Game game;


    @BeforeEach
    public void setup() {
        wizard = new Wizard(64 * 32, 64 * 32, ID.Player, handler, new Game());
    }

    @Test
    public void RemoveObject_RemoveSelectedGameObject_PassesIfCalledOnce() {
        Handler mockHandler = mock(Handler.class);
        mockHandler.removeObject(wizard);

        verify(mockHandler).removeObject(wizard);
    }

    @Test
    public void AddObject_AddSelectedGameObject_PassesIfCalledNineTimes() {
        Game g = new Game();
        ImageLoader loader = new ImageLoader();
        BufferedImage level = loader.loadImage("/lvl1.png");
        Handler mockHandler = mock(Handler.class);
        g.loadLevel(level);
        for (int i = 0; i < 9; i++) {
            mockHandler.addObject(chest = new Chest(64 * 32, 64 * 32, ID.Chest));
        }

        verify(mockHandler, times(9)).addObject(any(Chest.class));
    }
}
