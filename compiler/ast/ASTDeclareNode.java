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

    }

    @Override
    public void execute() {
        if(symbolTable.getSymbol(identifier) != null) {
            System.err.printf("Das Symbol \"%s\" ist bereits vergeben!\n", identifier);
            return;
        }

        symbolTable.createSymbol(identifier);
    }

}
