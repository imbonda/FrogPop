package sprites;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * Created by MichaelBond on 8/25/2016.
 */

public class FrogGenerator {
    private int fullhole=0;


    public static Frog generateFrog(Array<Hole> holes) {
      //  if(fullhole<9) {
            int randHoleIndex = (new Random()).nextInt(100) % holes.size;
            Vector2 chosenHolePosition = holes.get(randHoleIndex).getPosition();
            Vector2 frogPosition = new Vector2(chosenHolePosition.x + 50, chosenHolePosition.y + 20);
         //   fullhole++;
            return new Frog(frogPosition.x, frogPosition.y);
   //     }
      //  return new Frog(frogPosition.x, frogPosition.y);
    }
}
