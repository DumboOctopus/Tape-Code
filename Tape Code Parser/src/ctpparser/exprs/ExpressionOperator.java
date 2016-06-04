package ctpparser.exprs;

/**
 * Created on 6/3/16.
 */
public class ExpressionOperator {
    private String symbol;
    private ExpressionOperation operation;

    public ExpressionOperator(String symbol, ExpressionOperation operation) {
        this.symbol = symbol;
        this.operation = operation;
    }


    public String getAssembly(Expression ex1, Expression ex2)
    {
        return operation.doOperation(ex1, ex2);
    }


    public interface ExpressionOperation
    {
        String doOperation(Expression ex1, Expression ex2);
    }
}
