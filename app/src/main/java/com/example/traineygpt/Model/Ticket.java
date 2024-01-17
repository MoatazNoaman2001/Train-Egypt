package com.example.traineygpt.Model;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;


@Entity(tableName = "Tickets", indices = {@Index(value = {"Train_ID"}, unique = true)})
public class Ticket implements Serializable , Parcelable {
    @NonNull
    @PrimaryKey
    public String Ticket_ID;

    public String Type, TrainTicketDateTime;

    @Embedded
    public Train train;
    public Duration duration;
    public boolean is_expired;

    @SuppressLint("NewApi")
    public Ticket(@NonNull String Ticket_ID, String Type, String TrainTicketDateTime, Train train) {
        this.Ticket_ID = Ticket_ID;
        this.Type = Type;
        this.TrainTicketDateTime = TrainTicketDateTime;
        this.train = train;
        this.duration = Duration.between(train.Start_Time.toInstant(), train.End_Time.toInstant());
    }

    protected Ticket(Parcel in) {
        Ticket_ID = in.readString();
        Type = in.readString();
        TrainTicketDateTime = in.readString();
        is_expired = in.readByte() != 0;
    }


    public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };

    public void setIs_expired(boolean is_expired) {
        this.is_expired = is_expired;
    }

    public boolean isIs_expired() {
        return is_expired;
    }

    public String getType() {
        return Type;
    }

    @NonNull
    public String getTicket_ID() {
        return Ticket_ID;
    }

    public String getTrainTicketDateTime() {
        return TrainTicketDateTime;
    }

    public Train getTrain() {
        return train;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setTicket_ID(@NonNull String ticket_ID) {
        Ticket_ID = ticket_ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(Type, ticket.Type) && Objects.equals(TrainTicketDateTime, ticket.TrainTicketDateTime) && Objects.equals(train, ticket.train);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String toString() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O)
            return "Ticket{" +
                    "Ticket_ID='" + Ticket_ID + '\'' +
                    ", Type='" + Type + '\'' +
                    ", Date='" + TrainTicketDateTime + '\'' +
                    ", train start=" + train.Start_Destination +
                    ", train end= " + train.Termination_Station + "\n" +
                    ", duration" + duration.toMinutes() / 60 + ":" + duration.toMinutes() % 60 +
                    '}';
        else return "Ticket{" +
                "Ticket_ID='" + Ticket_ID + '\'' +
                ", Type='" + Type + '\'' +
                ", Date='" + TrainTicketDateTime + '\'' +
                ", train start=" + train.Start_Destination +
                ", train end= " + train.Termination_Station + "\n" +
                '}';

    }



    @Override
    public int hashCode() {
        return Objects.hash(Type, TrainTicketDateTime, train);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Ticket_ID);
        dest.writeString(Type);
        dest.writeString(TrainTicketDateTime);
        dest.writeByte((byte) (is_expired ? 1 : 0));
    }
}
