package com.summer.java.ali;

public class Test2 {
    //实现函数,给定一个字符串数组,求该数组的连续非空子集，分別打印出来各子集 ，
    //举例数组为[abc]，输出[a],[b],[c],[ab],[bc],[abc]

    public void printSub(String[] arr){

        for(int i=0; i <arr.length;i++){

            String str = arr[i];
            //每个元素都是一个子集
            System.out.println("["+arr[i]+"]");
            //如果是最后一个元素则只打印当前元素
            if(i+1 == arr.length){
                break;
            }
            //
            for(int j=i+1; j <arr.length;j++){
                str += arr[j];
                System.out.println("["+ str +"]");
            }


        }

    }


    public static void main(String[] args){
        Test2 t2 = new Test2();
        String[] arr = {"a","b","c"};
        t2.printSub(arr);

    }
}
