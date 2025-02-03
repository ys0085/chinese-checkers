package com.tp;

import java.util.List;
import java.util.ArrayList;

// Class defining a bot player that extends a Player class.
public class BotPlayer extends Player {
    private Color color; // Color of the bot player

    // Constructor initializing the bot player with a specified color
    public BotPlayer(Color color) {
        super("Bot", color); // Calls the constructor of the Player class with the name "Bot"
        this.color = color; // Assigns the color to the bot
    }

    // Method to retrieve a random valid move for the bot
    private Move getRandomMove() {
        Board board = Server.getInstance().getSession().getBoard(); // Get the current game board
        Variant variant = Server.getInstance().getSession().getVariant(); // Get the game variant
        // Retrieve target positions based on the bot's color
        board.getTargetPositions(board.getTargetColor(color), variant);
        // Get the positions of the bot's pawns on the board
        List<Position> myPawnPositions = board.getPlayerPawnPositions(color);
        System.out.println("Bot's pawns: " + myPawnPositions); // Log the pawn positions

        // Shuffle the list of pawn positions to add randomness
        for (int i = 0; i < myPawnPositions.size(); i++) {
            int randomIndex = (int) (Math.random() * myPawnPositions.size());
            Position temp = myPawnPositions.get(i);
            myPawnPositions.set(i, myPawnPositions.get(randomIndex));
            myPawnPositions.set(randomIndex, temp);
        }
        System.out.println("Shuffled pawns: " + myPawnPositions); // Log the shuffled pawn positions

        // Attempt to find a random valid move from the shuffled pawn positions
        for (Position pawnPosition : myPawnPositions) {
            List<Position> possibleMoves = board.getValidMoveTargets(pawnPosition); // Get valid move targets for the
                                                                                    // pawn
            if (possibleMoves.size() > 0) { // Check if there are possible moves
                Position randomMove = possibleMoves.get((int) (Math.random() * possibleMoves.size())); // Select a
                                                                                                       // random move
                System.out.println("Bot moved from " + pawnPosition + " to " + randomMove); // Log the move
                return new Move(pawnPosition, randomMove); // Return the move
            }
        }
        throw new IllegalStateException("No valid moves"); // Exception if no valid moves are found
    }

    // Method for determining the best move for the bot
    private Move getMove() {
        Board board = Server.getInstance().getSession().getBoard(); // Get the current game board
        Variant variant = Server.getInstance().getSession().getVariant(); // Get the game variant

        // Get target positions for the bot and identify empty positions among them
        List<Position> targetPositions = board.getTargetPositions(board.getTargetColor(color), variant);
        List<Position> emptyTargetPositions = new ArrayList<>();

        // Loop through target positions to find empty ones
        for (Position target : targetPositions) {
            if (board.getTile(target.x, target.y) == Tile.EMPTY) {
                emptyTargetPositions.add(target); // Add to the list if it is empty
            }
        }

        List<Position> myPawnPositions = board.getPlayerPawnPositions(color); // Get the bot's pawn positions

        // Shuffle the list of pawn positions for randomness in decision making
        for (int i = 0; i < myPawnPositions.size(); i++) {
            int randomIndex = (int) (Math.random() * myPawnPositions.size());
            Position temp = myPawnPositions.get(i);
            myPawnPositions.set(i, myPawnPositions.get(randomIndex));
            myPawnPositions.set(randomIndex, temp);
        }

        System.out.println("Bot's pawns: " + myPawnPositions); // Log the current pawn positions

        // If there are no empty target positions, fallback to a random move
        if (emptyTargetPositions.isEmpty())
            return getRandomMove();

        Position bestMoveFrom = null; // To keep track of the best starting position
        Position bestMoveTo = null; // To keep track of the best target position
        int bestDelta = Integer.MAX_VALUE; // Initialize best delta for evaluating moves

        // Evaluate all possible moves to find the most strategic one
        for (Position pawnPosition : myPawnPositions) {
            List<Position> possibleMoveTo = board.getValidMoveTargets(pawnPosition); // Get valid moves from the pawn
                                                                                     // position
            // Track the minimum distance from pawn to an empty target
            int fromScore = Integer.MAX_VALUE;
            for (Position emptyTarget : emptyTargetPositions) {
                int distance = board.getDistance(pawnPosition, emptyTarget); // Calculate the distance to an empty
                                                                             // target
                fromScore = Math.min(fromScore, distance); // Update fromScore if a shorter distance is found
            }

            for (Position moveTo : possibleMoveTo) {
                // Track the minimum distance from target position to any empty target
                int toScore = Integer.MAX_VALUE;
                for (Position emptyTarget : emptyTargetPositions) {
                    int distance = board.getDistance(moveTo, emptyTarget); // Calculate distance to empty target from
                                                                           // moveTo
                    toScore = Math.min(toScore, distance); // Update toScore if a shorter distance is found
                }

                int delta = toScore - fromScore; // Calculate the change in score

                // Update best moves if a better option is found
                if (delta < bestDelta) {
                    bestMoveFrom = pawnPosition; // Update best move from position
                    bestMoveTo = moveTo; // Update best move to position
                    bestDelta = delta; // Update the best delta
                }
            }
        }

        // If a valid move is determined, execute it
        if (bestMoveFrom != null && bestMoveTo != null) {
            System.out.println("Bot moved from " + bestMoveFrom + " to " + bestMoveTo); // Log the best move
            return new Move(bestMoveFrom, bestMoveTo); // Return the best move
        }

        // If no moves are found, resort to random movement
        return getRandomMove();
    }

    // Notify the bot when it's its turn to play
    @Override
    public void notifyTurn() {
        System.out.println("Bot's turn"); // Indicate that it's the bot's turn
        Move move = this.getMove(); // Get the bot's next move
        Server.getInstance().getSession().makeMove(this, move); // Execute the move in the game session
    }

    // Notify of changes in the player list, not utilized by bot
    @Override
    public void notifyPlayerListChange(List<Player> players) {
        // Intentionally left empty; the bot does not need to handle player list changes
    }

    // Notify of changes in the game board, not utilized by bot
    @Override
    public void notifyBoardChange(Board board) {
        // Intentionally left empty; the bot does not need to handle board changes
    }

    // Notify the end of the game, not utilized by bot
    @Override
    public void notifyGameEnd(Color winner) {
        // Intentionally left empty; the bot does not need to handle game termination
    }
}