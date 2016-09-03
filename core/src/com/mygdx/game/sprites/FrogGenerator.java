package com.mygdx.game.sprites;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


import java.util.Random;

/**
 * Created by MichaelBond on 8/25/2016.
 */

public class FrogGenerator {
    private Vector2[] PopulatedHolesIndexes={new Vector2(0, 0), new Vector2(0, 0), new Vector2(0, 0), new Vector2(0, 0),new Vector2(0, 0),new Vector2(0, 0),new Vector2(0, 0),new Vector2(0,0),new Vector2(0,0)};
    //  Vector2 chosenHolePosition;
    public FrogGenerator()
    {

    }

    public Frog generateFrog(Array<Hole> holes) {
        int i=0;
        int randHoleIndex = (new Random()).nextInt(100) % holes.size;
        Vector2 chosenHolePosition = holes.get(randHoleIndex).getPosition();
        Vector2 frogPosition = new Vector2(chosenHolePosition.x + 50, chosenHolePosition.y + 20);
        while((PopulatedHolesIndexes[i].x==frogPosition.x) && (PopulatedHolesIndexes[i].y==frogPosition.y)&&(i<9)) {
            System.out.print("contain");
            randHoleIndex = (new Random()).nextInt(100) % holes.size;
            chosenHolePosition = holes.get(randHoleIndex).getPosition();
            frogPosition = new Vector2(chosenHolePosition.x + 50, chosenHolePosition.y + 20);
            i++;
        }
        //System.out.print("index "+i);
        PopulatedHolesIndexes[i]=frogPosition;
        for(int j=0;j<9;j++){
        System.out.print("  places"+PopulatedHolesIndexes[j]+"   frogPosition   "+frogPosition);
        }
        //Vector2 chosenHolePosition = holes.get(randHoleIndex).getPosition();
        //Vector2 frogPosition = new Vector2(chosenHolePosition.x + 50, chosenHolePosition.y + 20);
        return new Frog(frogPosition.x, frogPosition.y);
    }
    public void unpopulate(Vector2 disableFrog,Array<Hole> holes)
    {
        // Vector2 chosenHolePosition = holes.get(randHoleIndex).getPosition();
    }

}

