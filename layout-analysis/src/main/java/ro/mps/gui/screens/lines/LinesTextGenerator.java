package ro.mps.gui.screens.lines;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lapa
 * Date: 25.11.2012
 * Time: 12:38
 */
public class LinesTextGenerator {
    private static final String PLACEHOLDER_TEXT =  "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed" +
                                                    " diam nonummy nibh euismod tincidunt ut laoreet dolore magna " +
                                                    "aliquam erat volutpat.";

    /**
     * Returns a list of text lines
     * @param numberOfLines
     * @return list of text lines
     */
    public static List<String> getLinesText(int numberOfLines) {
        List<String> linesList = new LinkedList<String>();
        for ( int i = 0; i < numberOfLines; i++ ) {
            linesList.add(PLACEHOLDER_TEXT);
        }

        return linesList;
    }
}
