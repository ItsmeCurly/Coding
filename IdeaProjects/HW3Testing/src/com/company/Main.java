package com.company;

public class Main {

    public static void main(String[] args) {\
        hash2("Yea.");
        hash2("slimy");
        hash2("things");
        hash2("did");
        hash2("crawl");
        hash2("with");
        hash2("legs");
        hash2("Upon");
        hash2("the");
        hash2("sea.");




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
        System.out.println(hashVal);
        for(int i = 0; i < key.length(); i++) {

            hashVal = (hashVal * 128 + key.charAt(i)) % 17;
            System.out.println(key.charAt(i)+" : " +hashVal);
        }

        return hashVal;
    }
}
