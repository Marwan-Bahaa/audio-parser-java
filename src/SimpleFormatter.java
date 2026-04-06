class SimpleFormatter implements HeaderFormatter {
    public String format(AudioHeader h) {
        return h.toString();
    }
}