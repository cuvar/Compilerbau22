package compiler.ast;

import java.io.OutputStreamWriter;

public class ASTCompareExprNode extends ASTExprNode {

    public ASTExprNode m_lhs;
    public ASTExprNode m_rhs;
    public compiler.Token.Type m_type;

    public ASTCompareExprNode(ASTExprNode lhs, ASTExprNode rhs, compiler.TokenIntf.Type type) {
        m_lhs = lhs;
        m_rhs = rhs;
        m_type = type;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        switch (m_type) {
            case LESS:
                outStream.write("< \n");
                break;
            case GREATER:
                outStream.write("> \n");
                break;
            case EQUAL:
                outStream.write("== \n");
                break;
            default:
                break;
        }
        String childIndent = indent + "  ";
        m_lhs.print(outStream, childIndent);
        m_rhs.print(outStream, childIndent);
    }

    @Override
    public int eval() {
        switch (m_type) {
            case LESS:
                return m_lhs.eval() < m_rhs.eval() ? 1 : 0;
            case GREATER:
                return m_lhs.eval() > m_rhs.eval() ? 1 : 0;
            case EQUAL:
                return m_lhs.eval() == m_rhs.eval() ? 1 : 0;
            default:
                return 0;
        }
    }
}
