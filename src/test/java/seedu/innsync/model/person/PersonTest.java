package seedu.innsync.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.innsync.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.innsync.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.innsync.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.innsync.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.innsync.logic.commands.CommandTestUtil.VALID_REQUEST_AMY;
import static seedu.innsync.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.innsync.testutil.Assert.assertThrows;
import static seedu.innsync.testutil.TypicalPersons.ALICE;
import static seedu.innsync.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.innsync.model.request.Request;
import seedu.innsync.model.request.exceptions.DuplicateRequestException;
import seedu.innsync.model.request.exceptions.RequestNotFoundException;
import seedu.innsync.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", memo=" + ALICE.getMemo()
                + ", requests=" + ALICE.getRequests()
                + ", bookingTags=" + ALICE.getBookingTags() + ", tags=" + ALICE.getTags()
                + ", starred=" + ALICE.getStarred() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void testClearRequests() {
        Person aliceCopy = new PersonBuilder(ALICE).build();
        aliceCopy.clearRequests();
        assertTrue(aliceCopy.getRequests().isEmpty());
    }

    @Test
    public void testAddRequest() throws DuplicateRequestException {
        Request newRequest = new Request(VALID_REQUEST_AMY);
        Person aliceCopy = new PersonBuilder(ALICE).build();
        aliceCopy.clearRequests();
        aliceCopy.addRequest(newRequest);
        assertTrue(aliceCopy.getRequests().contains(newRequest));
    }

    @Test
    public void testAddDuplicateRequestThrowsException() {
        Person aliceCopy = new PersonBuilder(ALICE).build();
        aliceCopy.clearRequests();
        Request duplicateRequest = new Request(VALID_REQUEST_AMY);
        aliceCopy.addRequest(new Request(VALID_REQUEST_AMY));
        assertThrows(DuplicateRequestException.class, () -> aliceCopy.addRequest(duplicateRequest));
    }


    @Test
    public void testRemoveRequest() {
        Request request = new Request(VALID_REQUEST_AMY);
        Person aliceCopy = new PersonBuilder(ALICE).build();
        aliceCopy.clearRequests();
        aliceCopy.addRequest(request);
        aliceCopy.removeRequest(request);
        assertFalse(aliceCopy.getRequests().contains(request));
    }

    @Test
    public void testRemoveRequestThrowsExceptionIfNotFound() {
        Person aliceCopy = new PersonBuilder(ALICE).build();
        aliceCopy.clearRequests();
        Request nonExistentRequest = new Request(VALID_REQUEST_AMY);
        assertThrows(RequestNotFoundException.class, () -> aliceCopy.removeRequest(nonExistentRequest));
    }

    @Test
    public void hashCodeTest() {
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertEquals(ALICE.hashCode(), aliceCopy.hashCode());
    }
}
