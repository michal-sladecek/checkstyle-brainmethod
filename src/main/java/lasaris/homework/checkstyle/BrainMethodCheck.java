package lasaris.homework.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class BrainMethodCheck extends AbstractCheck {


    MethodLineLength lineLengthCheck = new MethodLineLength();
    CyclomaticComplexity cyclomaticComplexityCheck = new CyclomaticComplexity();
    VariableCount variableCount = new VariableCount();

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
                TokenTypes.INSTANCE_INIT,
                TokenTypes.STATIC_INIT,
                TokenTypes.LITERAL_WHILE,
                TokenTypes.LITERAL_DO,
                TokenTypes.LITERAL_FOR,
                TokenTypes.LITERAL_IF,
                TokenTypes.LITERAL_SWITCH,
                TokenTypes.LITERAL_CASE,
                TokenTypes.LITERAL_CATCH,
                TokenTypes.QUESTION,
                TokenTypes.LAND,
                TokenTypes.LOR,
                TokenTypes.VARIABLE_DEF
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


    private void processVisitMethodDefinition(DetailAST ast){
        lineLengthCheck.visitMethodDefinition(ast);
        cyclomaticComplexityCheck.visitMethodDefinition(ast);
        variableCount.visitMethodDefinition(ast);
    }

    private void processVisitNormalToken(DetailAST ast){
        lineLengthCheck.visitToken(ast);
        cyclomaticComplexityCheck.visitToken(ast);
        variableCount.visitToken(ast);
    }



    private void processLeaveMethodDefinition(DetailAST ast){
        if(     lineLengthCheck.getMetric() > maxLinesOfCode &&
                cyclomaticComplexityCheck.getMetric() > maxCyclomaticComplexity &&
                variableCount.getMetric() > maxVariables
        ){
            log(ast.getLineNo(), "Brain method");
        }

        lineLengthCheck.cleanupAfterMethod();
        cyclomaticComplexityCheck.cleanupAfterMethod();
        variableCount.cleanupAfterMethod();
    }


    @Override
    public void visitToken(DetailAST ast) {
        if(ast.getType() == TokenTypes.METHOD_DEF || ast.getType() == TokenTypes.CTOR_DEF){
            processVisitMethodDefinition(ast);
        } else{
            processVisitNormalToken(ast);
        }
    }


    @Override
    public void leaveToken(DetailAST ast) {
        if(ast.getType() == TokenTypes.METHOD_DEF || ast.getType() == TokenTypes.CTOR_DEF){
            processLeaveMethodDefinition(ast);
        }

    }


}
