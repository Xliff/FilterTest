package com.example.cliff.filtertest;

import android.app.*;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.cliff.filtertest.app.Activity;
import com.example.cliff.filtertest.data.PlayerData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {
    private DragDropListView theList;
    private CustomAdapter theAdapter;
    private boolean loadCompleted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        loadCompleted = false;

        final LoaderTask t = new LoaderTask();
        final View theView = findViewById(R.id.MainScreen);
        theAdapter = new CustomAdapter(context);

        // cw: Do not start background task until after layout is completed.
        theView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Call only ONCE!
                theView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                theList = (DragDropListView) theView.findViewById(R.id.listView);
                theList.setAdapter(theAdapter);
                t.execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        context = null;
        super.onDestroy();
    }

    //region Prevent activity from restarting before loading has been completed.
    @Override
    public void onBackPressed() {
        if (loadCompleted) {
            super.onBackPressed();
        }
    }


    //endregion


    final private class LoaderTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDlg;
        boolean caughtError;

        // cw: Might want this in onPostExecute instead of doInBackground(). Still haven't
        // decided yet, which is why this is not a local variable.
        HashMap<Integer, PlayerData> draftList;
        ArrayList<PlayerData> draftArray;
        int oldRotation;
        String message;
        boolean indeterminate = true;
        int pMax = -1;
        int pIdx = -1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDlg = new ProgressDialog(context);
            pDlg.setMessage("Loading Player data...");
            pDlg.setCancelable(false);
            pDlg.setCanceledOnTouchOutside(false);
            pDlg.setIndeterminate(true);
            pDlg.show();

            draftList = new HashMap<>();
            draftArray = new ArrayList<>();
            oldRotation = getRequestedOrientation();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

            caughtError = false;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // cw: XXX - If we want a SplashScreen, then we should probably rethink this implementation!
            // It looks like a final inner class extending AsyncTask will be mandatory.

            // For now, we load from local assets.
            AssetManager assetManager = context.getAssets();
            String jsonString = null;
            InputStream ims = null;

            try {
                ims = assetManager.open("DraftRankings.json");
                byte[] fileData = new byte[ims.available()];
                ims.read(fileData);
                jsonString = new String(fileData);
            } catch (IOException e) {
                e.printStackTrace();
                caughtError = true;
            } finally {
                try {
                    if (ims != null) ims.close();
                } catch (Exception squish) {
                    // Squash.
                }
            }
            if (caughtError)
                return null;

            try {
                message = "Parsing player data...";
                JSONObject jsonData = new JSONObject(jsonString);
                JSONArray players = jsonData.getJSONArray("DraftRankings");
                publishProgress();

                message = "Populating players...";
                publishProgress();
                indeterminate = false;
                pMax = players.length();

                for (pIdx = 0; pIdx < players.length(); pIdx++) {
                    JSONObject jP = players.getJSONObject(pIdx);
                    int playerID = jP.getInt("playerId");

                    PlayerData p = new PlayerData(
                            playerID,
                            jP.getString("position"),
                            jP.getString("displayName"),
                            jP.getString("fname"),
                            jP.getString("lname"),
                            jP.getString("team"),
                            jP.getInt("byeWeek"),
                            jP.getDouble("standDev"),
                            jP.getDouble("nerdRank"),
                            jP.getInt("positionRank"),
                            jP.getInt("overallRank")
                    );

                    draftList.put(playerID, p);
                    // cw: We duplicate effort for fear of performance issues. Can revisit later once
                    // we optimize.
                    draftArray.add(p);

                    if (context == null)
                        return null;
                }

                if (context == null)
                    return null;

                // cw: Store players data in Application Data Store.
                // Should this be in onPostExecute() or remain out of the UI thread?
                Application app = (Application) ((Activity)context).getApplication();
                app.applicationData.put(
                        "draftData",
                        draftList
                );
            } catch (JSONException e) {
                e.printStackTrace();
                caughtError = true;
            }
            if (caughtError)
                return null;

            // cw: Yes, the previous line may not be needed, but there may be more code to come
            // after it...
            return null;
        }

        protected void onProgressUpdate() {
            pDlg.setMessage(message);
            if (pMax > 0)
                pDlg.setMax(pMax);
            if (pIdx > 0)
                pDlg.setProgress(pIdx);
        }

        protected void onPostExecute(Void v) {
            if (context == null)
                return;

            if (caughtError) {
                ((Activity)context).finish();
            }

            theAdapter.setList(draftArray);
            pDlg.dismiss();
            loadCompleted = true;
            setRequestedOrientation(oldRotation);

            super.onPostExecute(v);
        }

    }
}
