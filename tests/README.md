# Snakes and Ladders – deterministic tests

From `snake_ladder_design` directory:

1. Compile main sources:
   ```
   javac JumpGenerator.java SnakeJumpGenerator.java LadderJumpGenerator.java Board.java Game.java Player.java Dice.java StandardDice.java Jump.java JumpType.java BoardBuilder.java
   ```

2. Compile tests:
   ```
   javac -cp . tests/FixedDice.java tests/BoardTest.java tests/GameTest.java tests/BoardBuilderTest.java
   ```

3. Run tests:
   ```
   java -cp .:tests BoardTest
   java -cp .:tests GameTest
   java -cp .:tests BoardBuilderTest
   ```

- **BoardTest**: overshoot, snake, ladder, exact win, no jump, single jump.
- **GameTest**: round-robin with fixed dice, winner removed from play.
- **BoardBuilderTest**: deterministic build with seed, lastCell/size and winning cell.
- **FixedDice**: test double returning a fixed sequence for reproducible runs.
