
import java.io.OutputStreamWriter;

public class StateMachineTableABMain {

	public static void main(String[] args) throws Exception {
		compiler.StateMachine abMachine = new CharStateMachine();
		OutputStreamWriter outStream = new OutputStreamWriter(System.out, "UTF-8");
		abMachine.process("'a'", outStream);

        System.out.println();
        System.out.println(abMachine.asDot());
		System.out.println();
	}

}
