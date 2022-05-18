import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class MouseInputTest {


    @Test
    public void MouseOver_ReturnBoolWhetherMouseIsWithinSaveGameRectangle_ReturnTrue() {
        int mx = 500;
        int my = 15;
        MouseInput mockMouseInput = mock(MouseInput.class);
        mockMouseInput.mouseOver(500, 15, 400, 5, 200,20);

        when(mockMouseInput.mouseOver(500, 15, 400, 5, 200,20)).thenReturn(true);
    }
}
