package machines;

import compiler.*;

public class StateMachineWhitespaces extends StateMachine {

	@Override
	public String getStartState() {
		return "start";
	}

	@Override
	public boolean isFinalState() {
		return (m_state.equals("end"));
	}
	
    public TokenIntf.Type getType() {
        return TokenIntf.Type.WHITESPACE;
    }

	@Override
	public void initStateTable() {
		State start = new State("start");
		State end = new State("end");
		start.addTransition(' ', "end");
		start.addTransition('\n', "end");
		start.addTransition('\r', "end");
		start.addTransition('\t', "end");
		m_stateMap.put("start", start);
		end.addTransition(' ', "end");
		end.addTransition('\n', "end");
		end.addTransition('\r', "end");
		end.addTransition('\t', "end");
		m_stateMap.put("end", end);
	}
	
}
