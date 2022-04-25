import java.io.OutputStreamWriter;

public class LexerMain {

    public static void main(String[] args) throws Exception {
        compiler.Lexer lexer = new compiler.Lexer();
        OutputStreamWriter outStream = new OutputStreamWriter(System.out, "UTF-8");
        compiler.StateMachineBase ifMachine = new StateMachineKeywords("if");
        lexer.addMachine(ifMachine);
        compiler.StateMachineBase elseMachine = new StateMachineKeywords("else");
        lexer.addMachine(elseMachine);
        compiler.StateMachineBase identifierMachine = new StateMachineIdentifier();
        lexer.addMachine(identifierMachine);
        compiler.StateMachineBase ganzzahlMachine = new StateMachineGanzzahl();
        lexer.addMachine(ganzzahlMachine);
        compiler.StateMachineBase decimalsMachine = new StateMachineDecimals();
        lexer.addMachine(decimalsMachine);
        lexer.processInput("if abc 5 else def 5.5", outStream);
    }

}
