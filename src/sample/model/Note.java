package sample.model;

public class Note {
    private int start = 0;
    private int finish = 100;

    public Note(int start, int stop) {
        this.start = start;
        this.finish = stop;
    }

    public Note() {
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }
}
