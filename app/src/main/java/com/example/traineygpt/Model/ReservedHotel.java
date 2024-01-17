package com.example.traineygpt.Model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;


@Entity(tableName = "ReservedHotels")
public class ReservedHotel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int Hid;

    Date start, End;
    String ClassType , DinnerType , TicketID;
    int AdultNumber , ChildrenNumber;
    public double price;

    @Embedded
    Hotel hotel;

    public ReservedHotel(Date start, Date End, String ClassType,
                         String DinnerType, int AdultNumber, int ChildrenNumber,
                         String TicketID, Hotel hotel) {
        this.start = start;
        this.End = End;
        this.ClassType = ClassType;
        this.DinnerType = DinnerType;
        this.AdultNumber = AdultNumber;
        this.ChildrenNumber = ChildrenNumber;
        this.hotel = hotel;
        this.TicketID = TicketID;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return End;
    }

    public String getClassType() {
        return ClassType;
    }

    public String getDinnerType() {
        return DinnerType;
    }

    public int getAdultNumber() {
        return AdultNumber;
    }

    public int getChildrenNumber() {
        return ChildrenNumber;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public String getTicketID() {
        return TicketID;
    }
}
