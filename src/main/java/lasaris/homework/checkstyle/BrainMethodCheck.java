package lasaris.homework.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;

public class BrainMethodCheck extends AbstractCheck {
    @Override
    public int[] getDefaultTokens() {
        return new int[0];
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[0];
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[0];
    }
}
