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
            "212","222"
    };
    String[] get(int floorNumber){
        if(floorNumber==2) {
            return room_second;
        }
        return room_third;
    }
}
