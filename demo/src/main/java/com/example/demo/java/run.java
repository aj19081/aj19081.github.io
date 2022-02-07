package com.example.demo.java;

// import java.nio.charset.StandardCharsets;
import java.sql.ClientInfoStatus;
import java.util.List;

public class run {
    public static void main(String[] args){
        //现在假设我传进来了2c08,
        //等把前端连接进来的时候传的就是"2.11"这类的房间值！
        String str = "2c08";
        //System.out.println(str);
        //这里的floorNumber是默认的初始值，我们根据输入的楼层来改它！
        int floorNumber;

        //这里检查传输进来的值是不是合理的房间号
        char ch = str.charAt(0);
        floorNumber =(ch-'0');//这里很巧妙，直接两个char相减得到需要的int
        System.out.println(floorNumber);
        //这里把房间号的第一位搞出来了
        //然后我们把整一层房间的信息都搞出来然后对比一下
        room rf = new room();
        boolean check = rf.compare(floorNumber,str);
        System.out.println(check);
        //check 是true的话就接着算
        //这里要写一下是false时要输出出去的代码,写在else里
        if(check){
            System.out.println("hello world");
        }else{
            System.out.println();
            //这里写要在前端输出的东西
            //然后加点命令给他发到前端去
        }
        //这里写处理floorNumber的代码
        //----------------------
//        floorNumber我直接在上面处理了，
//        所以这里现在不写东西，
//        但是等要写任意房间走到任意房间的代码时，
//        就要在这里操作！
        //---------------------
        //这里把房间号换成0-9的数字
        int roomNumber_0_9 =0;
        if(check){
            roomNumber_0_9 = rf.find(floorNumber,str);
            System.out.println(roomNumber_0_9);
            //注意，因为array总是从0开始记数，故实际上这个房间是第i+1个房间
            //但由于我们代码中的matrix也是从0开始，我们使用这个特性，只要得到i就好
        }
        //-------------------------------------------------------------------------
        //至此！我们得到了想要去的楼层的数量被改造过符合条件的0-9的房间号
        //以及被改造过符合条件的0-9的房间号
        //可以开始pathfinding操作，需注意：
        //目前我们只要同一层操作就可以了，有了多层以后要添加上楼的操作！！！！！
        //-------------------------------------------------------------------
        //这里开始操作pathfinding

        dPathfinding test = new dPathfinding();
        floor a = new floor();
        //这里的get()里面写的应该是floorNumber
        int[][] matrix = a.get(floorNumber);

        //
//        int[][] matrix ={
//                {0,1,-1,-1,-1,-1,1,-1,-1,-1},//2.18  0
//                {1,0,1,-1,-1,-1,-1,-1,-1,-1},//2.19  1
//                {-1,1,0,1,-1,-1,-1,-1,-1,-1},//2.25  2
//                {-1,-1,5,0,5,-1,-1,-1,-1,1}, //2c02  3
//                {-1,-1,-1,5,0,5,-1,-1,-1,-1}, //2c08  4
//                {-1,-1,-1,-1,1,0,1,-1,-1,-1},//2.16  5
//                {-1,-1,-1,-1,-1,1,0,1,-1,-1},//2.17  6
//                {-1,-1,-1,-1,-1,-1,1,0,1,-1},//2.11  7
//                {-1,-1,-1,-1,-1,-1,-1,1,0,-1},//2.08 8
//                {-1,-1,-1,1,-1,-1,-1,-1,-1,0}//2c14  9
//        };

        int wantedNode = roomNumber_0_9;
        int[] result = test.score(matrix);
        int[] result2 = test.trace(matrix,wantedNode);

//        System.out.println("顶点0到图中所有顶点之间的最短距离为：");
//        for (int j : result) {
//            System.out.print(j + " ");
//        }
//        System.out.println('\n');

        converter converted_result = new converter();
        List<Integer> l = converted_result.sequence(result2,wantedNode);
        System.out.println("hello");
        System.out.println(l);
        change cha = new change();
        List<Character> l2 =cha.numberToChar(l,floorNumber);
        System.out.println(l2);
        //List<Integer> r = test.sequence(result2,6);
    }
}
