package com.example.mertdesktop.schoolproject;


class ExtendableClass {

    private String[] arr;

    public ExtendableClass(int capacity) {
        arr = new String [capacity];
    }

    public void add(String item) {
        if (getLastIndex() == arr.length) {
            generateBiggerArray();
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                arr[i] = item;
                break;
            }
        }
    }


    private void generateBiggerArray() {
        int currentCapacity = arr.length;
        String[] tempArr = new String[currentCapacity + 4];
        for(int i = 0; i < arr.length; i++) {
            tempArr[i] = arr[i];
        }
        this.arr = tempArr;
    }

    private int getLastIndex() {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null)
                return i;
        }
        return arr.length;
    }
}
