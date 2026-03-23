# Snake & Ladder

## How to run

```bash
javac *.java
java Main
```

## Tests

```bash
javac -cp .:tests tests/*.java
java -cp .:tests BoardTest
java -cp .:tests GameTest
java -cp .:tests EasyModeStrategyTest
java -cp .:tests HardModeStrategyTest
```

## Class Diagram

```mermaid
classDiagram
    direction LR

    class Game {
        -Board board
        -Dice dice
        -MakeMoveStrategy strategy
        -Queue~Player~ activePlayers
        -List~Player~ winners
        +play()
        +makeTurn(Player) MoveOutcome
    }

    class Board {
        -int size
        -int lastCell
        -Map~int, int~ jumps
        +resolveMove(from, roll) int
        +applyJump(cell) int
        +isWinningCell(cell) boolean
    }

    class Player {
        -String id
        -int position
        +moveTo(cell)
    }

    class Dice {
        <<interface>>
        +roll() int
    }

    class StandardDice {
        -int faces
        +roll() int
    }

    class MakeMoveStrategy {
        <<interface>>
        +makeMove(Player, Board, Dice) MoveOutcome
    }

    class EasyModeStrategy {
        +makeMove() MoveOutcome
    }

    class HardModeStrategy {
        -Map consecutiveCount
        +makeMove() MoveOutcome
    }

    class StandardModeStrategy {
        +makeMove() MoveOutcome
    }

    class MoveOutcome {
        <<enum>>
        NORMAL
        WON
        ELIMINATED
    }

    class JumpGenerator {
        <<interface>>
        +placeOne(lastCell, usedStarts, jumps, random) boolean
    }

    class RandomJumpGenerator {
        -boolean goesDown
        +snake()$ RandomJumpGenerator
        +ladder()$ RandomJumpGenerator
    }

    class BoardBuilder {
        +randomBoard(n, random)$ Board
    }

    Game --> Board
    Game --> Dice
    Game --> MakeMoveStrategy
    Game --> Player
    Game ..> MoveOutcome

    StandardDice ..|> Dice
    EasyModeStrategy ..|> MakeMoveStrategy
    HardModeStrategy ..|> MakeMoveStrategy
    StandardModeStrategy ..|> MakeMoveStrategy
    MakeMoveStrategy ..> MoveOutcome

    RandomJumpGenerator ..|> JumpGenerator
    BoardBuilder ..> RandomJumpGenerator
    BoardBuilder ..> Board
```

## Game Loop

```
1. Poll next player from queue
2. strategy.makeMove(player, board, dice)
   - rolls dice
   - applies mode rules (overshoot, elimination)
   - applies snakes/ladders via board.applyJump()
   - returns NORMAL / WON / ELIMINATED
3. WON        -> added to winners
   ELIMINATED -> removed from game
   NORMAL     -> back in queue
4. Repeat while >= 2 players remain
```
