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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import sprites.Frog;
import sprites.FrogGenerator;
import sprites.Hole;

public class PlayState extends State {
    private static final int VIRTUAL_WIDTH = 800;
    private static final int VIRTUAL_HEIGHT = 530;
  //  private static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;
    private static float FROG_LIFE_TIME_SECS =5.0f;
    private static final Vector2[] HOLES_POSITIONS = {
            new Vector2(50, 50), new Vector2(300, 50), new Vector2(50, 200), new Vector2(300, 200),new Vector2(550, 50),new Vector2(550, 200),new Vector2(50, 350),new Vector2(300, 350),new Vector2(550,350)
    };
    private Vector3 touches;
    BitmapFont scoreFont;
    BitmapFont Time;
    BitmapFont Level;
    BitmapFont Life;
    Sprite sprite;
    private Array<Hole> holes;
    private Frog[] frog;
    private int yourscore = 0;
    private int frogs=1;
    private int lives=3;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        sprite=new Sprite(new Texture (Gdx.files.internal("world.jpg")));
        this.scoreFont = new BitmapFont();
        this.Time = new BitmapFont();
        this.Level = new BitmapFont();
        this.Life = new BitmapFont();
        this.holes = new Array<Hole>();
        for (int i = 0; i < 9; ++i) {
            this.holes.add(new Hole(HOLES_POSITIONS[i].x, HOLES_POSITIONS[i].y));
        }
        frog=new Frog[9];
        for(int i=0;i<9;i++) {
            this.frog[i] = FrogGenerator.generateFrog(this.holes);
           // frog[i].lifeTime=FROG_LIFE_TIME_SECS;
        }
        this.cam.setToOrtho(false,VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
            }

    public void setViewport(Viewport v)
    {
        viewport=v;
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            touches=viewport.unproject( new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
            Vector2 touchVector = new Vector2(touches.x,touches.y);
            System.out.print("   Touch in:  "+ touches.x+"    " +touches.y);
            if (this.frog[0].isFrogTouched(touchVector) && !frog[0].isDead) {
                this.yourscore++;
                this.frog[0].isDead = true;
                if((this.yourscore%10)==0&&yourscore!=0)
                {
                    this.FROG_LIFE_TIME_SECS=(float)(FROG_LIFE_TIME_SECS*0.8);
                }
                                }
            else
            {
                lives--;
                if(lives==0) {
                    this.gsm.set(new GameOver(gsm));
                }
            }

        }

    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        for(int i=0;i<9;i++) {
            this.frog[i].lifeTime += deltaTime;
        }
        if (this.frog[0].lifeTime >= FROG_LIFE_TIME_SECS) {
            this.gsm.set(new GameOver(gsm));
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        Gdx.gl.glClearColor(171/255f, 107/255f, 72/255f, 1);
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
    private void drawGO(SpriteBatch batch)
    {
        Time.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        Time.draw(batch, "Time remain:"+ (int)(((FROG_LIFE_TIME_SECS- this.frog[0].lifeTime)*100)/(FROG_LIFE_TIME_SECS)),25,470);
        Life.setColor(Color.GREEN);
        Life.setColor(Color.GREEN);
        Life.draw(batch, "Lifes:"+lives,740,510);


    }
    private void drawLevel(SpriteBatch batch)
    {
        Level.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        Level.draw(batch, "Your Level :" + (int) yourscore /+10, 25, 495);
        if(yourscore%10==0&yourscore!=0) {
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
      //  if (yourscore >= 2) {
        //    Vector2 frogPosition = this.frog[1].getPosition();
          //  batch.draw(this.frog[1].getFrogTexture(),frogPosition.x, frogPosition.y, 0, 0, 100, 100-(int)(((FROG_LIFE_TIME_SECS- this.frog[1].lifeTime)*100)/(FROG_LIFE_TIME_SECS)));
        //}
        if (!frog[0].isDead) {
            Vector2 frogPosition = this.frog[0].getPosition();
            batch.draw(this.frog[0].getFrogTexture(), frogPosition.x, frogPosition.y,0,0,100, 100-(int)(((FROG_LIFE_TIME_SECS- this.frog[0].lifeTime)*100)/(FROG_LIFE_TIME_SECS)));
        }
        else {
            Vector2 frogPosition = this.frog[0].getPosition();
            this.frog[0] = FrogGenerator.generateFrog(this.holes);
            batch.draw(this.frog[0].getFrogTexture(), frogPosition.x, frogPosition.y, 0, 0, 100, 100 - (int) (((FROG_LIFE_TIME_SECS - this.frog[0].lifeTime) * 100) / (FROG_LIFE_TIME_SECS)));
        }
    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
    }
    public float getGameWitdh()
    {
        return VIRTUAL_WIDTH;}

    public float getGameHeight()
    {
        return VIRTUAL_HEIGHT;}

}
