package compiler.ast;

import java.io.OutputStreamWriter;

public class ASTParentheseNode extends ASTExprNode {
    public ASTExprNode m_inner;
    public compiler.Token.Type m_type;

    public ASTParentheseNode(ASTExprNode inner) {
        m_inner = inner;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("PARENTHESE\n");
        String childIndent = indent + "  ";
        m_inner.print(outStream, childIndent);
    }

    @Override
    public int eval() {
        return m_inner.eval();
    }

}
