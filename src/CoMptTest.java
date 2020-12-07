package com.huawei.mutidatasource.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class CoMptTest {
    private List<CoMpt> coMpts;
    @BeforeEach
    public void before() {
//        CoMpt coMpt1 = new CoMpt("1", null, null);
//
//        CoMpt coMpt2 = new CoMpt("2", null, null);
        // esn [[3,4], [5,6], [7], [8]]
        // esnIndex {3:[3,4], 4:[3,4], 5:[5,6], 6:[5,6], 7:[7], 8:[8]}
        // did [[3], [4,5], [6], [7,8]] -> [[4,5], [7,8]]
        // result [[3,4,5,6], [7,8]]
        CoMpt coMpt3 = new CoMpt("3", "esn111", "did444");
        CoMpt coMpt4 = new CoMpt("4", "esn111", "did111");

        CoMpt coMpt5 = new CoMpt("5", "esn222", "did111");
        CoMpt coMpt6 = new CoMpt("6", "esn222", "did333");

        CoMpt coMpt7 = new CoMpt("7", "esn333", "did333");
        CoMpt coMpt8 = new CoMpt("8", "esn444", "did222");

        coMpts = new ArrayList<>();
//        coMpts.add(coMpt1);
//        coMpts.add(coMpt2);
        coMpts.add(coMpt3);
        coMpts.add(coMpt4);
        coMpts.add(coMpt5);
        coMpts.add(coMpt6);
        coMpts.add(coMpt7);
        coMpts.add(coMpt8);
    }

    @Test
    public void sepMpt(){
        List<List<CoMpt>> esnList = new ArrayList<>(coMpts.stream().collect(Collectors.groupingBy(CoMpt::getEsn)).values());
        Map<String, List<CoMpt>> esnIndex = new HashMap<>();
        esnList.forEach(list -> list.forEach(coMpt -> {
            esnIndex.put(coMpt.getNeName(), list);
        }));

        List<List<CoMpt>> didList = coMpts.stream().collect(Collectors.groupingBy(CoMpt::getDid)).values().stream().filter(coMpts1 -> coMpts1.size() > 1).collect(Collectors.toList());
        // merge-remove-add
        for (List<CoMpt> coMpts1 : didList) {
            List<CoMpt> merge = new ArrayList<>();
            for (CoMpt coMpt : coMpts1) {
                List<CoMpt> coMpts = esnIndex.get(coMpt.getNeName());
                merge.addAll(coMpts);
                esnList.remove(coMpts);
            }
            merge = merge.stream().distinct().collect(Collectors.toList());
            esnList.add(merge);
        }
        System.out.println(esnList);
    }

}