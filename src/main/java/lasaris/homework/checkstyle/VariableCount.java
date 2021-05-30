package lasaris.homework.checkstyle;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class VariableCount implements  MethodMetric{

    int numberOfVariables = 0;
    @Override
    public int getMetric() {
        return numberOfVariables;
    }

    @Override
    public void visitMethodDefinition(DetailAST ast) {
        return;
    }

    @Override
    public void cleanupAfterMethod() {
        numberOfVariables = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if(ast.getType() == TokenTypes.VARIABLE_DEF){
            numberOfVariables++;
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {

    }
}
