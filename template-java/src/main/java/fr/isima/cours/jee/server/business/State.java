// package fr.isima.cours.jee.server.business;

public enum State {
    WHITE{
        @Override
        public State opposite() {
            return BLACK;
        }
        @Override
        public State name() {
            return "white";
        }
        @Override
        public String toString(){
            return "W";
        }
    },
    BLACK{
        @Override
        public State opposite() {
            return WHITE;
        }
        @Override
        public State name() {
            return "black";
        }
        @Override
        public String toString(){
            return "B";
        }
    },
    EMPTY{
        @Override
        public State opposite() {
            return null;
        }
        @Override
        public State name() {
            return "empty";
        }
        @Override
        public String toString(){
            return "-";
        }
    },
    OUT{
        @Override
        public State opposite() {
            return null;
        }
        @Override
        public State name() {
            return "out";
        }
        @Override
        public String toString(){
            return "E";
        }
    };

    public abstract State opposite();
    public abstract State name();
}
