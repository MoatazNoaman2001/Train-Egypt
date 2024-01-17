package com.example.traineygpt.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity(tableName = "hotal")
public class Hotel implements Serializable {

    @NonNull
    @PrimaryKey()
    String ID;
    String name, address, discreption, City ;
    String [] HotelImage;
    String PhoneNumber;
    String[] supplies;
    double rate;

    public Hotel(@NonNull String ID, String name, String address, String City
                  , String PhoneNumber , String discreption, double rate) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.discreption = discreption;
        this.rate = rate;
        this.PhoneNumber = PhoneNumber;
        this.City = City;
    }

    public String[] getHotelImage() {
        return HotelImage;
    }

    public void setHotelImage(String[] hotelImage) {
        HotelImage = hotelImage;
    }

    public void setSupplies(String[] supplies) {
        this.supplies = supplies;
    }

    @NonNull
    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDiscreption() {
        return discreption;
    }

    public double getRate() {
        return rate;
    }

    public String getCity() {
        return City;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String[] getSupplies() {
        return supplies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return ID.equals(hotel.ID) && Objects.equals(name, hotel.name) && Objects.equals(address, hotel.address) && Objects.equals(discreption, hotel.discreption) && Objects.equals(City, hotel.City) && Arrays.equals(HotelImage, hotel.HotelImage) && Objects.equals(PhoneNumber, hotel.PhoneNumber);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(ID, name, address, discreption, City, PhoneNumber);
        result = 31 * result + Arrays.hashCode(HotelImage);
        return result;
    }
}
