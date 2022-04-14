import compiler.State;
/**
 * sample state machine
 * accept Line Comment
 */

public class StateMachineLineComment extends compiler.StateMachine {
	
	@Override
	public String getStartState() {
		return "start";
	}

	@Override
	public boolean isFinalState() {
		return (m_state.equals("end"));
	}

	@Override
	public void initStateTable() {
		compiler.State start = new compiler.State("start");
		start.addTransition('/', "afterSlash");
		m_stateMap.put("start", start);

		compiler.State afterSlash = new compiler.State("afterSlash");
		afterSlash.addTransition('/', "inComment");
		m_stateMap.put("afterSlash", afterSlash);

		compiler.State inComment = new compiler.State("inComment");
		inComment.addTransition(' ', "inComment");
		addTransitionForLetter(inComment, "inComment");
		inComment.addTransition('/', "inComment");
		inComment.addTransition('\\', "inCommentAfterBackslash");
		m_stateMap.put("inComment", inComment);

		compiler.State inCommentAfterBackslash = new compiler.State("inCommentAfterBackslash");
		inCommentAfterBackslash.addTransition('\\', "inCommentAfterBackslash");
		inCommentAfterBackslash.addTransition(' ', "inComment");
		addTransitionForLetter(inCommentAfterBackslash, "inComment");
		inCommentAfterBackslash.addTransition('n', "end");
		m_stateMap.put("inCommentAfterBackslash", inCommentAfterBackslash);

		compiler.State end = new compiler.State("end");
		m_stateMap.put("end", end);
	}

	public String getName() {
	        return "LineComment";
	    }

}
