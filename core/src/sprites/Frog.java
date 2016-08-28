package sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by MichaelBond on 8/25/2016.
 */
public class Frog {
    private Texture frogTexture;
    private Vector2 position;
    private Rectangle frogRectangle;

    public float lifeTime;
    public boolean isDead = false;

    public Frog(float xCord, float yCord) {
        this.frogTexture = new Texture("frog.png");
        this.position = new Vector2(xCord, yCord);
        this.frogRectangle = new Rectangle(
                    this.position.x, this.position.y,
                    this.frogTexture.getWidth(), this.frogTexture.getHeight());
        this.lifeTime = 0;
    }

    public Texture getFrogTexture() {
        return this.frogTexture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isFrogTouched(Vector2 touchVector) {
        return this.frogRectangle.contains(touchVector);

    }
}
