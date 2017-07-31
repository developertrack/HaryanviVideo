package com.publiseyez.piyushpratap2009.haryanvivideo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> male_singer = new ArrayList<String>();
        male_singer.add("Mannu diwana Videos");
        male_singer.add("Kulbir danoda Videos");
        male_singer.add("Surender Romio Videos");
        male_singer.add("Vijay verma Videos");
        male_singer.add("Janu rakhi Videos ");
        male_singer.add("Raju punjabi Videos");

          /* Sapna Chaudhary
        Sushila haryanvi
        Anjali raghav*/

//        kalua

        List<String> female_singer = new ArrayList<String>();
        female_singer.add("Sapna chaudhary Videos");
        female_singer.add("Sushila haryanvi Videos");
        female_singer.add("Anjali raghav Videos");

        List<String> sapna_singer = new ArrayList<String>();
        sapna_singer.add("Sapna chaudhary Latest Videos");
        sapna_singer.add("Sapna chaudhary Hits Videos");
        sapna_singer.add("Sapna chaudhary Top 10 Videos");

        List<String> latest = new ArrayList<String>();
        latest.add("Latest Haryanvi Hit Videos");
        latest.add("Latest Haryanvi Videos Collection");

        List<String> hit = new ArrayList<String>();
        hit.add("Haryanvi Superhit Videos");
        hit.add("Top 10 Haryanvi Videos");

        expandableListDetail.put("Latest Haryanvi Videos", latest);
        expandableListDetail.put("Haryanvi Hit Videos ", hit);
        expandableListDetail.put("Haryanvi Male Singer", male_singer);
        expandableListDetail.put("Haryanvi Female Singer", female_singer);
        expandableListDetail.put("Sapna Chaudhary Collection", sapna_singer);


        return expandableListDetail;
    }
}
