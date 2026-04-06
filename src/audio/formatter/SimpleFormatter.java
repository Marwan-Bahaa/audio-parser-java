package audio.formatter;

import audio.header.AudioHeader;

public class SimpleFormatter implements HeaderFormatter {
    public String format(AudioHeader h) {
        return h.toString();
    }
}