import compiler.StateMachine;
import compiler.State;

public class CharStateMachine extends StateMachine {

	@Override
	public void initStateTable() {
		State start = new State("start");
		start.addTransition('\'', "afterK");
		m_stateMap.put("start", start);
		
		State afterK = new State("afterK");
		for ( char c = ' '; c <= '~'; c++) {
			afterK.addTransition(c, "beforeK");
		}
		m_stateMap.put("afterK", afterK);		
		
		State beforeK = new State("beforeK");
		beforeK.addTransition('\'', "end");
		m_stateMap.put("beforeK", beforeK);
		
		State end = new State("end");
		m_stateMap.put("end", end);
		
		
		

	}

	@Override
	public String getStartState() {
		return "start";
	}

	@Override
	public boolean isFinalState() {
		return (m_state.equals("end"));	
		}

}
