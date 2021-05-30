package lasaris.homework.checkstyle;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ControlNesting implements MethodMetric{
    int maxNestingLevel = 0;
    int currentNestingLevel = 0;
    @Override
    public int getMetric() {
        return maxNestingLevel;
    }

    @Override
    public void visitMethodDefinition(DetailAST ast) {
    }

    @Override
    public void cleanupAfterMethod() {
        maxNestingLevel = 0;
        currentNestingLevel = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if(ast.getType() == TokenTypes.LITERAL_IF || ast.getType() == TokenTypes.LITERAL_ELSE ||
                ast.getType() == TokenTypes.LITERAL_SWITCH || ast.getType() == TokenTypes.QUESTION){
            currentNestingLevel++;
            maxNestingLevel = Math.max(currentNestingLevel, maxNestingLevel);
        }
    }

    public void leaveToken(DetailAST ast) {
        if(ast.getType() == TokenTypes.LITERAL_IF || ast.getType() == TokenTypes.LITERAL_ELSE ||
                ast.getType() == TokenTypes.LITERAL_SWITCH || ast.getType() == TokenTypes.QUESTION){
            currentNestingLevel--;
        }
    }
}
