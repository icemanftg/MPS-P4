package ro.mps.gui.screens.generators;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lapa
 * Date: 24.11.2012
 * Time: 22:06
 */
public class ParagraphsTextGenerator {
    private static final String PLACEHOLDER_TEXT = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed\n" +
            " diam nonummy nibh euismod tincidunt ut laoreet dolore magna \n" +
            "aliquam erat volutpat. Ut wisi enim ad minim veniam, quis\n" +
            " nostrud exerci tation ullamcorper suscipit lobortis nisl ut\n" +
            " aliquip ex ea commodo consequat. Duis autem vel eum iriure\n" +
            " dolor in hendrerit in vulputate velit esse molestie consequat,\n" +
            " vel illum dolore eu feugiat nulla facilisis at vero eros et\n" +
            " accumsan et iusto odio dignissim qui blandit praesent luptatum\n" +
            " zzril delenit augue duis dolore te feugait nulla facilisi.\n" +
            " Nam liber tempor cum soluta nobis eleifend option congue nihil\n" +
            " imperdiet doming id quod mazim placerat facer possim assum.";

    /**
     * Returns a list of paragraphs
     *
     * @param numberOfParagraphs - number of paragraphs
     * @return list of paragraphs
     */
    public static List<String> getParagraphsText(int numberOfParagraphs) {
        List<String> paragraphsList = new LinkedList<String>();
        for (int i = 0; i < numberOfParagraphs; i++) {
            paragraphsList.add(PLACEHOLDER_TEXT);
        }

        return paragraphsList;
    }
}
