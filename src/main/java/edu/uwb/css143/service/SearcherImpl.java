package edu.uwb.css143.service;

import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

@Service
public class SearcherImpl implements Searcher {
    /*
    Extra credit
    this search won't work. why?

    Because keyPhrase searches word by word, but by using contains it searches by substring


     */
    // public List<Integer> search(String keyPhrase, List<String> docs) {
    //
    //     List<Integer> result = new ArrayList<>();
    //
    //     for (int i = 0; i < docs.size(); i++) {
    //         if (docs.get(i).contains(keyPhrase)) {
    //             result.add(i);
    //         }
    //     }
    //     return result;
    // }

    // a naive search
    // DO NOT CHANGE
    public List<Integer> search(String searchPhrase, List<String> docs) {

        List<Integer> result = new ArrayList<>();

        String[] searchWords = searchPhrase.split("\\s+");

        // search in each doc for consecutive matches of each word in the search phrase
        for (int i = 0; i < docs.size(); i++) {
            String doc = docs.get(i).trim();
            if (doc.isEmpty()) {
                continue;
            }
            String[] wordsInADoc = doc.split("\\s+");

            for (int j = 0; j < wordsInADoc.length; j++) {
                boolean matchFound = true;
                for (int k = 0; k < searchWords.length; k++) {
                    if (j + k >= wordsInADoc.length || !searchWords[k].equals(wordsInADoc[j + k])) {
                        matchFound = false;
                        break;
                    }
                }
                if (matchFound) {
                    result.add(i);
                    break;
                }
            }
        }
        return result;
    }

    //1. find all docs that have a certain search word/s

    /*
    Ethan Albright,
    Additional team member Erica helped and collaborate with me through discord
     */
    public List<Integer> search(String searchPhrase, Map<String, List<List<Integer>>> index) {
        List<Integer> searchResult = new ArrayList<>(); // do not change
        //split search input for spaces
        String[] words = searchPhrase.trim().toLowerCase().split("\\s+");

        List<Integer> docList = getCommon(words, index);
        if (docList.isEmpty()) {
            return searchResult;
        }
        for (Integer docId : docList) {
            List<List<Integer>> positions = new ArrayList<>();

            for (String singleWord : words) {
                    positions.add(index.get(singleWord).get(docId));
            }
            if (matchFound(positions)) {
                searchResult.add(docId);
            }
        }
        Collections.sort(searchResult);

        return searchResult; // do not change. variable "index" is the result that this function should return
    }

    public boolean matchFound(List<List<Integer>> positions) {
        HashMap<Integer, Integer> locations = new HashMap<>();
        int position = positions.size();

        for (int i = 0; i < positions.size(); i++) {
           List<Integer> positionLoc = positions.get(i);
                for(Integer pos: positionLoc){
                    int num = pos - i;
                    if(!locations.containsKey(num)){
                        locations.put(num, 0);
                    }
                    int countNum = locations.get(num) + 1;
                    if(countNum == position){
                        return true;
                    }
                    locations.put(num, countNum);;
                }
            }

        return false;
    }

    public List<Integer> getCommon(String[] words, Map<String, List<List<Integer>>> index) {
        Map<Integer, Integer> freqMap = new HashMap<>();

        //go through each word
        for (String word : words) {
            //get list of lists if word is found
            if (index.containsKey(word)) {
                List<List<Integer>> docList = index.get(word);
                //go through each doc list
                for (int i = 0; i < docList.size(); i++) {
                    List<Integer> positions = docList.get(i);
                    if (!positions.isEmpty()) {
                        //if first occurrence, add it to frequency value
                        if(!freqMap.containsKey(i)){
                            freqMap.put(i,0);
                        }
                        //update freq map by putting back into
                        int freq = freqMap.get(i);
                        freqMap.put(i, freq+1);
                    }
                }
            }
        }

        //return list of frequency occurrences
        List<Integer> newList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            if (entry.getValue() == words.length) {
                newList.add(entry.getKey());
            }
        }
        return newList;
    }
    /*
    Extra credit
     */
}
