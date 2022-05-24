import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;


public class WizardTest {

    Wizard wizard;
    Enemy enemy;
    Chest chest;
    Sheet sh;
    Handler handler;
    Game game = new Game();
    LinkedList<GameObject> objectsToBeRemoved = new LinkedList<>();


    @BeforeEach
    public void setup() {
        handler = new Handler();
        handler.objectsToBeRemoved = objectsToBeRemoved;
        wizard = new Wizard(64 * 32, 64 * 32, ID.Player, handler, game, sh);
        game.ply = wizard;
        enemy = new Enemy(64 * 32, 64 * 32, ID.Enemy, handler, sh);
        chest = new Chest(64 * 32, 64 * 32, ID.Chest, handler, game, sh);
    }

    @Test
    public void Collision_CheckIfHealthGoesDown_returnTrue() {
        //ARRANGE
        handler.objects.add(enemy);
        handler.objects.add(wizard);

        int expectedValue = 99;

        //ACT
        wizard.tick();
        int result = wizard.hp;

        //ASSERT
        Assertions.assertEquals(expectedValue, result);
    }

    @Test
    public void Collision_ItemAddedToInventory_returnArrayWithItemInIt() {
        handler.objects.add(wizard);
        wizard.chestsLeft = 1;
        handler.objects.add(chest);

        String expectedValue = "Door key";

        chest.tick();
        String result = wizard.inventory.get(0);

        Assertions.assertEquals(expectedValue, result);
    }


}
