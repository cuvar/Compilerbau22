package compiler;

public class ExpressionEvaluator {
    private Lexer m_lexer;

    public ExpressionEvaluator(Lexer lexer) {
        m_lexer = lexer;
    }

    public int eval(String val) throws Exception {
        m_lexer.init(val);
        return getQuestionMarkExpr();
    }

    int getParantheseExpr() throws Exception {
        Token curToken = m_lexer.lookAhead();
        int result = 0;

        if (curToken.m_type == Token.Type.LPAREN) {
            m_lexer.expect(Token.Type.LPAREN);
            result = getQuestionMarkExpr();
            m_lexer.expect(Token.Type.RPAREN);

        } else if (curToken.m_type == Token.Type.INTEGER) {
            result = Integer.valueOf(curToken.m_value);
        }

        return result;
    }

    int getUnaryExpr() throws Exception {
        return getParantheseExpr();
    }

    int getMulDivExpr() throws Exception {
        return getUnaryExpr();
    }

    // plusMinusExpr : mulDivExpr ((PLUS|MINUS) mulDivExpr)*
    int getPlusMinusExpr() throws Exception {
        int result = getMulDivExpr();
        while (m_lexer.lookAhead().m_type == Token.Type.PLUS ||
                m_lexer.lookAhead().m_type == Token.Type.MINUS) {
            Token nextToken = m_lexer.lookAhead();
            if (nextToken.m_type == Token.Type.PLUS) {
                m_lexer.expect(Token.Type.PLUS);
                result += getMulDivExpr();
            } else {
                m_lexer.expect(Token.Type.MINUS);
                result -= getMulDivExpr();
            }
        }
        return result;
    }

    // bitAndOrExpr : plusMinusExpr (( BITAND | BITOR ) plusMinusExpr)*
    int getBitAndOrExpr() throws Exception {
        int result = getPlusMinusExpr();
        while (m_lexer.lookAhead().m_type == Token.Type.BITAND ||
                m_lexer.lookAhead().m_type == Token.Type.BITOR) {
            Token nextToken = m_lexer.lookAhead();
            if (nextToken.m_type == Token.Type.BITAND) {
                m_lexer.expect(Token.Type.BITAND);
                result = result & getPlusMinusExpr();
            } else {
                m_lexer.expect(Token.Type.BITOR);
                result = result | getPlusMinusExpr();
            }
        }
        return result;
    }

    int getShiftExpr() throws Exception {
        return getBitAndOrExpr();
    }

    int getCompareExpr() throws Exception {
        int result = getShiftExpr();
        while (m_lexer.lookAhead().m_type == Token.Type.EQUAL ||
                m_lexer.lookAhead().m_type == Token.Type.GREATER ||
                m_lexer.lookAhead().m_type == Token.Type.LESS) {
            Token nextToken = m_lexer.lookAhead();
            switch (nextToken.m_type) {
                case EQUAL:
                    m_lexer.expect(Token.Type.EQUAL);
                    result = (result == getShiftExpr()) ? 1 : 0;
                    break;
                case GREATER:
                    m_lexer.expect(Token.Type.GREATER);
                    result = (result > getShiftExpr()) ? 1 : 0;
                    break;
                case LESS:
                    m_lexer.expect(Token.Type.LESS);
                    result = (result < getShiftExpr()) ? 1 : 0;
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    int getAndOrExpr() throws Exception {
        int result = getCompareExpr();

        while (
                m_lexer.lookAhead().m_type == Token.Type.AND ||
                        m_lexer.lookAhead().m_type == Token.Type.OR) {
            Token nextToken = m_lexer.lookAhead();
            if (nextToken.m_type == Token.Type.AND) {
                m_lexer.expect(Token.Type.AND);
                result &= getCompareExpr();
            } else {
                m_lexer.expect(Token.Type.OR);
                result |= getCompareExpr();
            }

        }
        return result;
    }

    int getQuestionMarkExpr() throws Exception {
        return getAndOrExpr();
    }
}
