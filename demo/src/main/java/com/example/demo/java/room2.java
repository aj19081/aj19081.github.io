package com.example.demo.java;

public class room2 {
    String[] room_second ={
            "2.18",
            "2.19",
            "2.25",
            "2c02",
            "2c08",
            "2.16",
            "2.17",
            "2.11",
            "2.08",
            "2c14"
    };
    String[] room_third ={
            "3.04",
            "3.01",
            "3.33",
            "3.30",
            "3.39",
            "3.42",
            "3.40",
            "3c01",
            "3c02",
            "3c07"
    };
    String[] get(int floorNumber){
        if(floorNumber==2) {
            return room_second;
        }else {
            return room_third;
        }
    }
}
