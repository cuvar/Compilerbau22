import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import machines.StateMachineChar;
import machines.StateMachineDecimals;
import machines.StateMachineGanzzahl;
import machines.StateMachineIdentifier;
import machines.StateMachineKeywords;
import machines.StateMachineLineComment;
import machines.StateMachineMultiLineComment;
import machines.StateMachineStringLiteral;
import machines.StateMachineWhitespaces;

public class LexerTest implements test.TestCaseIntf {


	public String executeTest(compiler.FileReaderIntf fileReader) throws Exception {
	    String input = new String();
	    while (fileReader.lookAheadChar() != 0) {
	        input += fileReader.lookAheadChar();
	        fileReader.advance();
	    }
        compiler.Lexer lexer = new compiler.Lexer();
        compiler.StateMachineBase ifMachine = new StateMachineKeywords("if", compiler.TokenIntf.Type.IF);
        lexer.addMachine(ifMachine);
        compiler.StateMachineBase elseMachine = new StateMachineKeywords("else", compiler.TokenIntf.Type.ELSE);
        lexer.addMachine(elseMachine);
        compiler.StateMachineBase identifierMachine = new StateMachineIdentifier();
        lexer.addMachine(identifierMachine);
        compiler.StateMachineBase ganzzahlMachine = new StateMachineGanzzahl();
        lexer.addMachine(ganzzahlMachine);
        compiler.StateMachineBase decimalsMachine = new StateMachineDecimals();
        lexer.addMachine(decimalsMachine);
        compiler.StateMachineBase stringMachine = new StateMachineStringLiteral();
        lexer.addMachine(stringMachine);
        compiler.StateMachineBase charMachine = new StateMachineChar();
        lexer.addMachine(charMachine);
        compiler.StateMachineBase lineCommentMachine = new StateMachineLineComment();
        lexer.addMachine(lineCommentMachine);
        compiler.StateMachineBase multiLineCommentMachine = new StateMachineMultiLineComment();
        lexer.addMachine(multiLineCommentMachine);
        compiler.StateMachineBase whitespaceMachine = new StateMachineWhitespaces();
        lexer.addMachine(whitespaceMachine);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "UTF-8");
        lexer.processInput(input, outStreamWriter);
		return new String(outStream.toByteArray(), "UTF-8");
	}
}
