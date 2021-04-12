//Katie Dolan
//Section AA
//This class generates sentences randomly from given grammers using given production rules.
import java.util.*;
import java.util.function.*;

public class LanguageGenerator {
    //map of grammers.
    private Map<String, String[]> grammars;
    //random object that can be inputed by user or not.
    private Random rand;

    //post:Constructs a Language Generator from given grammar without a random object.
    public LanguageGenerator(Grammar grammar) {
        grammars = grammar.productionRules.get();
        rand = new Random();
    }

    //post: Constructs a Language Generator from given grammer with a random object.
    public LanguageGenerator(Grammar grammar, Random random) {
        grammars = grammar.productionRules.get();
        rand = random;
    }
    
    //pre: target can be terminal or non-terminal.
    //post: generates a string sentence for the given target from grammars production rules.
    public String generate(String target) {
        String result = "";   
        String[] terminals = grammars.get(target); 

        if(!grammars.containsKey(target)) {
            return target;
        } else {
            String symbol = terminals[rand.nextInt(terminals.length)];
            String [] terms = symbol.trim().split("\\s+");
            for(int i = 0; i< terms.length; i++) {
                target = terms[i]; 
                result += generate(target).trim() + " ";
            }
            return result.trim();
        }   
    }

    public enum Grammar {
        FORMULA(() -> {
            Map<String, String[]> result = new TreeMap<>();
            result.put("E", "T, E OP T".split(", "));
            result.put("T", "x, y, 1, 2, 3, ( E ), F1 ( E ), - T, F2 ( E . E )".split(", "));
            result.put("OP", "+, -, *, %, /".split(", "));
            result.put("F1", "sin, cos, tan, sqrt, abs".split(", "));
            result.put("F2", "max, min, pow".split(", "));
            return result;
        }),
        MUSIC(() -> {
            Map<String, String[]> result = new TreeMap<>();
            result.put("measure", "pitch-w, half half".split(", "));
            result.put("half", "pitch-h, quarter quarter".split(", "));
            result.put("quarter", "pitch-q, pitch pitch".split(", "));
            result.put("pitch", "C, D#, F, F#, G, A#, C6".split(", "));
            result.put("chordmeasure", "chord-w, halfchord halfchord".split(", "));
            result.put("halfchord", "chord-h, chord-q chord-q".split(", "));
            result.put("chord", "Cmin, Cmin7, Fdom7, Gdom7".split(", "));
            result.put("bassdrum", "O..o, O..., O..o, OO..".split(", "));
            result.put("snare", "..S., S..s, .S.S".split(", "));
            result.put("crash", "...*, *...".split(", "));
            result.put("claps", "x..x, xx..x".split(", "));
            return result;
        }),
        ENGLISH(() -> {
            Map<String, String[]> result = new TreeMap<>();
            result.put("SENTENCE", "NOUNP VERBP".split(", "));
            result.put("NOUNP", "DET ADJS NOUN, PROPNOUN".split(", "));
            result.put("PROPNOUN", "Seattle, Matisse, Kim, Zela, Nia, Remi, Alonzo".split(", "));
            result.put("ADJS", "ADJ, ADJ ADJS".split(", "));
            result.put("ADJ", "fluffy, bright, colorful, beautiful, purple, calming".split(", "));
            result.put("DET", "the, a".split(", "));
            result.put("NOUN", "cat, dog, bagel, apple, person, school, car, train".split(", "));
            result.put("VERBP", "TRANSVERB NOUNP, INTRANSVERB".split(", "));
            result.put("TRANSVERB", "ate, followed, drove, smacked, embraced, helped".split(", "));
            result.put("INTRANSVERB", "shined, smiled, laughed, sneezed, snorted".split(", "));
            return result;
        });

        public final Supplier<Map<String, String[]>> productionRules;

        private Grammar(Supplier<Map<String, String[]>> productionRules) {
            this.productionRules = productionRules;
        }
    }

    public static void main(String[] args) {
        LanguageGenerator generator = new LanguageGenerator(Grammar.FORMULA);
        System.out.println(generator.generate("T"));
    }
}
