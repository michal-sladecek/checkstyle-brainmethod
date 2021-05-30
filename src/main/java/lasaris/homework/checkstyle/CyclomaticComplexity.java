package lasaris.homework.checkstyle;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.math.BigInteger;



public class CyclomaticComplexity implements MethodMetric {
    /** The initial current value. */
    private static final BigInteger INITIAL_VALUE = BigInteger.ONE;


    /** The current value. */
    private BigInteger currentValue = INITIAL_VALUE;

    public int getMetric(){
        return currentValue.intValue();
    }

    @Override
    public void visitMethodDefinition(DetailAST ast) {

    }

    @Override
    public void cleanupAfterMethod() {
        currentValue = INITIAL_VALUE;
    }

    /**
     * Hook called when visiting a token. Will not be called the method
     * definition tokens.
     *
     * @param ast the token being visited
     */
    @Override
    public void visitToken(DetailAST ast) {
        switch(ast.getType()){
            case TokenTypes.LITERAL_WHILE:
            case TokenTypes.LITERAL_DO:
            case TokenTypes.LITERAL_FOR:
            case TokenTypes.LITERAL_IF:
            case TokenTypes.LITERAL_SWITCH:
            case TokenTypes.LITERAL_CASE:
            case TokenTypes.LITERAL_CATCH:
            case TokenTypes.QUESTION:
            case TokenTypes.LAND:
            case TokenTypes.LOR:
                incrementCurrentValue(BigInteger.ONE);
            default:
                break;
        }
    }

    /**
     * Increments the current value by a specified amount.
     *
     * @param amount the amount to increment by
     */
    private void incrementCurrentValue(BigInteger amount) {
        currentValue = currentValue.add(amount);
    }


}