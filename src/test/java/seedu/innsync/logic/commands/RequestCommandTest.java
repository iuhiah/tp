package seedu.innsync.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.innsync.logic.commands.CommandTestUtil.VALID_REQUEST_AMY;
import static seedu.innsync.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.innsync.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.innsync.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.innsync.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.innsync.commons.core.index.Index;
import seedu.innsync.logic.Messages;
import seedu.innsync.logic.commands.exceptions.CommandException;
import seedu.innsync.model.AddressBook;
import seedu.innsync.model.Model;
import seedu.innsync.model.ModelManager;
import seedu.innsync.model.UserPrefs;
import seedu.innsync.model.person.Person;
import seedu.innsync.model.request.Request;
import seedu.innsync.testutil.PersonBuilder;

public class RequestCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() throws CommandException {
        Index indexFirstPerson = Index.fromOneBased(INDEX_FIRST_PERSON.getOneBased());
        Person firstPerson = model.getPersonList().get(indexFirstPerson.getZeroBased());

        Set<Request> validRequests = Set.of(new Request(VALID_REQUEST_AMY));
        Person editedPerson = new PersonBuilder(firstPerson)
                .withRequests(VALID_REQUEST_AMY)
                .build();

        RequestCommand requestCommand = new RequestCommand(indexFirstPerson, validRequests);

        String expectedMessage = String.format(RequestCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(requestCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getPersonList().size() + 1);
        RequestCommand requestCommand = new RequestCommand(outOfBoundIndex, Set.of(new Request(VALID_REQUEST_AMY)));
        assertCommandFailure(requestCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Set<Request> firstRequestSet = new HashSet<>();
        firstRequestSet.add(new Request("Need a bottle of champagne every morning"));
        Set<Request> secondRequestSet = new HashSet<>();
        secondRequestSet.add(new Request("I need a coffee"));

        RequestCommand command1 = new RequestCommand(INDEX_FIRST_PERSON, firstRequestSet);
        RequestCommand command2 = new RequestCommand(INDEX_FIRST_PERSON, firstRequestSet);
        RequestCommand command3 = new RequestCommand(INDEX_FIRST_PERSON, secondRequestSet);

        // same object -> returns true
        assertEquals(command1, command1);

        // same values -> returns true
        assertEquals(command1, command2);

        // different values -> returns false
        assertNotEquals(command1, command3);
    }
}
