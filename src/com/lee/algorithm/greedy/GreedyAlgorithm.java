package com.lee.algorithm.greedy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author LiJing
 * @version 1.0
 * 贪心算法
 */
public class GreedyAlgorithm {
    public static void main(String[] args) {
        //broadcasts K:电台 v:电台覆盖的地区
        HashMap<String, HashSet<String>> broadcasts = new HashMap<>();
        HashSet<String> hashSet1 = new HashSet<>();
        hashSet1.add("北京");
        hashSet1.add("上海");
        hashSet1.add("天津");
        HashSet<String> hashSet2 = new HashSet<>();
        hashSet2.add("广州");
        hashSet2.add("北京");
        hashSet2.add("深圳");
        HashSet<String> hashSet3 = new HashSet<>();
        hashSet3.add("成都");
        hashSet3.add("上海");
        hashSet3.add("杭州");
        HashSet<String> hashSet4 = new HashSet<>();
        hashSet4.add("上海");
        hashSet4.add("天津");
        HashSet<String> hashSet5 = new HashSet<>();
        hashSet5.add("杭州");
        hashSet5.add("大连");

        broadcasts.put("K1", hashSet1);
        broadcasts.put("K2", hashSet2);
        broadcasts.put("K3", hashSet3);
        broadcasts.put("K4", hashSet4);
        broadcasts.put("K5", hashSet5);

        //allAreas 存放需要覆盖的所有地区
        HashSet<String> allAreas = new HashSet<>();
        allAreas.add("北京");
        allAreas.add("上海");
        allAreas.add("天津");
        allAreas.add("广州");
        allAreas.add("深圳");
        allAreas.add("成都");
        allAreas.add("杭州");
        allAreas.add("大连");
        //selects 存放选择的电台
        ArrayList<String> selects = new ArrayList<>();

        //定义一个临时的集合，存放遍历过程中的电台覆盖的地区和当前还没有覆盖的地区的交集
        HashSet<String> tempSet = new HashSet<>();
        //maxKey 保存在一次遍历过程中，能够覆盖最多未覆盖地区的电台
        String maxKey;
        //max 保存能够覆盖最多未覆盖地区的地区数量
        int max;

        while (allAreas.size() != 0) {
            maxKey = null;
            max = 0;
            //遍历broadcasts
            for (String key : broadcasts.keySet()) {
                tempSet.clear();
                HashSet<String> areas = broadcasts.get(key);
                tempSet.addAll(areas);
                //求出tempSet和allAreas集合的交集，交集会赋给 tempSet
                tempSet.retainAll(allAreas);
                if (tempSet.size() > max) {
                    maxKey = key;
                    max = tempSet.size();
                }
            }
            //如果maxKey不为null，则加入到selects
            if (maxKey != null) {
                selects.add(maxKey);
                allAreas.removeAll(broadcasts.get(maxKey));
            }
        }

        System.out.println("得到的选择结果是：" + selects);
    }
}
