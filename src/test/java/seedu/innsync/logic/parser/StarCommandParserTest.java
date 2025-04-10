package seedu.innsync.logic.parser;

import static seedu.innsync.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.innsync.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.innsync.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.innsync.logic.Messages;
import seedu.innsync.logic.commands.StarCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the StarCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the StarCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class StarCommandParserTest {

    private StarCommandParser parser = new StarCommandParser();

    @Test
    public void parse_validArgs_returnsStarCommand() {
        assertParseSuccess(parser, "1", new StarCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(Messages.MESSAGE_PARSE_EXCEPTION,
                    ParserUtil.MESSAGE_INVALID_INDEX, StarCommand.MESSAGE_USAGE));
    }
}
