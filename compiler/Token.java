package compiler;

public class Token extends TokenIntf {

	@Override
	public String toString() {
		String s = type2String(m_type);
    	s += ' ';
		s += m_value;
		return s;
	}

	static String type2String(Type type) {
		if (type == Type.EOF) {
			return "EOF";
        } else if (type == Type.IF) {
            return "IF";
        } else if (type == Type.ELSE) {
            return "ELSE";
		} else if (type == Type.IDENT) {
			return "IDENT";
		} else if (type == Type.INTEGER) {
			return "INTEGER";
		} else if (type == Type.DECIMAL) {
			return "DECIMAL";
		} else if (type == Type.CHAR) {
			return "CHAR";
		} else if (type == Type.STRING) {
			return "STRING";
		} else if (type == Type.LINECOMMENT) {
			return "LINECOMMENT";
		} else if (type == Type.MULTILINECOMMENT) {
			return "MULTILINECOMMENT";
        } else if (type == Type.WHITESPACE) {
            return "WHITESPACE";
		} else {
			return null;
		}
	}

}

