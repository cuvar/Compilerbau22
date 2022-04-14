import compiler.StateMachine;

public class StateMachineGanzzahl extends StateMachine {

	@Override
	public void initStateTable() {
		compiler.State start = new compiler.State("start");
		compiler.State end = new compiler.State("end");
		start.addTransition('0', "end");
		start.addTransition('-', "negative");
		compiler.State numberOneToNine = new compiler.State("numberOneToNine");
		compiler.State negative = new compiler.State("negative");

		for (int i = 0; i <= 9; i++) {
			numberOneToNine.addTransition((char) (i + '0'), "numberOneToNine");
		}
		for (int i = 1; i <= 9; i++) {
			start.addTransition((char) (i + '0'), "numberOneToNine");
		}
		for (int i = 1; i <= 9; i++) {
			negative.addTransition((char) (i + '0'), "numberOneToNine");
		}

		m_stateMap.put("start", start);

		m_stateMap.put("numberOneToNine", numberOneToNine);
		m_stateMap.put("end", end);
		m_stateMap.put("negative", negative);

	}

	@Override
	public String getStartState() {
		return "start";
	}

	@Override
	public boolean isFinalState() {
		return (m_state.equals("numberOneToNine") || m_state.equals("end"));
	}

}
