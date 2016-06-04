package ctpparser.exprs;

/**
 * Created on 6/3/16.
 */
public class LiteralExpression extends Expression {
    private int value;

    public LiteralExpression(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }
}
