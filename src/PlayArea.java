import java.awt.*;

public class PlayArea extends Collideable{
    public PlayArea(int paddingTop, int paddingBottom, int paddingLeft, int paddingRight, int width, int height) {
        bodyParts.add(new Rectangle(paddingLeft, paddingTop, width-(paddingRight*2), height-(paddingBottom*2)));
    }
}
