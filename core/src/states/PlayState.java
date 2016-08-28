package states;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import sprites.Frog;
import sprites.FrogManager;
import sprites.Hole;

public class PlayState extends State {

    private static final int VIRTUAL_WIDTH = 800;
    private static final int VIRTUAL_HEIGHT = 530;
  //  private static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;
    private static final float INITIAL_FROG_MAX_LIFE_TIME_SECS = 5.0f;
    private static final float FROG_LIFE_TIME_DECREASE_FACTOR = 0.8f;
    private static final Vector2[] HOLES_POSITIONS = {
                new Vector2(50, 50), new Vector2(300, 50), new Vector2(50, 200),
                new Vector2(300, 200), new Vector2(550, 50), new Vector2(550, 200),
                new Vector2(50, 350), new Vector2(300, 350), new Vector2(550,350)
    };

    BitmapFont scoreFont;
    BitmapFont Time;
    BitmapFont Level;
    Sprite sprite;
    private Array<Hole> holes;
    private FrogManager frogManager;
    private int yourscore = 0;

    public PlayState(GameStateManager gsm) {
        super(gsm);
      //  sb = new SpriteBatch();
      //  cam = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        sprite=new Sprite(new Texture (Gdx.files.internal("world.jpg")));
        this.scoreFont = new BitmapFont();
        this.Time = new BitmapFont();
        this.Level = new BitmapFont();
        this.holes = new Array<Hole>();
        for (int i = 0; i < 9; ++i) {
            this.holes.add(new Hole(HOLES_POSITIONS[i].x, HOLES_POSITIONS[i].y));
        }
        this.frogManager = new FrogManager(this.holes, INITIAL_FROG_MAX_LIFE_TIME_SECS);
        this.frogManager.addFrog();
        this.cam.setToOrtho(false,this.sprite.getWidth(),this.sprite.getHeight());
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector2 touchVector = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            for (Frog frog: this.frogManager.getFrogs()) {
                if (frog.isFrogTouched(touchVector) && !frog.isLifeTimeExpired()) {
                    this.yourscore++;
                    frog.isKilled = true;
                    if((this.yourscore%10)==0&&yourscore!=0) {
                        this.frogManager.decreaseFrogMaxLifeTime(FROG_LIFE_TIME_DECREASE_FACTOR);
                    }
                }
                else {
                    this.gsm.push(new GameOver(gsm));
                    return;
                }
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();

        this.frogManager.update(deltaTime);
        for (Frog frog : this.frogManager.getFrogs()) {
            if (frog.isLifeTimeExpired()) {
                this.gsm.set(new GameOver(gsm));
                return;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
       //batch.setProjectionMatrix(this.cam.combined);
        sprite.draw(batch);
        drawHole(batch);
        drawFrogs(batch);
        drawScore(batch);
        drawGO(batch);
        drawLevel(batch);
        batch.end();
    }

    private void drawScore(SpriteBatch batch) {
        scoreFont.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        scoreFont.draw(batch, "Your Mother Fucking Score: " + yourscore, 25, 520);
    }

    private void drawGO(SpriteBatch batch) {
        Time.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        // TODO: 8/29/2016 fix timer, or remove if unnecessary
        //Time.draw(batch, "Time remain:"+ (int)(((FROG_LIFE_TIME_SECS- this.frog.lifeTime)*100)/(FROG_LIFE_TIME_SECS)),25,470);
    }

    private void drawLevel(SpriteBatch batch) {
        Level.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        Level.draw(batch, "Your Level :" + (int) yourscore /+10, 25, 495);
        if(yourscore % 10 == 0 & yourscore != 0) {
            Level.setColor(Color.RED);
           // Level.getData().setScale(1,1);
            Level.draw(batch, "Level up!", VIRTUAL_WIDTH/2-15, VIRTUAL_WIDTH/2+70);
        }
    }
    private void drawHole(SpriteBatch batch) {
        for (Hole hole: this.holes) {
            Vector2 holePosition = hole.getPosition();
            batch.draw(hole.getHoleTexture(), holePosition.x, holePosition.y);
        }
    }

    public void drawFrogs(SpriteBatch batch) {
        for (Frog frog: this.frogManager.getFrogs()) {
            if (!frog.isKilled && !frog.isLifeTimeExpired()) {
                batch.draw(frog.getFrogTexture(), frog.getPosition().x, frog.getPosition().y,
                            0, 0, 100, 100-(int)(((frog.maxLifeTime- frog.lifeTime)*100)/(frog.maxLifeTime)));
            }
        }
    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
        this.frogManager.dispose();
    }

    public float getGameWitdh() {
        return VIRTUAL_WIDTH;
    }

    public float getGameHeight() {
        return VIRTUAL_HEIGHT;
    }

}
