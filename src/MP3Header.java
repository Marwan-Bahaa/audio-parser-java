public class MP3Header implements AudioHeader {
    private boolean hasID3;

    public MP3Header(boolean hasID3) {
        this.hasID3 = hasID3;
    }

}