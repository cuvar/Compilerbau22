package compiler.ast;

import compiler.SymbolTable;

import java.io.OutputStreamWriter;

public class ASTDeclareNode extends ASTStmtNode {
    private final SymbolTable symbolTable;
    private final String identifier;

    public ASTDeclareNode(SymbolTable symbolTable, String identifier) {
        this.symbolTable = symbolTable;
        this.identifier = identifier;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("DeclareNode " + identifier + "\n");
    }

    @Override
    public void execute() {
    }

}
