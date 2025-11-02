package dev.siebrenvde.ntfy.message;

import dev.siebrenvde.ntfy.message.action.Action;
import dev.siebrenvde.ntfy.message.attachment.Attachment;
import org.junit.jupiter.api.Test;

import java.util.List;

import static dev.siebrenvde.ntfy.message.Message.message;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MessageTest {

    private static final String TITLE = "title";
    private static final String BODY = "body";
    private static final Priority PRIORITY = Priority.MAX;

    private static final String TAG_ONE = "one";
    private static final String TAG_TWO = "two";
    private static final List<String> TAG_LIST_ONE = List.of(TAG_ONE);
    private static final List<String> TAG_LIST_TWO = List.of(TAG_ONE, TAG_TWO);

    private static final boolean MARKDOWN = true;

    private static final Action VIEW_ACTION = Action.view("view", "view");
    private static final Action BROADCAST_ACTION = Action.broadcast("broadcast").build();
    private static final List<Action> ACTION_LIST_ONE = List.of(VIEW_ACTION);
    private static final List<Action> ACTION_LIST_TWO = List.of(VIEW_ACTION, BROADCAST_ACTION);

    private static final String CLICK_ACTION = "click";
    private static final Attachment ATTACHMENT = Attachment.url("attachment");
    private static final String ICON = "icon";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final boolean CACHE = false;
    private static final boolean FIREBASE = false;

    private static final Message MESSAGE = new MessageImpl(
        BODY,
        TITLE,
        PRIORITY,
        TAG_LIST_ONE,
        MARKDOWN,
        ACTION_LIST_ONE,
        CLICK_ACTION,
        ATTACHMENT,
        ICON,
        EMAIL,
        PHONE,
        CACHE,
        FIREBASE
    );

    @Test
    void testBodyOnlyConstructor() {
        assertEquals(
            new MessageImpl(
                BODY,
                null,
                Priority.DEFAULT,
                List.of(),
                false,
                List.of(),
                null,
                null,
                null,
                null,
                null,
                true,
                true
            ),
            message(BODY)
        );
    }

    @Test
    void testBodyAndTitleConstructor() {
        assertEquals(
            new MessageImpl(
                BODY,
                TITLE,
                Priority.DEFAULT,
                List.of(),
                false,
                List.of(),
                null,
                null,
                null,
                null,
                null,
                true,
                true
            ),
            message(BODY, TITLE)
        );
    }

    @Test
    void testBuilder() {
        assertEquals(
            MESSAGE,
            message()
                .body(BODY)
                .title(TITLE)
                .priority(PRIORITY)
                .tags(TAG_LIST_ONE)
                .markdown(MARKDOWN)
                .actions(ACTION_LIST_ONE)
                .clickAction(CLICK_ACTION)
                .attachment(ATTACHMENT)
                .icon(ICON)
                .email(EMAIL)
                .phone(PHONE)
                .cache(CACHE)
                .firebase(FIREBASE)
                .build()
        );
    }

    @Test
    void testBuilderTags() {
        final Message one = message().tags(TAG_LIST_ONE).build();
        assertEquals(one, message().tags(TAG_ONE).build());
        assertEquals(one, message().addTag(TAG_ONE).build());

        final Message two = message().tags(TAG_LIST_TWO).build();
        assertEquals(two, message().tags(TAG_ONE, TAG_TWO).build());
        assertEquals(two, message().tags(TAG_ONE).addTag(TAG_TWO).build());
        assertEquals(two, message().addTag(TAG_ONE).addTag(TAG_TWO).build());
        assertNotEquals(two, message().tags(TAG_ONE).tags(TAG_TWO).build());
    }

    @Test
    void testBuilderActions() {
        final Message one = message().actions(ACTION_LIST_ONE).build();
        assertEquals(one, message().actions(VIEW_ACTION).build());
        assertEquals(one, message().addAction(VIEW_ACTION).build());

        final Message two = message().actions(ACTION_LIST_TWO).build();
        assertEquals(two, message().actions(VIEW_ACTION, BROADCAST_ACTION).build());
        assertEquals(two, message().actions(VIEW_ACTION).addAction(BROADCAST_ACTION).build());
        assertEquals(two, message().addAction(VIEW_ACTION).addAction(BROADCAST_ACTION).build());
        assertNotEquals(two, message().actions(VIEW_ACTION).actions(BROADCAST_ACTION).build());
    }

    @Test
    void testToBuilder() {
        assertEquals(MESSAGE, MESSAGE.toBuilder().build());
    }

}
