package com.example.traineygpt.Model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Entity(tableName = "train")
public class Train {
    @NonNull
    @PrimaryKey()
    public String Train_ID;
    public String Train_type, Start_Destination, Termination_Station, Train_number, Train_Supports;
    public String Train_Dis[];
    public Date Start_Time, End_Time;
    public long Distance;

    public Train() {
        Train_ID = UUID.randomUUID().toString();
    }

    public Train(@NonNull String Train_ID, String Train_type,
                 train_distinction Start_Destination,
                 train_distinction Termination_Station,
                 String support, String Train_Number, Date Start_Time, Date End_Time, long Distance) {
        this.Train_ID = Train_ID;
        this.Train_type = Train_type;
        this.Start_Destination = Start_Destination.name();
        this.Termination_Station = Termination_Station.name();
        this.Train_number = Train_Number;
        this.Train_Supports = support;
        this.Start_Time = Start_Time;
        this.End_Time = End_Time;
        this.Distance = Distance;
        int sindx = Arrays.asList(train_distinction.values()).indexOf(Start_Destination),
                tindex = Arrays.asList(train_distinction.values()).indexOf(Termination_Station);
        train_distinction[] distinctions;
        if (sindx > tindex) {
            ArrayList<train_distinction> ddd = new ArrayList<>(Arrays.asList(train_distinction.values()).subList(tindex, sindx));
            Collections.sort(ddd, Collections.reverseOrder());
            distinctions = ddd.toArray(new train_distinction[0]);
        } else
            distinctions = Arrays.asList(train_distinction.values()).subList(sindx, tindex).toArray(new train_distinction[0]);
        Train_Dis = new String[distinctions.length];
        for (train_distinction d : distinctions) {
            Train_Dis[Arrays.asList(distinctions).indexOf(d)] = d.name();
        }
    }

    @NonNull
    public String getTrain_ID() {
        return Train_ID;
    }

    public String getTrain_type() {
        return Train_type;
    }

    public String getStart_Destination() {
        return Start_Destination;
    }

    public String getTermination_Station() {
        return Termination_Station;
    }

    public Date getStart_Time() {
        return Start_Time;
    }

    public Date getEnd_Time() {
        return End_Time;
    }

    public long getDistance() {
        return Distance;
    }


    public String getTrain_number() {
        return Train_number;
    }

    public String getTrain_Supports() {
        return Train_Supports;
    }

    public String[] getTrain_Dis() {
        return Train_Dis;
    }
}
