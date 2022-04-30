import machines.StateMachineDecimals;
import machines.StateMachineGanzzahl;
import machines.StateMachineIdentifier;
import machines.StateMachineKeywords;
import machines.StateMachineStringLiteral;
import machines.StateMachineWhitespaces;
import machines.StateMachineChar;
import machines.StateMachineLineComment;
import machines.StateMachineMultiLineComment;

import java.io.OutputStreamWriter;

public class LexerMain {

    public static void main(String[] args) throws Exception {
        compiler.Lexer lexer = new compiler.Lexer();
        OutputStreamWriter outStream = new OutputStreamWriter(System.out, "UTF-8");
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
        lexer.processInput("if abc /*abc\ndef*/5 else \"hello\" def 'u' //comment\n 5.5", outStream);
    }

}
