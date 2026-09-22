package abstraction.assigment_problems;

/**
 * PROBLEM 2: Gallery Description Cards
 * There's no such thing as a generic, undefined "art piece" with no
 * actual form, so ArtPiece is abstract and new ArtPiece() cannot compile.
 */
public abstract class ArtPiece {

    private static int pieceIdCounter = 5000;

    private final String pieceId;
    protected final String title;

    public ArtPiece(String title) {
        this.title = title;
        pieceIdCounter++;
        this.pieceId = "ART-" + pieceIdCounter;
    }

    public abstract String describe();

    String getPieceId() {
        return pieceId;
    }

    public static void main(String[] args) {
        Painting p = new Painting("Sunset Fields");
        System.out.println(p.describe()); // Painting: Sunset Fields, framed on canvas

        Sculpture s = new Sculpture("The Thinker II");
        System.out.println(s.describe()); // Sculpture: The Thinker II, carved from stone
    }
}

/**
 * Painting - a concrete ArtPiece with its own description.
 */
class Painting extends ArtPiece {

    public Painting(String title) {
        super(title);
    }

    @Override
    public String describe() {
        return "Painting: " + title + ", framed on canvas";
    }
}

/**
 * Sculpture - a concrete ArtPiece with its own description.
 */
class Sculpture extends ArtPiece {

    public Sculpture(String title) {
        super(title);
    }

    @Override
    public String describe() {
        return "Sculpture: " + title + ", carved from stone";
    }
}