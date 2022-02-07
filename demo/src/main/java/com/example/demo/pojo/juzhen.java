package com.example.demo.pojo;
// import java.util.Scanner;
public class juzhen {
    private int row;
    private int col;
    int data[][];
//  三个属性    其中data用于存储二维数组

    //三个构造函数
    public juzhen() {		//无参构造函数  空的以便以后使用
    }
    public juzhen(int row, int col) {
        // 根据用户输入构造矩阵 即构造行列的
    }
    public juzhen(int data[][]) {// 根据传入的二维数组构建矩阵
        this.row = data.length;
        //row的值为传过来矩阵的行数
        this.col = data[0].length;
        this.data = new int[row][col];
        for (int i = 0; i < this.row; i++)//for循环赋值给date
            for (int j = 0; j < this.col; j++) {
                this.data[i][j] = data[i][j];
            }
    }

    //打印数据   输出函数
    public void print() {//输出传过来的矩阵
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                System.out.print(" " + this.data[i][j]);
            }
            System.out.println();//一行之后回车
        }
    }

    //get set 方法  获取矩阵属性行数列数
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] data) {
        this.data = data;
    }

    //利用函数  获取第i行j列的元素
    public int get(int i, int j) {
        return this.data[i][j];//仍用this获取这个矩阵的数据
    }

    //矩阵加法  c[i][j]= a[i][j]+b[i][j]
    public static juzhen add(juzhen a, juzhen b) {
// 矩阵加法    结果返回给矩阵c   传过来对象的加法
        juzhen c = null;//先给矩阵c设为空
        if (a.getRow() != b.getRow() || a.getCol() != b.getCol()) {
//矩阵a b不同行或不同列   且利用get set 函数
            System.out.println("不是同型矩阵，不能相加");				return c;
        }
        int[][] data = new int[a.getRow()][a.getCol()];
//仍用原来的构造函数用data  并给它分配大小
        for (int i = 0; i < a.getRow(); i++)//做循环 进行相加
            for (int j = 0; j < a.getCol(); j++) {
                data[i][j] = a.get(i, j) + b.get(i, j);
                /*不用data[i][j] = a.data[i][j] + b.data[i][j] 					因为为私有  要获取i行j列的元素的数据  创建函数get(int i,int j)*/
            }
        c = new juzhen(data);//将data给矩阵c
        return c;
    }

    //矩阵减法 c[i][j]= a[i][j]-b[i][j]
    public static juzhen subtract(juzhen a, juzhen b) {// 矩阵加法    结果返回给矩阵e   传过来对象的减法
        juzhen e = null;//先给矩阵e设为空
        if (a.getRow() != b.getRow() || a.getCol() != b.getCol()) {//矩阵a b不同行或不同列   且利用get set 函数
            System.out.println("不是同型矩阵,不能相加");
            return e;
        }
        int[][] data = new int[a.getRow()][a.getCol()];//仍用原来的构造函数 用data  并给它分配大小
        for (int i = 0; i < a.getRow(); i++)//做循环 进行相加
            for (int j = 0; j < a.getCol(); j++) {
                data[i][j] = a.get(i, j) - b.get(i, j);
            }
        e = new juzhen(data);//将data给矩阵e
        return e;
    }


    //矩阵的数乘   c[i][j]= a[i][j]*h
    public static juzhen shucheng(juzhen a, int h) {
        juzhen f = null;//先给矩阵f设为空
        int[][] data = new int[a.getRow()][a.getCol()];//仍用原来的构造函数 用data  并给它分配大小
        for (int i = 0; i < a.getRow(); i++)//做循环 进行相加
            for (int j = 0; j < a.getCol(); j++) {
                data[i][j] = a.get(i, j) *h;
            }
        f = new juzhen(data);//将data给矩阵f
        return f;
    }

    //矩阵乘法
    public static juzhen multiply(juzhen a, juzhen b) {//传过来对象
        juzhen d=null;//先设矩阵d为空
        if(a.getCol() !=b.getRow())//判是否可以相乘
        {
            System.out.println("矩阵不能相乘");
            return d;
        }
        int [][]data = new int [a.getRow()][b.getCol()];//给data分配空间  a的行数 b的列数  data暂存数据
        for (int i =0 ;i< a.getRow();i++)
            for(int j = 0; j< b.getCol();j++)
                for(int k =0; k < a.getCol();k++)
                {
                    data[i][j]=data[i][j]+a.get(i, k)*b.get(k, j);//矩阵乘法运算
                }

        d= new juzhen(data);//将矩阵给d
        return d;//返回矩阵d
    }
}

class test {
    public static void main(String[] args) {

        // int a[][] = { { 1, 2, 3 }, { 2, 3, 4 }, { 2, 1, 3 } };
        // juzhen ma = new juzhen(a);//将矩阵a传过去
        // System.out.println("矩阵a为：");
        // ma.print();//输出矩阵a
        // System.out.println("\n");//回车进行隔开
        // int b[][]=  { { 1, 2, 3 }, { 2, 3, 4 }, { 2, 1, 3 } };
        // juzhen mb = new juzhen(b);
        // System.out.println("矩阵b为：");
        // mb.print();
        // System.out.println("\n");

//输入h的值  进行数乘运算
        // Scanner input = new Scanner(System.in);
        // System.out.println("请输入h的值：");
        // int h = input.nextInt();
        // System.out.println("\n");

        // System.out.println("矩阵a与数h相乘结果为：");
        // juzhen mf = juzhen.shucheng(ma, h);
        // mf.print();
        // System.out.println("\n");

        // System.out.println("矩阵b与数h相乘结果为：");
        // juzhen mg = juzhen.shucheng(mb, h);
        // mf.print();
        // System.out.println("\n");

        // juzhen mc =juzhen.add(ma, mb);
        // System.out.println("矩阵相加结果为：");
        // mc.print();//输出mc
        // System.out.println("\n");

        // System.out.println("矩阵相减结果为：");
        // juzhen me = juzhen.subtract(ma, mb);
        // me.print();
        // System.out.println("\n");

        // System.out.println("矩阵相乘结果为：");
        // juzhen md = juzhen.multiply(ma, mb);
        // md.print();
    }
}
