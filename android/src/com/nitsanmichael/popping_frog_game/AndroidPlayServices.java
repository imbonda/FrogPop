package com.nitsanmichael.popping_frog_game;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.badlogic.gdx.Gdx;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.nitsanmichael.popping_frog_game.playservice.PlayServices;

/**
 * Created by MichaelBond on 10/9/2016.
 */
public class AndroidPlayServices implements PlayServices {

    private final static int REQUEST_CODE = 1;

    private Activity mainActivity;
    private GameHelper gameHelper;


    AndroidPlayServices (Activity mainActivity) {
        this.mainActivity = mainActivity;
        // Create the Google Api Client with access to the Play Games services
        this.gameHelper = new GameHelper(this.mainActivity, GameHelper.CLIENT_GAMES);
        this.gameHelper.enableDebugLog(false);

        GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
            @Override
            public void onSignInFailed(){ }

            @Override
            public void onSignInSucceeded(){ }
        };
        this.gameHelper.setup(gameHelperListener);
    }

    protected void onStart() {
        this.gameHelper.onStart(this.mainActivity);
    }

    protected void onStop() {
        this.gameHelper.onStop();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.gameHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void signIn() {
        try {
            this.mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.beginUserInitiatedSignIn();
                }
            });
        }
        catch (Exception e) {
            Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void signOut() {
        try {
            this.mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run()
                {
                    gameHelper.signOut();
                }
            });
        }
        catch (Exception e) {
            Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void rateGame() {
        String str = "Your PlayStore Link";
        this.mainActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
    }

    @Override
    public void unlockAchievement() {
//		Games.Achievements.unlock(gameHelper.getApiClient(),
//				getString(R.string.achievement_dum_dum));
    }

    @Override
    public void submitScore(int highScore) {
        if (isSignedIn()) {
            Games.Leaderboards.submitScore(gameHelper.getApiClient(),
                    this.mainActivity.getString(R.string.leaderboard_highest), highScore);
        }
    }

    @Override
    public void showAchievement() {
        if (isSignedIn()) {
//			startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient(),
//					getString(R.string.achievement_dum_dum)), REQUEST_CODE);
        }
        else {
            signIn();
        }
    }

    @Override
    public void showScore() {
        if (isSignedIn()) {
            this.mainActivity.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
                    this.gameHelper.getApiClient(),
                    this.mainActivity.getString(R.string.leaderboard_highest)), REQUEST_CODE);
        }
        else {
            signIn();
        }
    }

    @Override
    public boolean isSignedIn() {
        return this.gameHelper.isSignedIn();
    }

}
