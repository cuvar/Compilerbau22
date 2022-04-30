package machines;

import compiler.StateMachine;
import compiler.TokenIntf;

public class StateMachineKeywords extends StateMachine {
	private String m_keyword;
	private TokenIntf.Type m_tokenType;
    private String m_endstate;
	
	
	public StateMachineKeywords(String keyword, TokenIntf.Type tokenType) {
	    m_keyword = keyword;
	    m_tokenType = tokenType;
	    initStateTableDelayed();
	}
	
	@Override
	public String getStartState() {
		return "Z0";
	}

	@Override
	public boolean isFinalState() {
		return (m_state.equals(m_endstate));
	}

	@Override
    public void initStateTable() {
	    
	}

	public void initStateTableDelayed() {
		m_endstate = "Z"+ m_keyword.length();
		char [] keywordArray =  m_keyword.toCharArray();
		String condition = "Z" + 0;
		int i = 1;
		for (char c : keywordArray) {
			String condition1 = "Z" + i;
			compiler.State state = new compiler.State(condition);
			state.addTransition(c, condition1);
			m_stateMap.put(condition, state);
			i++;
			condition = condition1; 
		}
		compiler.State end = new compiler.State(condition);
		m_stateMap.put(condition, end);
	}

    public TokenIntf.Type getType() {
        return m_tokenType;
    }

}
