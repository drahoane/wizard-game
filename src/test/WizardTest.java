import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class WizardTest {

    Wizard wizard;
    Enemy enemy;
    Chest chest;
    Sheet sh;
    Handler handler;


    @BeforeEach
    public void setup() {
        handler = new Handler();
        wizard = new Wizard(64 * 32, 64 * 32, ID.Player, handler, new Game(), sh);
        enemy = new Enemy(64 * 32, 64 * 32, ID.Enemy, handler, sh);
        chest = new Chest(64 * 32, 64 * 32, ID.Chest, sh);
    }

    @Test
    public void Collision_CheckIfHealthGoesDown_returnTrue() {
        //ARRANGE
        handler.objects.add(enemy);
        handler.objects.add(wizard);

        int expectedValue = 99;

        //ACT
        wizard.collision();
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

        wizard.collision();
        String result = wizard.inventory.get(0);

        Assertions.assertEquals(expectedValue, result);
    }


}
