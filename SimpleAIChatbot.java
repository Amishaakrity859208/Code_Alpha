import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class SimpleAIChatbot {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! I am ChatBot-9000. (Type 'bye' to exit)");

        while (true) {
            System.out.print("You: ");
            String input = scanner.nextLine().toLowerCase().trim();

            if (input.equals("bye") || input.equals("exit")) {
                System.out.println("Bot: Goodbye! Have a great day.");
                break;
            }

            String response = getResponse(input);
            System.out.println("Bot: " + response);
        }
        scanner.close();
    }

    // This method decides what the AI says
    public static String getResponse(String input) {
        
        // 1. Greetings
        if (input.contains("hello") || input.contains("hi") || input.contains("hey")) {
            return "Hello there! How can I help you today?";
        }
        
        // 2. Identity
        if (input.contains("your name") || input.contains("who are you")) {
            return "I am ChatBot-9000, a simple AI written in Java.";
        }

        // 3. Emotional State
        if (input.contains("how are you")) {
            return "I'm just a computer program, so I don't have feelings, but my code is running perfectly!";
        }

        // 4. Time Check
        if (input.contains("time")) {
            LocalTime now = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
            return "The current time is " + now.format(formatter);
        }

        // 5. Tech Questions
        if (input.contains("java")) {
            return "Java is a robust, object-oriented programming language. I was written in it!";
        }

        // 6. Joke capability
        if (input.contains("joke")) {
            return "Why do Java programmers wear glasses? Because they don't C# (See Sharp)!";
        }

        // 7. Unknown inputs (Random generic responses)
        return getRandomGenericResponse();
    }

    private static String getRandomGenericResponse() {
        String[] responses = {
            "That's interesting! Tell me more.",
            "I'm not sure I understand. Can you rephrase that?",
            "Could you clarify what you mean?",
            "I am still learning. That is a bit too complex for me right now."
        };
        Random random = new Random();
        return responses[random.nextInt(responses.length)];
    }
}