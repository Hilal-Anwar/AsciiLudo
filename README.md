# 🎲 ASCII Ludo

<div align="center">

```
  ______                       __  __        __                       __          
 /      \                     |  \|  \      |  \                     |  \         
|  $$$$$$\  _______   _______  \$$ \$$      | $$      __    __   ____| $$  ______ 
| $$__| $$ /       \ /       \|  \|  \      | $$     |  \  |  \ /      $$ /      \
| $$    $$|  $$$$$$$|  $$$$$$$| $$| $$      | $$     | $$  | $$|  $$$$$$$|  $$$$$$\
| $$$$$$$$ \$$    \ | $$      | $$| $$      | $$     | $$  | $$| $$  | $$| $$  | $$
| $$  | $$ _\$$$$$$\| $$_____ | $$| $$      | $$_____| $$__/ $$| $$__| $$| $$__/ $$
| $$  | $$|       $$ \$$     \| $$| $$      | $$     \\$$    $$ \$$    $$ \$$    $$
 \$$   \$$ \$$$$$$$   \$$$$$$$ \$$ \$$       \$$$$$$$$ \$$$$$$   \$$$$$$$  \$$$$$$ 
```

**A terminal-based implementation of the classic board game Ludo with beautiful ASCII graphics**

[![Java](https://img.shields.io/badge/Java-22-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![JLine](https://img.shields.io/badge/JLine-3.25.0-blue?style=for-the-badge)](https://jline.github.io/)

</div>

## 🌟 Features

- 🎨 **Beautiful ASCII Art Interface** - Stunning terminal-based graphics
- 🎮 **Classic Ludo Gameplay** - Traditional 4-player Ludo rules
- ⌨️ **Keyboard Controls** - Intuitive arrow key navigation
- 🎲 **Animated Dice Rolling** - Visual dice with random outcomes
- 🏠 **Player Home Bases** - Color-coded starting positions
- 🛤️ **Path Visualization** - Clear game board with special squares
- 🎯 **Token Movement** - Smooth token animations and interactions
- 🏆 **Turn-based System** - Fair gameplay rotation
- 💥 **Token Capturing** - Send opponents back to base
- 🎊 **Loading Animation** - Stylish game startup sequence

## 🚀 Quick Start

### Prerequisites

- **Java 22** or higher
- **Maven 3.6+**

### Installation & Running

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd AsciiLudo
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Run the game**
   ```bash
   mvn exec:java -Dexec.mainClass="org.ui.Main"
   ```

   **OR** build a JAR file:
   ```bash
   mvn assembly:single
   java -jar target/AsciiLudo-1.0-SNAPSHOT-jar-with-dependencies.jar
   ```

## 🎮 How to Play

### Controls
- **Arrow Keys** (↑↓←→) - Navigate between your tokens
- **Enter** - Roll dice / Select token to move
- **ESC** - Exit game

### Game Rules
1. **Starting** - Roll a 6 to get your first token out of the home base
2. **Moving** - Use arrow keys to select which token to move
3. **Capturing** - Land on opponent tokens to send them home
4. **Special Squares** - Safe zones where tokens can't be captured
5. **Winning** - Get all four tokens to the center home area first

### Game Flow
1. Players take turns rolling the dice
2. If you roll a 6, you get another turn
3. Move tokens around the board clockwise
4. Enter the home column when completing a full lap
5. First player to get all tokens home wins!

## 🏗️ Project Structure

```
src/
├── main/java/org/ui/
│   ├── Main.java                 # Entry point with ASCII art loading
│   ├── controller/
│   │   ├── Game.java            # Main game logic and state management
│   │   ├── GameStatus.java      # Game state enumeration
│   │   ├── LudoBoard.java       # Board rendering and display
│   │   └── LudoBox.java         # Individual board cell representation
│   ├── core/
│   │   ├── Box.java             # Basic geometric box primitive
│   │   ├── Dice.java            # Dice rolling mechanics
│   │   ├── PathType.java        # Path classification enumeration
│   │   ├── Point.java           # 2D coordinate system
│   │   ├── Token.java           # Player token with position tracking
│   │   └── TokensType.java      # Player color enumeration
│   └── util/
│       ├── Colors.java          # Terminal color management
│       ├── Cursor.java          # Cursor position and rendering
│       ├── Display.java         # Terminal display utilities
│       ├── KeyBoardInput.java   # Input handling system
│       ├── Key.java             # Keyboard key enumeration
│       └── Text.java            # Text formatting and coloring
```

## 🛠️ Technical Details

### Dependencies
- **JLine 3.25.0** - Advanced terminal input/output handling
- **JNA 5.12.1** - Native system integration
- **Jansi 2.4.0** - ANSI escape sequence support for colors

### Architecture Highlights
- **MVC Pattern** - Clean separation of concerns
- **Event-driven Input** - Non-blocking keyboard input handling
- **State Management** - Comprehensive game state tracking
- **Modular Design** - Reusable components and utilities

## 🎨 Screenshots

```
Game Board Layout:
┌───────────────────────────────────────────────┐
│  🟥🟥🟥🟥🟥🟥    🟦🟦    🟩🟩🟩🟩🟩🟩  │
│  🟥  r1  r2  🟥    🟦🟦    🟩  g1  g2  🟩  │
│  🟥          🟥    🟦🟦    🟩          🟩  │
│  🟥  r3  r4  🟥    🟦🟦    🟩  g3  g4  🟩  │
│  🟥🟥🟥🟥🟥🟥    🟦🟦    🟩🟩🟩🟩🟩🟩  │
│                     🟦🟦                     │
│  ██████████████     🎲🎲    ██████████████   │
│                     🎲🎲                     │
│  🟨🟨🟨🟨🟨🟨    🟦🟦    🟦🟦🟦🟦🟦🟦  │
│  🟨  y1  y2  🟨    🟦🟦    🟦  b1  b2  🟦  │
│  🟨          🟨    🟦🟦    🟦          🟦  │
│  🟨  y3  y4  🟨    🟦🟦    🟦  b3  b4  🟦  │
│  🟨🟨🟨🟨🟨🟨    🟦🟦    🟦🟦🟦🟦🟦🟦  │
└───────────────────────────────────────────────┘
```
![ludo](https://github.com/user-attachments/assets/8087c09b-dd63-44e0-aa3e-13f3da155cec)


## 🐛 Troubleshooting

### Common Issues

**Game doesn't start:**
- Ensure Java 22+ is installed: `java --version`
- Check Maven installation: `mvn --version`

**Display issues:**
- Use a terminal that supports ANSI colors
- Ensure terminal window is large enough (minimum 80x24)

**Input not responsive:**
- Make sure terminal is focused
- Try different terminal applications if issues persist

## 🤝 Contributing

We welcome contributions! Here's how you can help:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

### Development Setup
```bash
# Clone your fork
git clone https://github.com/your-username/AsciiLudo.git
cd AsciiLudo

# Build and test
mvn clean compile
mvn test

# Run in development mode
mvn exec:java -Dexec.mainClass="org.ui.Main"
```

## 📝 Future Enhancements

- [ ] 🤖 AI opponents with different difficulty levels
- [ ] 🌐 Network multiplayer support
- [ ] 💾 Save/load game functionality
- [ ] 📊 Game statistics and leaderboards
- [ ] 🎵 Sound effects and background music
- [ ] 🎨 Customizable themes and color schemes
- [ ] 🏅 Achievement system
- [ ] 📱 Mobile-friendly version

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Inspired by the classic board game Ludo
- Built with love for terminal gaming enthusiasts
- Thanks to the JLine community for excellent terminal handling libraries

---

<div align="center">

**Enjoy playing ASCII Ludo! 🎲🎮**

*Made with ❤️ for terminal gaming*

</div>
