package edu.uwb.css143.service;

import org.springframework.stereotype.Service;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    Ethan Albright
    Additional team member Erica helped and collaborated with me through Discord
 */
@Service
public class IndexerImpl implements Indexer {
    public Map<String, List<List<Integer>>> createIndex(List<String> docs) {
        //[key -> value]

        Map<String, List<List<Integer>>> index = new HashMap<>(); // do not change


        //go through each docs
        for(int i = 0; i < docs.size(); i++) {
            // for each word in each doc, store location

            // doc is list of string
            String doc = docs.get(i).trim().toLowerCase();
            if(doc.isEmpty()) {
                continue;
            }

            //split the doc into words
            String[] words = doc.split("\\s+");

            // put each word into the hash map
            //key is word, value is list of integer lists
            for(int j = 0; j < words.length; j++) {
                String word = words[j];

                //j is location of word in document i
                //create the list of integer list first for the first time
                //when word is seen. Otherwise get the list of integer list from map
                List<List<Integer>> targetList;

                if(!index.containsKey(word)){
                    // create the list of intgeger list
                    targetList = new ArrayList<>();

                    for(int k = 0; k < docs.size(); k++){
                        targetList.add(new ArrayList<>());
                        index.put(word, targetList);
                    }
                }
                else{
                    //add location j into the list i
                    targetList = index.get(word);
                }

                // put the doc id(i) and location id(j) into index entry for word
                targetList.get(i).add(j);
            }
        }
        return index; // do not change. variable "index" is the result that this function should return
    }
}
