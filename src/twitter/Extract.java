package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Extract {

    public static Timespan getTimespan(List<Tweet> tweets) {
        if (tweets.isEmpty()) {
            return new Timespan(Instant.MIN, Instant.MIN);
        }
        
        Instant start = Instant.MAX;
        Instant end = Instant.MIN;
        
        for (Tweet tweet : tweets) {
            Instant timestamp = tweet.getTimestamp();
            if (timestamp.isBefore(start)) start = timestamp;
            if (timestamp.isAfter(end)) end = timestamp;
        }
        
        return new Timespan(start, end);
    }

    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentionedUsers = new HashSet<>();
        
        for (Tweet tweet : tweets) {
            String text = tweet.getText();
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == '@' && isValidMention(text, i)) {
                    String username = getUsernameMention(text, i);
                    mentionedUsers.add(username.toLowerCase());
                }
            }
        }
        return mentionedUsers;
    }

    private static boolean isValidMention(String text, int index) {
        return (index == 0 || !isValidChar(text.charAt(index - 1))) &&
               (index < text.length() - 1 && isValidChar(text.charAt(index + 1)));
    }

    private static boolean isValidChar(char ch) {
        return Character.isLetterOrDigit(ch) || ch == '-' || ch == '_';
    }

    private static String getUsernameMention(String text, int start) {
        StringBuilder builder = new StringBuilder();
        for (int i = start + 1; i < text.length() && isValidChar(text.charAt(i)); i++) {
            builder.append(text.charAt(i));
        }
        return builder.toString();
    }
}
