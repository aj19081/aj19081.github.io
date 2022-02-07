package com.example.demo.java;

import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
// import java.util.Arrays;
import java.util.List;

@Controller
public class runner {


/*
    public void ajax1(String name , HttpServletResponse response) throws IOException {

        str1 = name;

        if ("".equals(name)){
            response.getWriter().print("false");
        }else{
            response.getWriter().print(str2);
        }
    }

 */
    List result(int destination,int d_floorNumber, int location, int l_floorNumber){
        dPathfinding test = new dPathfinding();
        floor a = new floor();
        //这里的get()里面写的应该是floorNumber
        int[][] matrix = a.get(l_floorNumber);
        int wantedNode = location;
        int[] result2 = test.trace(matrix, wantedNode);
        converter converted_result = new converter();
        List<Integer> l = converted_result.sequence(result2, wantedNode);
        change cha = new change();
        List<Character> l2 = cha.numberToChar(l,l_floorNumber);
        System.out.println("l2 is");
        System.out.println(l2);
        //这里做的是destination的那一段
        int[][] matrix_d = a.get(d_floorNumber);
        int wantedNode_d = destination;
        int[] result2_d = test.trace(matrix_d, wantedNode);
        converter converted_result_d = new converter();
        List<Integer> l_d = converted_result_d.sequence(result2_d, wantedNode_d);
        change cha_d = new change();
        List<Character> l2_d = cha_d.numberToChar(l_d,d_floorNumber);
        System.out.println("l2_d is");
        System.out.println(l2_d);
        //List<Integer> r = test.sequence(result2,6);
        List fin = cha.processToFinal(l2_d,l2);
        System.out.println(fin);
        return fin;
    }

    @RequestMapping("/a1")
    public void main(String[] args,String name , HttpServletResponse response) throws IOException{
        
        int k= Integer.parseInt(name);

        dPathfinding test = new dPathfinding();
        int[][] matrix = {
                {0,5,10,3,-1,-1,-1,-1},
                {5,0,4,-1,-1,-1,-1,-1},
                {10,4,0,2,2,-1,-1,-1},
                {3,-1,2,0,-1,5,-1,-1},
                {-1,-1,2,-1,0,-1,-1,2},
                {-1,-1,-1,5,-1,0,5,-1},
                {-1,-1,-1,-1,-1,5,0,5},
                {-1,-1,-1,-1,2,-1,5,0}};
       // int[] result = test.score(matrix);
        int[] result2 = test.trace(matrix, k);
        /*
        System.out.println("from gate to each room have length：");
        for (int j : result) {
            System.out.print(j + " ");
        }

         */
 //       System.out.println('\n');
//        System.out.println("到node的最短路径是");
//        for (int k : result2){
//            System.out.println(k + " ");
//        }
//        System.out.println("the path to the room " + wantedNode +" is");
        converter result3 = new converter();
        List<Integer> l = result3.sequence(result2, k);
       // System.out.println(l);



        int[] a = new int[l.size()];
        //System.out.println(l.size());
        for (int i=0;i< l.size();i++){
            a[i] =l.get(i);
        }
        StringBuilder str2 = new StringBuilder();

        for (int i : a) {
            str2.append(i);
        }

       // String str2 = Arrays.toString(a);

        if ("".equals(name)){
            response.getWriter().print("false");
        }else{
            response.getWriter().print(str2);
        }


        //List<Integer> r = test.sequence(result2,6);
    }
}
