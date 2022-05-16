package compiler.ast;

import java.io.OutputStreamWriter;

public class ASTQuestionmarkExprNode extends ASTExprNode {

	private ASTExprNode toEval;
	private ASTExprNode trueCase;
	private ASTExprNode falseCase;

	public ASTQuestionmarkExprNode(ASTExprNode toEval, ASTExprNode trueCase, ASTExprNode falseCase) {
		super();
		this.toEval = toEval;
		this.trueCase = trueCase;
		this.falseCase = falseCase;
	}

	@Override
	public void print(OutputStreamWriter outStream, String indent) throws Exception {
		outStream.write(indent);
		outStream.write("Questionmark\n");
		String childIndent = indent + "  ";
		toEval.print(outStream, childIndent);
		trueCase.print(outStream, childIndent);
		falseCase.print(outStream, childIndent);
	}

	@Override
	public int eval() {
		return toEval.eval() != 0 ? trueCase.eval() : falseCase.eval();
	}

}
