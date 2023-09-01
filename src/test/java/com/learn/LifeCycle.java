package com.learn;

public class LifeCycle {

    private  static String staticField=getStaticField();

    static {
        System.out.println(staticField);
        System.out.println("静态代码块初始化");
    }

    private String filed=getField();

    {
        System.out.println(filed);
    }

    public LifeCycle(){
        System.out.println("构造函数初始化");
    }

    public static  String getStaticField(){
        return "Static Field Initial";
    }

    public static String getField(){


        return "Field Initial";
    }

    public static void main(String[] args){
        new LifeCycle();
    }
    private static int[] a=new int[]{1,2,2,2,2,2,2};

    private static int a1[]= new int[]{1,2,2,2,2,2,2};
    private static int a2[] = {1,2,3,4};


    {
        for (int aa : a1) {
            System.out.println(aa);
        }

        for (int i = 0; i < a1.length;i++) {
            System.out.println(a[i]);
        }
    }

}
