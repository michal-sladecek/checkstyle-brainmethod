package lasaris.homework.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class BrainMethodCheck extends AbstractCheck {

    boolean inMethod = false;
    MethodLineLength lineLengthCheck = new MethodLineLength();
    CyclomaticComplexity cyclomaticComplexityCheck = new CyclomaticComplexity();
    VariableCount variableCount = new VariableCount();
    ControlNesting controlNesting = new ControlNesting();

    int maxLinesOfCode = 20;
    int maxCyclomaticComplexity = 5;
    int maxNesting = 2;
    int maxVariables = 4;



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
        inMethod = true;
        lineLengthCheck.visitMethodDefinition(ast);
        cyclomaticComplexityCheck.visitMethodDefinition(ast);
        variableCount.visitMethodDefinition(ast);
        controlNesting.visitMethodDefinition(ast);
    }

    private void processVisitNormalToken(DetailAST ast){
        if(!inMethod) return;

        lineLengthCheck.visitToken(ast);
        cyclomaticComplexityCheck.visitToken(ast);
        variableCount.visitToken(ast);
        controlNesting.visitToken(ast);
    }



    private void processLeaveMethodDefinition(DetailAST ast){
        inMethod = false;
/*
        // Uncomment for debugging purposes
        log(ast.getLineNo(), String.format("Method has %d LOC.",lineLengthCheck.getMetric()));
        log(ast.getLineNo(), String.format("Method has %d cyclomatic complexity.",cyclomaticComplexityCheck.getMetric()));
        log(ast.getLineNo(), String.format("Method has %d variable definitions.",variableCount.getMetric()));
        log(ast.getLineNo(), String.format("Method has %d control nesting depth.",controlNesting.getMetric()));
*/
        if(     lineLengthCheck.getMetric() > maxLinesOfCode &&
                cyclomaticComplexityCheck.getMetric() > maxCyclomaticComplexity &&
                variableCount.getMetric() > maxVariables &&
                controlNesting.getMetric() > maxNesting
        ){
            log(ast.getLineNo(), "Brain method");
        }

        lineLengthCheck.cleanupAfterMethod();
        cyclomaticComplexityCheck.cleanupAfterMethod();
        variableCount.cleanupAfterMethod();
        controlNesting.cleanupAfterMethod();
    }
    private void processLeaveNormalToken(DetailAST ast) {
        if(!inMethod) return;

        controlNesting.leaveToken(ast);
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
        } else {
            processLeaveNormalToken(ast);
        }
    }
}
