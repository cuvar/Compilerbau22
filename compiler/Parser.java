package compiler;

import compiler.ast.*;

public class Parser {
    private Lexer m_lexer;

    public Parser(Lexer lexer) {
        m_lexer = lexer;
    }

    public ASTExprNode parseExpression(String val) throws Exception {
        m_lexer.init(val);
        return getQuestionMarkExpr();
    }

    ASTExprNode getParantheseExpr() throws Exception {
        Token curToken = m_lexer.lookAhead();
        m_lexer.expect(Token.Type.INTEGER);
        return new ASTIntegerLiteralNode(curToken.m_value);
    }

    // unaryexpr: (NOT | MINUS) ? paranthesisexpr
    ASTExprNode getUnaryExpr() throws Exception {
        var token = m_lexer.lookAhead().m_type;

        if (token == TokenIntf.Type.MINUS || token == TokenIntf.Type.NOT) {
            m_lexer.advance();
        }

        var parenExpr = getParantheseExpr();
        return new ASTUnaryExprNode(parenExpr, token);
    }

    ASTExprNode getMulDivExpr() throws Exception {
        return getUnaryExpr();
    }

    ASTExprNode getPlusMinusExpr() throws Exception {
        ASTExprNode result = getMulDivExpr();
        Token nextToken = m_lexer.lookAhead();
        while (nextToken.m_type == Token.Type.PLUS || nextToken.m_type == Token.Type.MINUS) {
            if (nextToken.m_type == Token.Type.PLUS) {
                m_lexer.advance();
                result = new ASTPlusMinusExprNode(result, getMulDivExpr(), Token.Type.PLUS);
            } else {
                m_lexer.advance();
                result = new ASTPlusMinusExprNode(result, getMulDivExpr(), Token.Type.MINUS);
            }
            nextToken = m_lexer.lookAhead();
        }
        return result;
    }

    ASTExprNode getBitAndOrExpr() throws Exception {
        return getPlusMinusExpr();
    }

    ASTExprNode getShiftExpr() throws Exception {
        return getBitAndOrExpr();
    }

    ASTExprNode getCompareExpr() throws Exception {
        return getShiftExpr();
    }

    ASTExprNode getAndOrExpr() throws Exception {

        ASTExprNode result = getCompareExpr();
        while (m_lexer.lookAhead().m_type == Token.Type.AND ||
                m_lexer.lookAhead().m_type == Token.Type.OR) {
            Token nextToken = m_lexer.lookAhead();
            if (nextToken.m_type == Token.Type.AND) {
                m_lexer.expect(Token.Type.AND);
                result = new ASTAndOrExprNode(result, getCompareExpr(), Token.Type.AND);
            } else {
                m_lexer.expect(Token.Type.OR);
                result = new ASTAndOrExprNode(result, getCompareExpr(), Token.Type.OR);
            }
        }
        return result;
    }

    ASTExprNode getQuestionMarkExpr() throws Exception {

        ASTExprNode toResolve = getAndOrExpr();
        while (m_lexer.lookAhead().m_type == Token.Type.QUESTIONMARK) {
            m_lexer.expect(Token.Type.QUESTIONMARK);
            ASTExprNode trueNum = getAndOrExpr();
            m_lexer.expect(Token.Type.DOUBLECOLON);
            ASTExprNode falseNum = getAndOrExpr();
            toResolve = new ASTQuestionmarkExprNode(toResolve, trueNum, falseNum);
        }
        return toResolve;
    }
}