package com.example.demo.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class converter {
    public List<Integer> sequence(int[] seq, int index){
        List<Integer> se = new ArrayList<>();
        Stack<Integer> vStack = new Stack<>();
        int v = seq[index];
        vStack.add(index);
        while (v != -1) {
            vStack.add(v);
            //System.out.println(v);
            v = seq[v];
        }
        while (!vStack.isEmpty()) {
            se.add(vStack.pop());
        }
        return se;
    }

    public static void main(String[] args){
        converter test = new converter();
        int[] a = {-1,1,0,2,3,6,4};
        List<Integer> l = test.sequence(a,5);
        System.out.println(l);
    }
}
