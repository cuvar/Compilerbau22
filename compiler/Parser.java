package compiler;
import compiler.ast.*;

public class Parser {
    private Lexer m_lexer;
    
    public Parser(Lexer lexer) {
        m_lexer = lexer;
    }
    
    public ASTExprNode parseExpression(String val) throws Exception {
        m_lexer.init(val);
        return getExpr();
    }
    
    ASTExprNode getExpr() throws Exception {
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
            m_lexer.advance();
            result = new ASTPlusMinusExprNode(result, getMulDivExpr(), nextToken.m_type);
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
        Token nextToken = m_lexer.lookAhead();
        while (nextToken.m_type == Token.Type.AND || nextToken.m_type == Token.Type.OR) {
            m_lexer.advance();
            result = new ASTAndOrExprNode(result, getCompareExpr(), nextToken.m_type);
            nextToken = m_lexer.lookAhead();
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
  
    // blockstmt: LBRACE stmtlist RBRACE
    // stmtlist: stmt stmtlist
    // stmtlist: epsilon
    ASTStmtNode getBlockStmt() throws Exception {
        return null;
    }
    
    // stmt: declareStmt
    // stmt: assignStmt
    // stmt: printStmt
    // stmt: declareStmt
    // stmt: assignStmt
    // stmt: printStmt
    ASTStmtNode getStmt() throws Exception {
        Token token = m_lexer.lookAhead();
        if (token.m_type == Token.Type.DECLARE) {
            return getDeclareStmt();
        } else if (token.m_type == Token.Type.IDENT) {
            return getAssignStmt();
        } else if (token.m_type == Token.Type.PRINT) {
            return getPrintStmt();
        }
        throw new Exception("Unexpected Statement");
    }

    // declareStmt: DECLARE IDENTIFIER SEMICOLON
    ASTStmtNode getDeclareStmt() throws Exception {
        return null;
    }

    // assignStmt: IDENTIFER ASSIGN expr SEMICOLON
    ASTStmtNode getAssignStmt() throws Exception {
        return null;
    }

    // printStmt: PRINT expr SEMICOLON
    ASTStmtNode getPrintStmt() throws Exception {
        m_lexer.expect(TokenIntf.Type.PRINT);
        var node = getExpr();
        m_lexer.expect(TokenIntf.Type.SEMICOLON);
        return new ASTPrintStmtNode(node);
    }

}