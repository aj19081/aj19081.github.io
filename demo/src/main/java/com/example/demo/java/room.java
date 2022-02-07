package com.example.demo.java;

public class room {
//    String[] room_first ={
//            {"2.18"}, {"2.19"}, {"2.25"}, {"2c02"}, {"2c08"}, {"2.16"}, {"2.17"}, {"2.11"}, {"2.08"}, {"2c14"}
//    };
//    String[] room_first ={
//            "2.18", "2.19", "2.25", "2c02", "2c08", "2.16", "2.17", "2.11", "2.08", "2c14"
//    };
    char[][] room_second = {
            {'2','.','1','8'},
            {'2','.','1','9'},
            {'2','.','2','5'},
            {'2','c','0','2'},
            {'2','c','0','8'},
            {'2','.','1','6'},
            {'2','.','1','7'},
            {'2','.','1','1'},
            {'2','.','0','8'},
            {'2','c','1','4'}
    };
    char[][] room_third ={
            {'3','.','0','4'},
            {'3','.','0','1'},
            {'3','.','3','3'},
            {'3','.','3','0'},
            {'3','.','3','9'},
            {'3','.','4','2'},
            {'3','.','4','0'},
            {'3','c','0','1'},
            {'3','c','0','2'},
            {'3','c','0','7'}
    };
    char[][] get(int floorNumber){
        if(floorNumber==2) {
            return room_second;
        }else{
            return room_third;
        }
    }



    int find(int floorNumber,String roomNumber){
        int remember=0;//0就当是默认的房间号好了
        int helper = 0;
        int helper_2= 0;
        boolean check = false;
        room r2 = new room();
        char[][] str = r2.get(floorNumber);//22222222暂时如此 必须要改，等加了楼层就改
        for(int i=0;i<str.length;i++)
        {
            for(int j=0;j<4;j++)
            {
                if(roomNumber.charAt(helper_2)==str[i][j]){
                    helper++;
                    helper_2++;
                }
                if (helper == 4){
                    check = true;
                    remember = i;
                    break;
                }
            }
            helper=0;
            helper_2=0;
        }
        return remember;
    }



    boolean compare(int floorNumber,String roomNumber){
        int helper = 0;
        int helper_2= 0;
        boolean check = false;
        room r2 = new room();
        char[][] str = r2.get(floorNumber);////////////////////暂时如此
        for(int i=0;i<str.length;i++)
        {
            for(int j=0;j<4;j++)
            {
                if(roomNumber.charAt(helper_2)==str[i][j]){
                    helper++;
                    helper_2++;
                }
                if (helper == 4){
                    check = true;
                    break;
                }
            }
            helper=0;
            helper_2=0;
        }
        return check;
    }
    //根据测试，发现以下几个问题
    //1.在class里面main外面定义的二维数组，如果不通过get方法获取的话无法在main里面使用
    //2.string的二维数组没办法直接print，也没办法用room_first[a][b]来访问他的每一个值
    public static void main(String[] args) {
        room r = new room();
        char[][] str = r.get(2);//z这里的2暂时指floorNumber
        //输出java数组
        for(int i=0;i<str.length;i++)
        {
            System.out.println(str[i]);
            for(int j=0;j<4;j++)
            {
                System.out.println(str[i][j]);
            }
        }
    }

}
