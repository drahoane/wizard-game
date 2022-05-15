import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class WizardTest {

    Wizard wizard;
    Enemy enemy;
    Chest chest;

    Handler handler;


    @BeforeEach
    public void setup() {
        handler = new Handler();
        wizard = new Wizard(64 * 32, 64 * 32, ID.Player, handler, new Game());
        enemy = new Enemy(64 * 32, 64 * 32, ID.Enemy, handler);
        chest = new Chest(64 * 32, 64 * 32, ID.Chest);
    }

    @Test
    public void Collision_CheckIfHealthGoesDown_returnTrue() {
        //ARRANGE
        handler.object.add(enemy);
        handler.object.add(wizard);

        int expectedValue = 99;

        //ACT
        wizard.collision();
        int result = wizard.hp;

        //ASSERT
        Assertions.assertEquals(expectedValue, result);
    }

    @Test
    public void Collision_ItemAddedToInventory_returnArrayWithItemInIt() {
        handler.object.add(wizard);
        wizard.chestsLeft = 1;
        handler.object.add(chest);

        String expectedValue = "Door key";

        wizard.collision();
        String result = wizard.inventory.get(0);

        Assertions.assertEquals(expectedValue, result);
    }


}
