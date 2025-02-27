package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMAGEPARENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWPHONEPARENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONEPARENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.logic.commands.parent.ParentAddCommand;
import seedu.address.logic.commands.parent.ParentCommand;
import seedu.address.logic.commands.parent.ParentDeleteCommand;
import seedu.address.logic.commands.parent.ParentEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Image;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.parent.Parent;
import seedu.address.model.tag.Tag;

/**
 * ParentCommandParser that parses commands starting with "parent"
 */
public class ParentCommandParser {
    public static final String HELP_MESSAGE = "Parent command has to include an action.\n"
            + ParentCommand.MESSAGE_USAGE;
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<arguments>.*)");

    /**
     * Parse the parent commands into their respective prefixes
     * @param args User input.
     * @return ParentCommand with an ArgumentMultimap object that is derived from tokenizing the input with Prefixes
     * @throws ParseException when there's an unexpected error in parsing the user input
     */
    public ParentCommand parse(String args) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HELP_MESSAGE));
        }
        final String arguments = matcher.group("arguments");
        ArgumentMultimap argMultimapFind =
                ArgumentTokenizer.tokenize(args, PREFIX_FIND);
        ArgumentMultimap argMultimapAdd =
                ArgumentTokenizer.tokenize(args, PREFIX_ADD, PREFIX_ADDRESS,
                        PREFIX_NAME, PREFIX_PARENTAGE, PREFIX_IMAGEPARENT, PREFIX_PHONEPARENT,
                        PREFIX_EMAIL);

        ArgumentMultimap argMultimapDelete =
                ArgumentTokenizer.tokenize(args, PREFIX_DELETE, PREFIX_ADDRESS,
                        PREFIX_NAME, PREFIX_PARENTAGE, PREFIX_IMAGEPARENT, PREFIX_PHONEPARENT,
                        PREFIX_EMAIL);
        ArgumentMultimap argMultimapEdit =
                ArgumentTokenizer.tokenize(args, PREFIX_EDIT, PREFIX_ADDRESS, PREFIX_NAME, PREFIX_PARENTAGE,
                        PREFIX_IMAGEPARENT, PREFIX_PHONEPARENT, PREFIX_EMAIL, PREFIX_NEWNAME, PREFIX_NEWPHONEPARENT);

        if (argMultimapAdd.getValue(PREFIX_ADD).isPresent()) {
            return addCommand(argMultimapAdd);
        } else if (argMultimapDelete.getValue(PREFIX_DELETE).isPresent()) {
            return deleteCommand(argMultimapDelete);
        } else if (argMultimapEdit.getValue(PREFIX_EDIT).isPresent()) {
            return editCommand(argMultimapEdit);
        } else if (argMultimapFind.getValue(PREFIX_FIND).isPresent()) {
            return new ParentFindCommandParser().parse(arguments);
        } else {
            //Rest of logic (Need to edit)
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HELP_MESSAGE));
        }
    }

    /**
     * Retrieve the relevant information to create a new Parent object from user input and parse it to create a new
     * Parent object and returns ParentAddCommand with the new Parent object.
     *
     * @param argMultimap An ArgumentMultimap object that is derived from tokenizing the user input with Prefixes.
     * @return ParentAddCommand to add the new Parent into PowerConnect.
     * @throws ParseException when there's an unexpected error in parsing the user input.
     */
    private ParentAddCommand addCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONEPARENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ParentAddCommand.MESSAGE_USAGE));
        }
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Age age = ParserUtil.parseAge((argMultimap.getValue(PREFIX_PARENTAGE).get()));
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Image image = ParserUtil.parseImage(argMultimap.getValue(PREFIX_IMAGEPARENT).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONEPARENT).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Parent parent = new Parent(name, age, image, email, phone, address, tagList);
        return new ParentAddCommand(parent);
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public ParentDeleteCommand deleteCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONEPARENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ParentDeleteCommand.MESSAGE_USAGE));
        }
        Phone phoneNumber = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONEPARENT).get());
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        return new ParentDeleteCommand(name, phoneNumber);
    }

    /**
     * Retrieve the relevant information to edit an existing Parent object from user input and
     * returns ParentEditCommand with the edited Parent object.
     *
     * @param argMultimap An ArgumentMultimap object that is derived from tokenizing the user input with Prefixes.
     * @return ParentEditCommand to edit a Parent in PowerConnect.
     * @throws ParseException when there's an unexpected error in parsing the user input.
     */
    public ParentEditCommand editCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONEPARENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ParentEditCommand.MESSAGE_USAGE));
        }
        Phone phoneNumber = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONEPARENT).get());
        Phone newPhoneNumber = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_NEWPHONEPARENT).get());
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Name newName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NEWNAME).get());
        Age newAge = ParserUtil.parseAge(argMultimap.getValue(PREFIX_PARENTAGE).get());
        Address newAddress = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Image newImage = ParserUtil.parseImage(argMultimap.getValue(PREFIX_IMAGEPARENT).get());
        Email newEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Set<Tag> newTagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        return new ParentEditCommand(name, newName, newAge, newImage, newEmail, phoneNumber, newPhoneNumber,
                newAddress, newTagList);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
