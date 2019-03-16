package fr.isima.cours.jee.server.business;

public enum State {
    WHITE{
        @Override
        public State opposite() {
            return BLACK;
        }
        @Override
        public String getName() {
            return "white";
        }
        @Override
        public String toString(){
            return "W";
        }
        public String getImagePath(){
            return "/images/white.png";
        }
    },
    BLACK{
        @Override
        public State opposite() {
            return WHITE;
        }
        @Override
        public String getName() {
            return "black";
        }
        @Override
        public String toString(){
            return "B";
        }
        public String getImagePath(){
            return "/images/black.png";
        }
    },
    EMPTY{
        @Override
        public State opposite() {
            return null;
        }
        @Override
        public String getName() {
            return "empty";
        }
        @Override
        public String toString(){
            return "-";
        }
        public String getImagePath(){
            return "/images/empty.png";
        }
    },
    OUT{
        @Override
        public State opposite() {
            return null;
        }
        @Override
        public String getName() {
            return "out";
        }
        @Override
        public String toString(){
            return "E";
        }
        public String getImagePath(){
            return null;
        }
    };

    public abstract State opposite();
    public abstract String getName();
    public abstract String getImagePath();
}
