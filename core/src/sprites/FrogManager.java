package sprites;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Set;

import sprites.Exceptions.OverpopulatedHolesException;


/**
 * Created by MichaelBond on 8/28/2016.
 */
public class FrogManager {

    private static final int FROG_OFFSET_X = 70;
    private static final int FROG_OFFSET_Y = 20;
    private static final float INITIAL_FROG_MAX_LIFE_TIME_SECS = 5.0f;

    private float frogMaxLifeTime;
    private HashMap<Frog, Integer> frogToHoleIndexMap;
    private Array<Hole> holes;
    private Array<Integer> unpopulatedHolesIndexes;


    public FrogManager(Array<Hole> holes) {
        this.frogMaxLifeTime = INITIAL_FROG_MAX_LIFE_TIME_SECS;
        this.holes = holes;
        this.frogToHoleIndexMap = new HashMap<Frog, Integer>();
        this.unpopulatedHolesIndexes = new Array<Integer>();
        for (int i = 0; i < holes.size; ++i) {
            this.unpopulatedHolesIndexes.add(i);
        }
    }

    public void addFrog() {
        int randomHoleIndex = this.getRandomUnpopulatedHoleIndex();
        Vector2 frogPosition = this.getFrogPosition(randomHoleIndex);
        // Add a new frog positioned at the chosen hole.
        this.frogToHoleIndexMap.put(new Frog(frogPosition, this.frogMaxLifeTime), randomHoleIndex);
        // The hole is now populated.
        this.unpopulatedHolesIndexes.removeValue(randomHoleIndex, true);
    }

    private int getRandomUnpopulatedHoleIndex() {
        Integer unpopulatedHoleIndex = this.unpopulatedHolesIndexes.random();
        if (null == unpopulatedHolesIndexes) {
            throw new OverpopulatedHolesException("There are no free holes to populate");
        }
        return unpopulatedHoleIndex;
    }

    private Vector2 getFrogPosition(int holeIndex) {
        Vector2 holePosition = this.holes.get(holeIndex).getPosition();
        return new Vector2(
                holePosition.x + FROG_OFFSET_X, holePosition.y + FROG_OFFSET_Y);
    }

//    private float getRandomLifeTime() {
//        //..
//    }

    public Set<Frog> getFrogs() {
        return this.frogToHoleIndexMap.keySet();
    }

    public void decreaseFrogMaxLifeTime(float decreaseFactor) {
        this.frogMaxLifeTime *= decreaseFactor;
    }

    public void update(float deltaTime) {
        for (Frog frog: this.getFrogs()) {
            frog.update(deltaTime);
            if (frog.isLifeTimeExpired() && frog.isKilled) {
                System.out.println("Dead");
                int frogOldHole = this.frogToHoleIndexMap.get(frog);
                int randomHoleIndex = this.getRandomUnpopulatedHoleIndex();
                Vector2 frogPosition = this.getFrogPosition(randomHoleIndex);
                this.frogToHoleIndexMap.put(frog, randomHoleIndex);
                frog.resurrect(frogPosition, this.frogMaxLifeTime);
                this.unpopulatedHolesIndexes.removeValue(randomHoleIndex, true);
                this.unpopulatedHolesIndexes.add(frogOldHole);
            }
        }
    }

    public void dispose() {
        for (Frog frog: this.getFrogs()) {
            frog.dispose();
        }
    }

}
