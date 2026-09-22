package abstraction.class_problems;

/**
 * PROBLEM 3: Orchestra Warm-Up Routine
 * Instrument is abstract with an abstract play() method - the top of a
 * genuine 3-level hierarchy: Instrument -> StringInstrument -> Violin.
 */
public abstract class Instrument {

    public abstract String play();

    public static void main(String[] args) {
        StringInstrument s = new StringInstrument();
        System.out.println(s.play()); // Strumming the strings

        Violin v = new Violin();
        System.out.println(v.play()); // Strumming the strings, with a bow drawn across four strings
    }
}

/**
 * StringInstrument overrides play() with its own concrete version. Since
 * Instrument.play() is abstract (no body), there is nothing for
 * super.play() to call at this level - StringInstrument provides the
 * first real implementation, which Violin then builds on.
 */
class StringInstrument extends Instrument {

    @Override
    public String play() {
        return "Strumming the strings";
    }
}

/**
 * Violin extends StringInstrument (a genuine multilevel hierarchy 3
 * classes deep). Calls super.play() first, then adds its own detail.
 */
class Violin extends StringInstrument {

    @Override
    public String play() {
        return super.play() + ", with a bow drawn across four strings";
    }
}