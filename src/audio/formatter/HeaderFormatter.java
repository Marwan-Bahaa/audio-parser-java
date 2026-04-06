package audio.formatter;

import audio.header.AudioHeader;

interface HeaderFormatter {
     String format(AudioHeader header);
}