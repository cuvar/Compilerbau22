package compiler.ast;

import compiler.TokenIntf;

import java.io.OutputStreamWriter;

public class ASTUnaryExprNode extends ASTExprNode {

    private final ASTExprNode parenthesisExpr;
    private final compiler.TokenIntf.Type type;

    public ASTUnaryExprNode(ASTExprNode parenthesisExpr, compiler.TokenIntf.Type type) {
        this.parenthesisExpr = parenthesisExpr;
        this.type = type;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);

        if (type != TokenIntf.Type.MINUS && type != TokenIntf.Type.NOT) {
            parenthesisExpr.print(outStream, "");
            return;
        }

        String out = (type == TokenIntf.Type.MINUS) ? "MINUS" : "NOT";
        out += " \n";
        outStream.write(out);

        String childIndent = indent + "  ";
        parenthesisExpr.print(outStream, childIndent);
    }

    @Override
    public int eval() {
        return switch (type) {
            case MINUS -> -parenthesisExpr.eval();
            case NOT -> (parenthesisExpr.eval() == 0) ? 1 : 0;
            default -> parenthesisExpr.eval();
        };
    }
}
