package org.hitbioinfo.exp1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateNewTextControl {
    public static String execute(WordsGraphData wordsGraphData, String astring) {
        List<String> wordlist = new ArrayList<>();
        StringBuilder builder = new StringBuilder(astring.replaceAll("[^a-zA-Z]+[^a-zA-Z]*", " "));
        for (int i = 0; i < builder.length(); i++) {
            if ((builder.charAt(i) >= 'a' && builder.charAt(i) <= 'z')
                    || (builder.charAt(i) >= 'A' && builder.charAt(i) <= 'Z')) {
                StringBuilder temp = new StringBuilder("");
                int j;
                for (j = i; j < builder.length() && !Character.isWhitespace(builder.charAt(j)); j++) {
                    temp.append(builder.charAt(j));
                }
                wordlist.add(temp.toString());
                i = j;
            }
        }
        if (wordlist.size() < 1) {
            return astring;
        }
        StringBuilder newText = new StringBuilder("");
        newText.append(wordlist.get(0));
        for (int i = 1; i < wordlist.size(); i++) {
            String[] bridgewords = QueryBridgeWordsControl.execute(wordsGraphData,
                    wordlist.get(i - 1).toLowerCase(), wordlist.get(i).toLowerCase());
            if (bridgewords.length > 0) {
                int p = new Random().nextInt(bridgewords.length);
                newText.append(" ").append(bridgewords[p]);
            }
            newText.append(" ").append(wordlist.get(i));
        }
        return newText.toString();
    }

}
