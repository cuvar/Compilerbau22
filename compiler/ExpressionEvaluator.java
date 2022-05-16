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
		m_lexer.expect(Token.Type.INTEGER);
		return Integer.valueOf(curToken.m_value);
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
		while (m_lexer.lookAhead().m_type == Token.Type.PLUS || m_lexer.lookAhead().m_type == Token.Type.MINUS) {
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

	int getBitAndOrExpr() throws Exception {
		return getPlusMinusExpr();
	}

	int getShiftExpr() throws Exception {
		return getBitAndOrExpr();
	}

	int getCompareExpr() throws Exception {
		return getShiftExpr();
	}

	int getAndOrExpr() throws Exception {
		return getCompareExpr();
	}

	// questionmark: number questionmark number doublecolon number
	int getQuestionMarkExpr() throws Exception {
		int toResolve = getAndOrExpr();
		m_lexer.expect(Token.Type.QUESTIONMARK);
		int trueNum = getAndOrExpr();
		m_lexer.expect(Token.Type.DOUBLECOLON);
		int falseNum = getAndOrExpr();

		return toResolve == 1 ? trueNum : falseNum;
	}
}
