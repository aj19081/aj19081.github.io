package com.example.demo.java;

public class floor{
    //这是二楼的房间数据
    int[][] matrix_second = {
            {0,1,-1,-1,-1,-1,1,-1,-1,-1},//2.18  0
            {1,0,1,-1,-1,-1,-1,-1,-1,-1},//2.19  1
            {-1,1,0,1,-1,-1,-1,-1,-1,-1},//2.25  2
            {-1,-1,1,0,1,-1,-1,-1,-1,1}, //2c02  3
            {-1,-1,-1,1,0,1,-1,-1,-1,-1}, //2c08  4
            {-1,-1,-1,-1,1,0,1,-1,-1,-1},//2.16  5
            {-1,-1,-1,-1,-1,1,0,1,-1,-1},//2.17  6
            {-1,-1,-1,-1,-1,-1,1,0,1,-1},//2.11  7
            {-1,-1,-1,-1,-1,-1,-1,1,0,-1},//2.08 8
            {-1,-1,-1,1,-1,-1,-1,-1,-1,0}//2c14  9
    };
    int[][] matrix_third = {
            {1},{2},{3},{4}
    };

    public int[][] get(int floorNumber){
        if(floorNumber==2) {
            return matrix_second;
        }//这里以后改成一大堆if else，用来选择楼层
        else{
            return matrix_third;
        }
    }
    public static void main(String[] args){
        floor a = new floor();
        a.get(1);//这里的三目前没意义
        System.out.println(a.get(1)[5][5]);
    }
}