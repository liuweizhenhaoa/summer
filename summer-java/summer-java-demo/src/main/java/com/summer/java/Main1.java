//package com.summer.java;
//
//import com.sun.tools.internal.ws.processor.model.Model;
//
//import java.lang.reflect.Array;
//import java.util.Scanner;
//
//public class Main1 {
//
//
////    private BoxTemplate boxTemplate;
//    /**请完成下面这个process函数，实现题目要求的功能**/
//    /**
//     * 当然，你也可以不按照这个模板来作答，完全按照自己的想法来^-^
//     **/
//    private static int process() {
//    }
//
//    public static void main(String args[]) {
//
//        BoxTemplate boxTemplate = new BoxTemplate();
//        Scanner scanner = new Scanner(System.in);
//        boxTemplate.price = 2000;
//        while (scanner.hasNext()) {
//            boxTemplate.length = scanner.nextInt();
//            boxTemplate.width = scanner.nextInt();
//            boxTemplate.height = scanner.nextInt();
//            int itemNum = scanner.nextInt();
//            Model[] items = new Model[itemNum];
//            for (int i = 0; i < itemNum; i++) {
//                Model item = new Model();
//                item.price = scanner.nextInt();
//                item.length = scanner.nextInt();
//                item.width = scanner.nextInt();
//                item.height = scanner.nextInt();
//                items[i] = item;
//            }
//            long startTime = System.currentTimeMillis();
//            boxMinNum = Integer.MAX_VALUE;
//            System.out.println(process());
//        }
//    }
//
//    static class BoxTemplate{
//        public int length;
//        public int width;
//        public int height;
//        public int price;
//    }
//
//    static class Model{
//        public int length;
//        public int width;
//        public int height;
//        public int price;
//    }
//}