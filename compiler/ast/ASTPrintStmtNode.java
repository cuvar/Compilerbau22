package compiler.ast;

import java.io.OutputStreamWriter;

public class ASTPrintStmtNode extends ASTStmtNode {
    private final ASTExprNode node;

    public ASTPrintStmtNode(ASTExprNode node) {
        this.node = node;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.append(indent);
        outStream.append("PRINT\n");

        String childIndent = indent + "  ";
        node.print(outStream, childIndent);
    }

    @Override
    public void execute() {
        System.out.println(node.eval());
    }
}
