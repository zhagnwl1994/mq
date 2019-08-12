package com.Services.Test;

public class psvm {
    public static void main(String[] args) {


        String name = "wodetian";
        Student student = new Student(name) {
            @Override
            public void getName(String name) {
                super.getName(name);
            }
        };
    }
}
