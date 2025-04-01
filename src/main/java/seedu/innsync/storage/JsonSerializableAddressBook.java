package seedu.innsync.storage;

import static seedu.innsync.logic.commands.RequestCommand.MESSAGE_DUPLICATE_REQUEST;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.innsync.commons.exceptions.IllegalValueException;
import seedu.innsync.model.AddressBook;
import seedu.innsync.model.ReadOnlyAddressBook;
import seedu.innsync.model.person.Person;
import seedu.innsync.model.request.Request;



/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedRequest> requests = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("requests") List<JsonAdaptedRequest> requests) {
        this.persons.addAll(persons);
        if (requests != null) {
            this.requests.addAll(requests);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        requests.addAll(source.getRequestList().stream().map(JsonAdaptedRequest::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }
        for (JsonAdaptedRequest jsonAdaptedRequest : requests) {
            Request request = jsonAdaptedRequest.toModelType();
            if (addressBook.hasRequest(request)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_REQUEST);
            }
            addressBook.addRequest(request);
        }
        return addressBook;
    }

}
