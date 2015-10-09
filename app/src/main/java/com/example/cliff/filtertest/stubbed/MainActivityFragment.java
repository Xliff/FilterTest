package com.example.cliff.filtertest.stubbed;

// cw: Disabled class, left as a reference.

/*
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.example.cliff.filtertest.Application;
import com.example.cliff.filtertest.data.PlayerData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

//
// A placeholder fragment containing a simple view.
//
public class MainActivityFragment extends Fragment {
    Activity context;


    public MainActivityFragment() {
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // cw: XXX - Should probaly take steps to ignore back button and rotation events until background task is completed.

        return theView;
    }

    public void onDestroy() {
        context = null;
    }

    @Override
    public void onBackPressed() {
        if (loadCompleted) {
            super.onBackPressed();
        }
    }

    final private class LoaderTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDlg;
        boolean caughtError;

        // cw: Might want this in onPostExecute instead of doInBackground(). Still haven't
        // decided yet, which is why this is not a local variable.
        HashMap<Integer, PlayerData> draftList;
        ArrayList<PlayerData> draftArray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDlg = new ProgressDialog(context);
            pDlg.setMessage("Loading Player data...");
            pDlg.setCancelable(false);
            pDlg.setCanceledOnTouchOutside(false);
            pDlg.setIndeterminate(true);
            draftList = new HashMap<>();

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
                pDlg.setMessage("Parsing playuer data...");
                JSONObject jsonData = new JSONObject(jsonString);
                JSONArray players = jsonData.getJSONArray("DraftRankings");

                pDlg.setMessage("Populating players...");
                pDlg.setIndeterminate(false);
                pDlg.setMax(players.length());
                for (int pIdx = 0; pIdx < players.length(); pIdx++) {
                    JSONObject jP = players.getJSONObject(pIdx);
                    int playerID = jP.getInt("playerId");

                    PlayerData p = new PlayerData(
                            playerID,
                            jP.getString("displayName"),
                            jP.getString("position"),
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
                    pDlg.setProgress(pIdx + 1);
                }

                // cw: Store players data in Application Data Store.
                // Should this be in onPostExecute() or remain out of the UI thread?
                ((Application)context.getApplication()).applicationData.put(
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

        protected void onPostExecute(Void v) {
            if (context == null)
                return;

            if (caughtError) {
                context.finish();
            }

            theAdapter.setList(draftArray);
            pDlg.dismiss();

            super.onPostExecute(v);
        }

    }

}
*/