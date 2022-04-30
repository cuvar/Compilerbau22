import machines.StateMachineKeywords;

import java.io.OutputStreamWriter;

import compiler.TokenIntf;

public class StateMachineKeywordsMain {
	public static void main(String[] args) throws Exception {
		compiler.StateMachine keysMachine = new StateMachineKeywords("else", TokenIntf.Type.ELSE);
		OutputStreamWriter outStream = new OutputStreamWriter(System.out, "UTF-8");
		keysMachine.process("else", outStream);
		
        System.out.println();
        System.out.println(keysMachine.asDot());
		System.out.println();
	}
}
