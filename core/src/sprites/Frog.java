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

    public float maxLifeTime;
    public float lifeTime;
    public boolean isKilled;

    public Frog(Vector2 position, float timeToLive) {
        this.frogTexture = new Texture("frog.png");
        this.position = new Vector2(position);
        this.maxLifeTime = timeToLive;
        this.frogRectangle = new Rectangle(
                    this.position.x, this.position.y,
                    this.frogTexture.getWidth(), this.frogTexture.getHeight());
        this.lifeTime = 0;
        this.isKilled = false;
    }

    public Texture getFrogTexture() {
        return this.frogTexture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void resurrect(Vector2 position, float timeToLive) {
        this.position.set(position);
        this.maxLifeTime = timeToLive;
        this.lifeTime = 0;
        this.isKilled = false;
    }

    public void update(float deltaTime) {
        this.lifeTime += deltaTime;
    }

    public boolean isLifeTimeExpired() {
        return this.lifeTime >= this.maxLifeTime;
    }

    public boolean isFrogTouched(Vector2 touchVector) {
        return this.frogRectangle.contains(touchVector);
    }

    public void dispose() {
        this.frogTexture.dispose();
    }
}
