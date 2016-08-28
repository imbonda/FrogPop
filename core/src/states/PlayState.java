package states;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import sprites.Frog;
import sprites.FrogGenerator;
import sprites.Hole;

public class PlayState extends State {
    private static final int VIRTUAL_WIDTH = 800;
    private static final int VIRTUAL_HEIGHT = 530;
  //  private static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;
    private static float FROG_LIFE_TIME_SECS =5.0f;
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
    private Frog frog;
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
        this.frog = FrogGenerator.generateFrog(this.holes);
        this.cam.setToOrtho(false,this.sprite.getWidth(),this.sprite.getHeight());

    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector2 touchVector = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            if (this.frog.isFrogTouched(touchVector) && !frog.isDead) {
                this.yourscore++;
                this.frog.isDead = true;
                if((this.yourscore%10)==0&&yourscore!=0) {
                    this.FROG_LIFE_TIME_SECS=(float)(FROG_LIFE_TIME_SECS*0.8);
                }
            }
            else {
                this.gsm.push(new GameOver(gsm));
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        this.frog.lifeTime += deltaTime;
        if (this.frog.lifeTime >= FROG_LIFE_TIME_SECS) {
            this.gsm.set(new GameOver(gsm));
         //   this.frog = FrogGenerator.generateFrog(this.holes);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
       //batch.setProjectionMatrix(this.cam.combined);
        sprite.draw(batch);
        drawHole(batch);
        drawFrog(batch);
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
        Time.draw(batch, "Time remain:"+ (int)(((FROG_LIFE_TIME_SECS- this.frog.lifeTime)*100)/(FROG_LIFE_TIME_SECS)),25,470);
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

    private void drawFrog(SpriteBatch batch) {
        if (!frog.isDead) {
            Vector2 frogPosition = this.frog.getPosition();
            batch.draw(this.frog.getFrogTexture(), frogPosition.x, frogPosition.y,0,0,100, 100-(int)(((FROG_LIFE_TIME_SECS- this.frog.lifeTime)*100)/(FROG_LIFE_TIME_SECS)));
            //batch.draw(this.frog.getFrogTexture(), frogPosition.x, frogPosition.y);
        }
        else {
            Vector2 frogPosition = this.frog.getPosition();
            this.frog = FrogGenerator.generateFrog(this.holes);
            batch.draw(this.frog.getFrogTexture(), frogPosition.x, frogPosition.y,0,0,100, 100-(int)(((FROG_LIFE_TIME_SECS- this.frog.lifeTime)*100)/(FROG_LIFE_TIME_SECS)));
        }
    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
    }

    public float getGameWitdh() {
        return VIRTUAL_WIDTH;
    }

    public float getGameHeight() {
        return VIRTUAL_HEIGHT;
    }

}
