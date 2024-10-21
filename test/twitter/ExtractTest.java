/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
//    @Test
//    public void testGetTimespanNoTweets() {
//        Timespan timespan = Extract.getTimespan(Arrays.asList());
//        
//        // Handle the case for no tweets; depends on how Timespan is implemented.
//        assertNull("expected null for no tweets", timespan);
//    }

    @Test
    public void testGetTimespanSingleTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }

    @Test
    public void testGetTimespanMultipleTweets() {
        Instant d3 = Instant.parse("2016-02-17T12:00:00Z");
        Tweet tweet3 = new Tweet(3, "user3", "Tweeting at noon", d3);
        
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d3, timespan.getEnd());
    }

    @Test
    public void testGetMentionedUsersSingleMention() {
        Tweet tweetWithMention = new Tweet(3, "user1", "Hello @user2, how are you?", d1);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetWithMention));
        
        assertEquals("expected one mentioned user", Set.of("user2"), mentionedUsers);
    }

    @Test
    public void testGetMentionedUsersMultipleMentions() {
        Tweet tweetWithMultipleMentions = new Tweet(4, "user1", "Shoutout to @user2 and @user3!", d1);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetWithMultipleMentions));
        
        assertEquals("expected two mentioned users", Set.of("user2", "user3"), mentionedUsers);
    }
    @Test
    public void testGetMentionedUsersCaseInsensitivity() {
        Tweet tweetWithMention = new Tweet(6, "user1", "Hey @User2, did you see that?", d1);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetWithMention));
        
        assertEquals("expected case insensitive mention", Set.of("user2"), mentionedUsers);
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
