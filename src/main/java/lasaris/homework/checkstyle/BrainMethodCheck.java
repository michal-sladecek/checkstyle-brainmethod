package lasaris.homework.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.checks.sizes.MethodLengthCheck;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class BrainMethodCheck extends AbstractCheck {


    MethodLengthCheck lineLengthCheck;

    int maxLinesOfCode;
    int maxCyclomaticComplexity;
    int maxNesting;
    int maxVariables;

    public void setMaxLinesOfCode(int maxLinesOfCode) {
        this.maxLinesOfCode = maxLinesOfCode;
    }

    public void setMaxCyclomaticComplexity(int maxCyclomaticComplexity) {
        this.maxCyclomaticComplexity = maxCyclomaticComplexity;
    }

    public void setMaxNesting(int maxNesting) {
        this.maxNesting = maxNesting;
    }

    public void setMaxVariables(int maxVariables) {
        this.maxVariables = maxVariables;
    }


    @Override
    public int[] getDefaultTokens() {
        return new int[] {
                TokenTypes.METHOD_DEF,
                TokenTypes.CTOR_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[0];
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[0];
    }


    public void processMethodDefinition(DetailAST ast){
        MethodLineLength lineLengthChecker = new MethodLineLength();
        int lineLength = lineLengthChecker.methodLength(ast);
    }


    @Override
    public void visitToken(DetailAST ast) {
        if(ast.getType() == TokenTypes.METHOD_DEF || ast.getType() == TokenTypes.CTOR_DEF){
            processMethodDefinition(ast);
        }

        
    }

    @Override
    public void leaveToken(DetailAST ast) {

    }


}
