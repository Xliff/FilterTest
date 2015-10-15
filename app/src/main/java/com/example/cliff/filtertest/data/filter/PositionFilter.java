package com.example.cliff.filtertest.data.filter;

import com.example.cliff.filtertest.data.PlayerData;

import java.util.ArrayList;

/**
 * Created by Cliff on 10/4/2015.
 *
 * If position is listed in [positions] then include them.
 */
public class PositionFilter extends Filter<PlayerData> {
    ArrayList<String> positions = new ArrayList<String>();

    public void addPosition(String pos) {
        positions.add(pos);
    }

    public void removePosition(String pos) {
        positions.remove(positions.indexOf(pos));
    }

    public void removePosition(int pos) {
        positions.remove(pos);
    }

    public int findPosition(String pos) {
        return positions.indexOf(pos);
    }

    public ArrayList<String> getPositions() {
        return positions;
    }

    public ArrayList<PlayerData> evaluate(ArrayList<PlayerData> l) {
        if (positions.size() == 0)
            return l;
        ArrayList<PlayerData> newList = new ArrayList<PlayerData>();
        player: for (PlayerData pd: l) {
            for (String p: positions) {
                if (pd.position.equals(p)) {
                    newList.add(pd);
                    continue player;
                }
            }
        }

        return newList;
    }
}
