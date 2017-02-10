package org.copticchurch.library.discoverorthodoxy.logic.fileimport;

import org.copticchurch.library.discoverorthodoxy.BrainPhaserApplication;
import org.copticchurch.library.discoverorthodoxy.logic.fileimport.bpc.BPCObjects;
import org.copticchurch.library.discoverorthodoxy.logic.fileimport.bpc.BPCRead;
import org.copticchurch.library.discoverorthodoxy.logic.fileimport.exceptions.FileFormatException;
import org.copticchurch.library.discoverorthodoxy.model.Answer;
import org.w3c.dom.Node;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.copticchurch.library.discoverorthodoxy.logic.fileimport.bpc.BPCWrite;
import org.copticchurch.library.discoverorthodoxy.logic.fileimport.exceptions.ElementAmountException;
import org.copticchurch.library.discoverorthodoxy.logic.fileimport.exceptions.InvalidAttributeException;
import org.copticchurch.library.discoverorthodoxy.logic.fileimport.exceptions.UnexpectedElementException;
import org.copticchurch.library.discoverorthodoxy.model.Category;
import org.copticchurch.library.discoverorthodoxy.model.Challenge;

/**
 * Created by Daniel Hoogen on 19/02/2016.
 * <p/>
 * Contains the logic for importing files
 */
public class FileImport {
    /**
     * Imports a .bpc file containing categories with challenges and their answers into the database
     *
     * @param is          the input stream of the file to be imported
     * @param application the BrainPhaserApplication instance
     * @throws FileFormatException        if file is no xml file
     * @throws UnexpectedElementException if an unexpected element was fond in the file
     * @throws ElementAmountException     if an element occurs more or less often than expected
     * @throws InvalidAttributeException  if an attribute has an invalid value
     */
    public static void importBPC(InputStream is, BrainPhaserApplication application)
            throws FileFormatException, UnexpectedElementException, ElementAmountException,
            InvalidAttributeException {

        //Get root element
        Node categoriesNode = BPCRead.getCategoriesNode(is);

        //Get the root's child nodes
        Node childCategories = categoriesNode.getFirstChild();

        //Create lists for saving the categories, challenges and answers
        List<Category> categoryList = new ArrayList<>();
        List<Challenge> challengeList = new ArrayList<>();
        List<Answer> answerList = new ArrayList<>();

        //All categories, challenges and answers are read first
        //So if there is any syntax error in the file, nothing will be imported
        long i = 0;
        long nextChallengeId = 0;
        while (childCategories != null) {
            if (childCategories.getNodeType() == Node.ELEMENT_NODE) {
                nextChallengeId = BPCObjects.readCategory(childCategories, i, nextChallengeId,
                        categoryList, challengeList, answerList);
                i++;
            }

            childCategories = childCategories.getNextSibling();
        }
        if (i == 0) throw new ElementAmountException("<category>", ">0", "0");

        //No syntax errors were found, so the read information is being written
        BPCWrite writer = new BPCWrite(application);
        writer.writeAll(categoryList, challengeList, answerList);
    }
}