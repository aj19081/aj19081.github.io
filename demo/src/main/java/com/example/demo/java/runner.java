package com.example.demo.java;

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
