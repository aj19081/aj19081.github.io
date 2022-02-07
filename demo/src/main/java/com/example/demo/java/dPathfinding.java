package com.example.demo.java;

// import java.util.ArrayList;
// import java.util.*;
import java.util.List;

public class dPathfinding {
    public int[] score(int[][] matrix){
        int[] scores = new int[matrix.length];
        //int[] nodeTrace = new int[node];
        boolean[] check = new boolean[matrix.length];
        check[0]=true;
        //initialize the distance between vertex 0 and other vertex;
        for(int i=1 ; i < matrix.length; i++ ){
            scores[i] = matrix[0][i];
            check[0] = false;
        }
        //这里是操作逐点找最小距离的
        for(int i = 0; i< matrix.length ; i++){
            int currentNode = 0;
            int distance = 1000;
            for(int j=0; j< matrix.length; j++){
                //没check过的话咱们就开始挑选   找其中最小的原点到他的path
                if(!check[j]){
                    //不是无限距离 小于现在的minimum distance
                    if(scores[j]<distance && scores[j]!= -1){
                        currentNode = j;
                        distance = scores[j];
                    }
                }
            }
            //把新找出来的最近的点给标记出来 check
            check[currentNode]= true;
            for(int j=0; j< matrix.length; j++){
                if (!check[j]){
                    if (matrix[currentNode][j] != -1 && (scores[j] > distance + matrix[currentNode][j] || scores[j] == -1)){
                        scores[j] = distance + matrix[currentNode][j];
                    }
                }
            }

        }
        return scores;
    }
    public int[] trace(int[][] matrix, int node){
        int[] nodeTrace = new int[matrix.length]; // 存上一个连接的点
        // initialize the trace by putting 0 for every node
        for (int i = 1; i< matrix.length; i++){
                nodeTrace[i] = 0;
        }
        nodeTrace[0]=-1;
        //重新写一遍 score
        int[] scores = new int[matrix.length];
        boolean[] check = new boolean[matrix.length];
        check[0]=true;
        //initialize the distance between vertex 0 and other vertex;
        for(int i=1 ; i < matrix.length; i++ ){
            scores[i] = matrix[0][i];
            check[0] = false;
        }
        //这里是操作逐点找最小距离的
        for(int i = 0; i< matrix.length ; i++){
            int currentNode = 0;
            int distance = 1000;
            for(int j=0; j< matrix.length; j++){
                //没check过的话咱们就开始挑选   找其中最小的原点到他的path
                if(!check[j]){
                    //不是无限距离 小于现在的minimum distance
                    if(scores[j]<distance && scores[j]!= -1){
                        currentNode = j;
                        distance = scores[j];
                    }
                }
            }
            //把新找出来的最近的点给标记出来 check
            check[currentNode]= true;

            //nodeTrace[currentNode]= 0; //初始点 后面加的东西；这句可以不写
            for(int j=0; j< matrix.length; j++){
                if (!check[j]){
                    if (matrix[currentNode][j] != -1 && (scores[j] > distance + matrix[currentNode][j] || scores[j] == -1)){
                        scores[j] = distance + matrix[currentNode][j];
                        nodeTrace[j]=currentNode;
                    }
                }
            }
        }
        return nodeTrace;
    }
    public static void main(String[] args) {
        dPathfinding test = new dPathfinding();
//        int[][] matrix = {{0,5,10,3,-1,-1,-1,-1},
//                {5,0,4,-1,-1,-1,-1,-1},
//                {10,4,0,2,2,-1,-1,-1},
//                {3,-1,2,0,-1,5,-1,-1},
//                {-1,-1,2,-1,0,-1,-1,2},
//                {-1,-1,-1,5,-1,0,5,-1},
//                {-1,-1,-1,-1,-1,5,0,5},
//                {-1,-1,-1,-1,2,-1,5,0}};
        //这里a是用来存每一层的room的数据的，在这里可以直接用a.get调用不同层的数据
        //之后再把a.get赋值给matrix
        floor a = new floor();
        int[][] matrix = a.get(1);

        int wantedNode = 7;
        int[] result = test.score(matrix);
        int[] result2 = test.trace(matrix,wantedNode);
        System.out.println("顶点0到图中所有顶点之间的最短距离为：");
        for (int j : result) {
            System.out.print(j + " ");
        }
        System.out.println('\n');
//        System.out.println("到node的最短路径是");
//        for (int k : result2){
//            System.out.println(k + " ");
//        }
        converter result3 = new converter();
        List<Integer> l = result3.sequence(result2,wantedNode);
        System.out.println(l);
        //List<Integer> r = test.sequence(result2,6);
    }
}
