package com.Services.Test;

public class Student implements person {

   public Student(String name){

    }

    @Override
    public void getName(String name) {
        System.out.println(name);
    }

    @Override
    public int getAge() {
        return 25;
    }
}
