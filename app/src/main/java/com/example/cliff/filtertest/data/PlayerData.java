package com.example.cliff.filtertest.data;

import android.graphics.Bitmap;

/**
 * Created by Cliff on 8/9/2015.
 */
public class PlayerData {
    public int playerID;
    public String position;
    public String displayName;
    public String fname;
    public String lname;
    public String team;
    public int byeWeek;
    public double standDev;
    public double nerdRank;
    public int positionRank;
    public int overallRank;

    public Bitmap b;

    //region CONSTRUCTOR
    public PlayerData(
        int playerID,
        String position,
        String displayName,
        String fname,
        String lname,
        String team,
        int byeWeek,
        double standDev,
        double nerdRank,
        int positionRank,
        int overallRank)
    {
        this.playerID = playerID;
        this.position = position;
        this.displayName = displayName;
        this.fname = fname;
        this.lname = lname;
        this.team = team;
        this.byeWeek = byeWeek;
        this.standDev = standDev;
        this.nerdRank = nerdRank;
        this.positionRank = positionRank;
        this.overallRank = overallRank;
    }
    //endregion

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerData)) return false;

        PlayerData that = (PlayerData) o;

        if (byeWeek != that.byeWeek) return false;
        if (Double.compare(that.standDev, standDev) != 0) return false;
        if (Double.compare(that.nerdRank, nerdRank) != 0) return false;
        if (positionRank != that.positionRank) return false;
        if (overallRank != that.overallRank) return false;
        if (!position.equals(that.position)) return false;
        if (!displayName.equals(that.displayName)) return false;
        if (!fname.equals(that.fname)) return false;
        if (!lname.equals(that.lname)) return false;
        if (!team.equals(that.team)) return false;
        return !(b != null ? !b.equals(that.b) : that.b != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = position.hashCode();
        result = 31 * result + displayName.hashCode();
        result = 31 * result + fname.hashCode();
        result = 31 * result + lname.hashCode();
        result = 31 * result + team.hashCode();
        result = 31 * result + byeWeek;
        temp = Double.doubleToLongBits(standDev);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(nerdRank);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + positionRank;
        result = 31 * result + overallRank;
        result = 31 * result + (b != null ? b.hashCode() : 0);

        return result;
    }


    @Override
    public String toString() {
        return "PlayerData{" +
            "position='" + position + '\'' +
            ", displayName='" + displayName + '\'' +
            ", fname='" + fname + '\'' +
            ", lname='" + lname + '\'' +
            ", team='" + team + '\'' +
            ", byeWeek=" + byeWeek +
            ", standDev=" + standDev +
            ", nerdRank=" + nerdRank +
            ", positionRank=" + positionRank +
            ", overallRank=" + overallRank +
            ", b=" + ((b == null) ? "null" : b.toString()) +
            '}';
    }
}
