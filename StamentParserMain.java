import java.io.OutputStreamWriter;

public class StamentParserMain {

    public static void main(String[] args) throws Exception {
        compiler.Lexer lexer = new compiler.Lexer();
        compiler.Parser parser = new compiler.Parser(lexer);
        compiler.ast.ASTStmtNode stmt = parser.parseStmt("{ DECLARE a; PRINT 5+2+a; }");
        stmt.execute();
        OutputStreamWriter outStream = new OutputStreamWriter(System.out, "UTF-8");
        stmt.print(outStream, "");
        outStream.flush();
    }

}
