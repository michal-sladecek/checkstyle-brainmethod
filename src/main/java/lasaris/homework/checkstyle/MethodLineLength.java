package lasaris.homework.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.sizes.MethodLengthCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;


/*
We did not find a way to extend the MethodLengthCheck to not log
 */



/*
MethodLineLength is derived from the code of MethodLengthCheck.
https://github.com/checkstyle/checkstyle/blob/master/src/main/java/com/puppycrawl/tools/checkstyle/checks/sizes/MethodLengthCheck.java
We did not find a way to extend this class in other way than completely rewriting it.
 */


public class MethodLineLength  implements MethodMetric {

    int currentMethodLength = 0;

    public int getMetric(){
        return currentMethodLength;
    }


    public void visitMethodDefinition(DetailAST ast) {
        final DetailAST openingBrace = ast.findFirstToken(TokenTypes.SLIST);
        if (openingBrace != null) {
            final DetailAST closingBrace =
                    openingBrace.findFirstToken(TokenTypes.RCURLY);
            currentMethodLength = getLengthOfBlock(openingBrace, closingBrace);
        }
    }

    public void cleanupAfterMethod(){
        currentMethodLength = 0;
    }

    public void visitToken(DetailAST ast){
        return;
    }

    @Override
    public void leaveToken(DetailAST ast) {

    }

    /**
     * Returns length of code. We also count comments and empty lines.
     *
     * @param openingBrace block opening brace
     * @param closingBrace block closing brace
     * @return number of lines with code for current block
     */
    private int getLengthOfBlock(DetailAST openingBrace, DetailAST closingBrace) {
        int length = closingBrace.getLineNo() - openingBrace.getLineNo() + 1;
        return length;
    }

}
