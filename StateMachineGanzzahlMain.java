import java.io.OutputStreamWriter;

public class StateMachineGanzzahlMain {
	public static void main(String[] args) throws Exception {

		compiler.StateMachine commentMachine = new StateMachineGanzzahl();
		OutputStreamWriter outStream = new OutputStreamWriter(System.out, "UTF-8");
		commentMachine.process("123456789", outStream);

//		System.out.println();
//		System.out.println(commentMachine.asDot());
//		System.out.println();
	}
}
