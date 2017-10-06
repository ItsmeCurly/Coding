package com.company;

public class Main {

    public static void main(String[] args) {
        hash3("burbled");
        hash3("Yea,");
        hash3("slimy");
        hash3("things");
        hash3("did");
        hash3("crawl");
        hash3("with");
        hash3("legs");
        hash3("Upon");
        hash3("the");
        hash3("sea.");
    }

    private static int hash1(String key) {
        int hashVal = 0;
        System.out.println(hashVal);
        for(int i = 0; i < key.length(); i++) {

            hashVal = (hashVal * 128 + key.charAt(i)) % 17;
            System.out.println(key.charAt(i)+" : " +hashVal);
        }

        return hashVal;
    }

    private static int hash2(String key) {
        int hashVal = 0;
        for(int i = 0; i < key.length(); i++) {
            hashVal = hashVal * 37 + key.charAt(i);
        }

        hashVal %= 17;
        if(hashVal < 0)
            hashVal += 17;
        System.out.println(key + ": " + hashVal);
        return hashVal;
    }

    private static int hash3(String key) {
        int hashVal = 0;
        for(int i = 0; i< key.length(); i++) {
            hashVal += key.charAt(i);
        }
        System.out.println(key + ": " + hashVal % 17);
        return (hashVal % 17);
    }
}
