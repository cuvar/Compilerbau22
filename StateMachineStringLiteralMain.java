
import machines.StateMachineStringLiteral;

import java.io.OutputStreamWriter;

public class StateMachineStringLiteralMain {

	public static void main(String[] args) throws Exception {
		compiler.StateMachine stringMachine = new StateMachineStringLiteral();
		OutputStreamWriter outStream = new OutputStreamWriter(System.out, "UTF-8");
		stringMachine.process("\"ABB\"", outStream);

        System.out.println();
        System.out.println(stringMachine.asDot());
		System.out.println();
	}

}
