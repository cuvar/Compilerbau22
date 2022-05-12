package compiler;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Lexer {

    static class MachineInfo {

        public StateMachineBase m_machine;
        public int m_acceptPos;

        public MachineInfo(StateMachineBase machine) {
            m_machine = machine;
            m_acceptPos = 0;
        }

        public void init(String input) {
            m_acceptPos = 0;
            m_machine.init(input);
        }
    }

    protected Vector<MachineInfo> m_machineList;
    protected String m_input;
    protected Token m_currentToken;
    protected String m_currentLine;
    protected int m_currentLineNumber;

    public Lexer() {
        m_machineList = new Vector<MachineInfo>();
    }

    public void addMachine(StateMachineBase machine) {
        m_machineList.add(new MachineInfo(machine));
    }

    public void init(String input) throws Exception {
        m_input = input;
        m_currentToken = new Token();
        m_currentLine = new String();
        m_currentLineNumber = 1;
        advance();
    }

    public void initMachines(String input) {
        for (MachineInfo machine : m_machineList) {
            machine.init(input);
        }
    }

    public Token nextWord() throws Exception {
        int curPos = 0;
        // initialize machines
        initMachines(m_input);
        // while some machine are in process
        boolean machineActive;
        do {
            machineActive = false;
            // for each machine in process
            for (MachineInfo machine : m_machineList) {
                if (machine.m_machine.isFinished()) {
                    continue;
                }
                machineActive = true;
                // next step
                machine.m_machine.step();
                // if possible final state
                if (machine.m_machine.isFinalState()) {
                    // update last position machine would accept
                    machine.m_acceptPos = curPos + 1;
                }
            } // end for each machine in process
            curPos++;
        } while (machineActive); // end while some machine in process
        // select first machine with largest final pos (greedy)
        MachineInfo bestMatch = new MachineInfo(null);
        for (MachineInfo machine : m_machineList) {
            if (machine.m_acceptPos > bestMatch.m_acceptPos) {
                bestMatch = machine;
            }
        }
        // throw in case of error
        if (bestMatch.m_machine == null) {
            throw new CompilerException("Illegal token", m_currentLineNumber, m_currentLine, null);
        }
        // set next word [start pos, final pos)
        String nextWord = m_input.substring(0, bestMatch.m_acceptPos);
        if (nextWord.indexOf('\n') != -1) {
            m_currentLineNumber++;
            m_currentLine = "";
        } else {
            m_currentLine += nextWord;
        }
        m_input = m_input.substring(bestMatch.m_acceptPos);
        Token token = new Token();
        token.m_type = bestMatch.m_machine.getType();
        token.m_value = nextWord;
        return token;
    }

    public Token nextToken() throws Exception {
        if (m_input.isEmpty()) {
            Token token = new Token();
            token.m_type = Token.Type.EOF;
            token.m_value = new String();
            return token;
        }
        Token token = nextWord();
        while (
            token.m_type == Token.Type.WHITESPACE ||
            token.m_type == Token.Type.MULTILINECOMMENT ||
            token.m_type == Token.Type.LINECOMMENT) {
            token = nextWord();    
        }
        return token;
    }
    
    boolean isWhitespace(char c) {
        if (c == ' ' || c == '\t' || c == '\n') {
            return true;
        } else {
            return false;
        }
    }

    void skipWhiteSpace() {
        int i = 0;
        while (i < m_input.length() && isWhitespace(m_input.charAt(i))) {
            i++;
        }
        m_input = m_input.substring(i);
    }

    public void processInput(String input, OutputStreamWriter outStream) throws Exception {
        m_input = input;
        m_currentLine = "";
        m_currentLineNumber = 1;
        // while input available
        while (!m_input.isEmpty()) {
            // get next word
            Token curWord = nextWord();
            // break on failure
            if (curWord.m_type == Token.Type.EOF) {
                outStream.write("ERROR\n");
                outStream.flush();
                break;
            } else if (curWord.m_type == Token.Type.WHITESPACE) {
                continue;
            } else {
                // print word
                outStream.write(curWord.toString());
                outStream.write("\n");
                outStream.flush();
            }
        }
    }

    public Token lookAhead() {
        return m_currentToken;
    }

    public void advance() throws Exception {
        m_currentToken = nextWord();
    }

    public void expect(Token.Type tokenType) throws Exception {
        if (tokenType == m_currentToken.m_type) {
            advance();
        }
        throw new Exception("Token not expected");

    }

    public boolean accept(Token.Type tokenType) throws Exception {
        if (tokenType == m_currentToken.m_type) {
            advance();
            return true;
        }
        return false;
    }
}
