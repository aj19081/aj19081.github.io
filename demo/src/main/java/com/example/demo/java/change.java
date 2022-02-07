package com.example.demo.java;
import java.util.ArrayList;
import java.util.List;

public class change {
    List numberToChar(List list,int floorNumber){
        room2 r = new room2();
        String[] str = r.get(floorNumber);//成功用get调出了room class里面的值
        //
        List result = new ArrayList();
        for (int i=0; i<list.size();i++){
            if (list.get(i).equals(1)){
                result.add(str[1]);
            }else if(list.get(i).equals(2)){
                result.add(str[2]);
            }else if(list.get(i).equals(3)){
                result.add(str[3]);
            }else if(list.get(i).equals(4)){
                result.add(str[4]);
            }else if(list.get(i).equals(5)){
                result.add(str[5]);
            }else if(list.get(i).equals(6)){
                result.add(str[6]);
            }else if(list.get(i).equals(7)){
                result.add(str[7]);
            }else if(list.get(i).equals(8)){
                result.add(str[8]);
            }else if(list.get(i).equals(9)){
                result.add(str[9]);
            }else if(list.get(i).equals(0)){
                result.add(str[0]);
            }
        }
        return result;
    }
}
