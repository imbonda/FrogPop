package sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by MichaelBond on 8/25/2016.
 */
public class Hole {
    private Texture holeTexture;
    private Vector2 position;

    public Hole(float xCord, float yCord) {
        this.holeTexture = new Texture("holen.png");
        this.position = new Vector2(xCord, yCord);
    }

    public Texture getHoleTexture() {
        return this.holeTexture;
    }

    public Vector2 getPosition() {
        return position;
    }
}
