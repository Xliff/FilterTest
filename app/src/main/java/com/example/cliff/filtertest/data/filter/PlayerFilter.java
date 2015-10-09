package com.example.cliff.filtertest.data.filter;

import com.example.cliff.filtertest.data.PlayerData;

import java.util.ArrayList;

/**
 * Created by Cliff on 10/4/2015.
 *
 * Filter: If player is in players, then filter them.
 */
public class PlayerFilter extends Filter<PlayerData> {
    ArrayList<PlayerData> players = new ArrayList<PlayerData>();

    public void addSelectedPlayer(PlayerData pd) {
        players.add(pd);
    }

    public void removeSelectedPlayer(PlayerData pd) {
        players.remove(players.indexOf(pd));
    }

    public ArrayList<PlayerData> getSelectedPlayers() {
        return players;
    }

    public ArrayList<PlayerData> evaluate(ArrayList<PlayerData> l) {
        if (players.size() == 0)
            return l;
        ArrayList<PlayerData> newList = new ArrayList<PlayerData>();
        player: for (PlayerData pd: l) {
            for (PlayerData pf: players) {
                if (!pd.position.equals(pf)) {
                    newList.add(pd);
                    continue player;
                }
            }
        }

        return newList;
    }
}
