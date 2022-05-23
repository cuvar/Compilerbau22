package compiler;
import compiler.ast.*;

public class Parser {
    private Lexer m_lexer;
    private SymbolTable m_symbolTable;
    
    public Parser(Lexer lexer) {
        m_lexer = lexer;
        m_symbolTable = new SymbolTable();
    }
    
    public SymbolTable getSymbolTable() {
        return m_symbolTable;
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
        if(curToken.m_type == Token.Type.INTEGER){
            m_lexer.advance();
            return new ASTIntegerLiteralNode(curToken.m_value);
        }
        else {
            return getVariableExpr();
        }
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
        ASTExprNode result = getPlusMinusExpr();
        Token nextToken = m_lexer.lookAhead();
        while (nextToken.m_type == Token.Type.BITAND || nextToken.m_type == Token.Type.BITOR) {
            if (nextToken.m_type == Token.Type.BITAND) {
                m_lexer.advance();
                result = new ASTBitAndOrExprNode(result, getPlusMinusExpr(), Token.Type.BITAND);
            } else {
                m_lexer.advance();
                result = new ASTBitAndOrExprNode(result, getPlusMinusExpr(), Token.Type.BITOR);
            }
            nextToken = m_lexer.lookAhead();
        }
        return result;
    }

    ASTExprNode getShiftExpr() throws Exception {
        return getBitAndOrExpr();
    }

    ASTExprNode getCompareExpr() throws Exception {
        ASTExprNode result = getShiftExpr();
        Token nextToken = m_lexer.lookAhead();
        while (nextToken.m_type == Token.Type.AND || nextToken.m_type == Token.Type.OR) {
            m_lexer.advance();
            result = new ASTCompareExprNode(result, getShiftExpr(), nextToken.m_type);
            nextToken = m_lexer.lookAhead();
        }
        return result;
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
        ASTBlockStmtNode result = new ASTBlockStmtNode();
        m_lexer.expect(Token.Type.LBRACE);
        while (!m_lexer.lookAhead().equals(Token.Type.RBRACE)) {
            result.addStatement(getStmt());
        }
        m_lexer.expect(Token.Type.RBRACE);

        return result;
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

    // variableExpr: IDENTIFIER
    ASTExprNode getVariableExpr() throws Exception {
        Token token = m_lexer.lookAhead();
        if (token.m_type == Token.Type.IDENT){
            m_lexer.advance();
            return new ASTVariableExprNode(token.m_value, getSymbolTable());
        }
        throw new Exception("Unexpected Statement");

    }



}