package com.pj.conf.utils;

import java.util.LinkedList;
import java.util.Queue;

/***
 * @ClassName: Thread
 * @Description: (这里用一句话描述这个类的作用)
 * @author SenevBoy
 * @date 2017/12/22 16:15   
 **/
public class ThreadEmail  extends  Thread{

    private Queue<Integer> queue =  new LinkedList<Integer>();
    private int maxSize;

    public ThreadEmail(Queue<Integer> queue , String name) {
        super(name);
        this.queue = queue;
        this.maxSize = 1;
    }


    @Override
    public void run() {
        while (true) {
            synchronized (queue) {
                    try {

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }finally {
                        queue=null;
                    }
            }
        }
    }

}
