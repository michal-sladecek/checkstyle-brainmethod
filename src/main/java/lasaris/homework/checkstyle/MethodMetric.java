package lasaris.homework.checkstyle;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

public interface MethodMetric {

    /*
       Should be called when the class is finished with visiting the method.
       Otherwise the metric could have wrong data.
     */
    public int getMetric();

    /*
    Initialization of method. Should only get AST with following values:
        TokenTypes.METHOD_DEF,
        TokenTypes.CTOR_DEF,
        TokenTypes.INSTANCE_INIT,
        TokenTypes.STATIC_INIT,
     */
    public void visitMethodDefinition(DetailAST ast);

    /*
    Indicates that the processing of current method is finished and class can clean up.
     */
    public void cleanupAfterMethod();
    
}
