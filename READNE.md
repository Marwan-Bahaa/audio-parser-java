# 🎧 Audio Parser (Java)

A robust and extensible audio parsing library built in Java, supporting multiple formats such as MP3 and WAV.
Designed to handle real-world files with high performance and clean architecture.

---

## 🚀 Features

* ✅ MP3 Parsing (Sync Hunting + ID3v2 Skipping)
* ✅ WAV Header Parsing
* ✅ Multi-format Architecture (Extensible Design)
* ✅ Bitrate & Sample Rate Extraction
* ✅ Memory-efficient using `MappedByteBuffer`
* ✅ Protection against false sync (basic validation)

---

## 🧠 Architecture

The project follows a clean and modular design to support multiple audio formats:

```
audio/
 ├── core/        # File reading & format detection
 ├── format/      # Format abstraction (MP3, WAV)
 ├── parser/      # Parsing logic for each format
 ├── header/      # Header models (AudioHeader, MP3Header, WAVHeader)
 ├── formatter/   # Output formatting
```

---

## 💻 Usage

```java
Path path = Paths.get("audio.mp3");

try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
    MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());

    AudioParser parser = new MP3Parser(); // or WAVParser
    AudioHeader header = parser.parse(buffer);

    System.out.println(header);
}
```

---

## ⚙️ Example Output

```
Format: MPEG 1 Layer III
Bitrate: 192 kbps
Sample Rate: 44100 Hz
```

```
Format: WAV
Sample Rate: 44100 Hz
Channels: 2
Bit Depth: 16-bit
```

---

## 🔍 How It Works

### MP3

1. Detects and skips ID3v2 metadata
2. Performs sync hunting to locate valid frames
3. Extracts header bits (version, layer, bitrate, sample rate)
4. Validates fields to reduce false sync detection

### WAV

1. Reads RIFF header
2. Extracts format chunk
3. Parses sample rate, channels, and bit depth

---

## 🛠️ Future Improvements

* 🔜 Frame Length Validation (eliminate false sync completely)
* 🔜 VBR Support (Xing / Info header parsing)
* 🔜 Automatic format detection (MP3 / WAV)
* 🔜 CLI tool for terminal usage
* 🔜 Support for additional formats (e.g., FLAC)

---

## 🎯 Why This Project?

Most simple parsers assume perfect input files.
This project focuses on **robustness and extensibility**, making it closer to real-world systems and production-grade tools.

---

## 📌 Technologies

* Java
* NIO (`MappedByteBuffer`, `FileChannel`)
* OOP & Clean Architecture Principles

---

## 👨‍💻 Author

**Marwan Bahaa**
Computer Science Student
Interested in Backend Development, Systems, ML , and AI
