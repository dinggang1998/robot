package com.learn;

public class test2 {


    static class Fruit{

        public void Fruit(){
            System.out.println("eat1");

        }

        public void eat(){
            System.out.println("eat1");
        }
    }

    static class Apple extends Fruit{


        public void Apple(){
            System.out.println("eat2");

        }
        @Override
        public void eat(){
            System.out.println("eat2");
        }
    }
}
