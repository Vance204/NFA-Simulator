import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("NFA Simulator (Type 'exit' to stop)");

        Map<Integer, Map<Character, Set<Integer>>> nfa = new HashMap<>();

        int q0 = 0, q1 = 1, q2 = 2;

        nfa.put(q0, new HashMap<>());
        nfa.put(q1, new HashMap<>());
        nfa.put(q2, new HashMap<>());

        nfa.get(q0).put('a', new HashSet<>(Arrays.asList(q0, q1)));
        nfa.get(q0).put('b', new HashSet<>(Collections.singletonList(q0)));
        nfa.get(q1).put('a', new HashSet<>(Collections.singletonList(q1)));
        nfa.get(q1).put('b', new HashSet<>(Collections.singletonList(q2)));
        nfa.get(q2).put('a', new HashSet<>(Collections.singletonList(q2)));
        nfa.get(q2).put('b', new HashSet<>(Collections.singletonList(q2)));

        while (true) {
            System.out.print("Input: ");
            String input = scan.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                break;
            }

            Set<Integer> currentStates = new HashSet<>();
            currentStates.add(q0);

            boolean invalidChar = false;
            for (char ch : input.toCharArray()) {
                if (ch != 'a' && ch != 'b') {
                    invalidChar = true;
                    break;
                }
                Set<Integer> next = new HashSet<>();
                for (int s : currentStates) {
                    Map<Character, Set<Integer>> trans = nfa.get(s);
                    if (trans != null && trans.containsKey(ch)) {
                        next.addAll(trans.get(ch));
                    }
                }
                currentStates = next;
                if (currentStates.isEmpty()) break;
            }

            if (invalidChar) {
                System.out.println("Output: Invalid input (use only 'a' and 'b')\n");
                continue;
            }

            if (currentStates.contains(q2)) {
                System.out.println("Output: Accepted\n");
            } else {
                System.out.println("Output: Rejected\n");
            }
        }

        scan.close();
    }
}
