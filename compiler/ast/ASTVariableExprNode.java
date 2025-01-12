package compiler.ast;

import compiler.Symbol;
import compiler.SymbolTable;

import java.io.OutputStreamWriter;

public class ASTVariableExprNode extends ASTExprNode{

    public String identifier;
    private SymbolTable symbolTable;

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write(this.identifier);
    }

    public ASTVariableExprNode(String identifier, SymbolTable symbolTable) {
        this.identifier = identifier;
        this.symbolTable = symbolTable;
    }

    @Override
    public int eval() {

        return symbolTable.getSymbol(this.identifier).m_number;
    }
}
