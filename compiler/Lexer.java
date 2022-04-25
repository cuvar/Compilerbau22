package compiler;

import java.util.Vector;

import compiler._Lexer.MachineInfo;

public class Lexer {

    Array<StateMachine> m_machineList;

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

    static class Token {
        public String m_word;
        public String m_kind;
        
        public Token(String word, String kind) {
            m_word = word;
            m_kind = kind;
        }
    }
    
    public void addMachine(MachineBase machine)
    
    public void process() {
    // while input available    
        // while any machine is accepting
            // send next input character to all accepting machines
        // look for first longest match
        // consume match from input and create token
    }
    
}

