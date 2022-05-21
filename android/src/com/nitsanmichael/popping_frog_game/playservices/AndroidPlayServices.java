package com.nitsanmichael.popping_frog_game.playservices;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.badlogic.gdx.Gdx;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.example.games.basegameutils.GameHelper;
import com.nitsanmichael.popping_frog_game.AndroidLauncher;
import com.nitsanmichael.popping_frog_game.R;
import com.nitsanmichael.popping_frog_game.playservice.PlayServices;

import java.util.HashMap;


/**
 * Created by MichaelBond on 10/9/2016.
 */
public class AndroidPlayServices implements PlayServices, GameHelper.GameHelperListener {

    private AndroidLauncher mainActivity;
    private GameHelper gameHelper;
    private HashMap<LeaderBoard, String> leaderBoardToId;


    public AndroidPlayServices (AndroidLauncher mainActivity) {
        this.mainActivity = mainActivity;
        // Create the Google Api Client with access to the Play Games services.
        this.gameHelper = new GameHelper(this.mainActivity, GameHelper.CLIENT_GAMES);
        this.gameHelper.enableDebugLog(false);

        this.gameHelper.setup(this);
        // Disable automated sign in on startup.
        this.gameHelper.setMaxAutoSignInAttempts(0);

        this.leaderBoardToId = new HashMap<>();
        this.leaderBoardToId.put(LeaderBoard.HIGHEST_SCORE,
                    this.mainActivity.getString(R.string.leaderboard_highest_score));
        this.leaderBoardToId.put(LeaderBoard.HIGHEST_LEVEL,
                    this.mainActivity.getString(R.string.leaderboard_highest_level));
    }

    @Override
    public void onSignInFailed() {

    }

    @Override
    public void onSignInSucceeded() {
        // Submit highest score.
        submitScore(LeaderBoard.HIGHEST_SCORE,
                    this.mainActivity.game.data.getHighScore(LeaderBoard.HIGHEST_SCORE));
        // Submit highest level.
        submitScore(LeaderBoard.HIGHEST_LEVEL,
                    this.mainActivity.game.data.getHighScore(LeaderBoard.HIGHEST_LEVEL));
        // Update from the highest-score leader-board.
        updateScoreFromLeaderBoard(LeaderBoard.HIGHEST_SCORE);
        // Update from the highest-level leader-board.
        updateScoreFromLeaderBoard(LeaderBoard.HIGHEST_LEVEL);
    }

    private void updateScoreFromLeaderBoard(final LeaderBoard leaderBoard) {
        // Fetching results from leader-board and matching scores.
        PendingResult<Leaderboards.LoadPlayerScoreResult> result;
        result = Games.Leaderboards.loadCurrentPlayerLeaderboardScore(
                this.gameHelper.getApiClient(),
                this.leaderBoardToId.get(leaderBoard),
                LeaderboardVariant.TIME_SPAN_ALL_TIME,
                LeaderboardVariant.COLLECTION_PUBLIC);

        result.setResultCallback(new ResultCallback<Leaderboards.LoadPlayerScoreResult>() {
            @Override
            public void onResult(@NonNull Leaderboards.LoadPlayerScoreResult result) {
                // Check if valid score.
                if (GamesStatusCodes.STATUS_OK == result.getStatus().getStatusCode()
                        && result.getScore() != null) {

                    // Assign score fetched as best score.
                    mainActivity.game.data.syncLocalScore(
                                leaderBoard,
                                (int) result.getScore().getRawScore());
                }
            }
        });
    }

    public void onStart() {
        this.gameHelper.onStart(this.mainActivity);
    }

    public void onStop() {
        this.gameHelper.onStop();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.gameHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void signIn() {
        if (isSignedIn()) {
            // Already signed in.
            return;
        }
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
    public void submitScore(LeaderBoard leaderBoard, int highScore) {
        if (isSignedIn()) {
            Games.Leaderboards.submitScore(
                        this.gameHelper.getApiClient(),
                        this.leaderBoardToId.get(leaderBoard),
                        highScore);
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
    public void showLeaderBoards() {
        if (isSignedIn()) {
            this.mainActivity.startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(
                    this.gameHelper.getApiClient()), LeaderboardVariant.COLLECTION_PUBLIC);

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
