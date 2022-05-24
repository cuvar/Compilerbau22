import java.io.OutputStreamWriter;

public class ExpressionParserMain {

    public static void main(String[] args) throws Exception {
        compiler.Lexer lexer = new compiler.Lexer();
        compiler.Parser exprParser = new compiler.Parser(lexer);
        compiler.ast.ASTExprNode expr = exprParser.parseExpression("{}");

        OutputStreamWriter outStream = new OutputStreamWriter(System.out, "UTF-8");
        expr.print(outStream, "");
        outStream.flush();

        System.out.println(expr.eval());
    }

}
