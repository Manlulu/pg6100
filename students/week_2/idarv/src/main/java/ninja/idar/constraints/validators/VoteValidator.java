package ninja.idar.constraints.validators;

import ninja.idar.models.Vote;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Idar Vassdal on 29.01.2016.
 */
public class VoteValidator implements ConstraintValidator<ValidVote, Vote> {

    @Override
    public void initialize(ValidVote constraintAnnotation) {

    }

    /**
     * Post or comment has to be set, but only one of them. The remaining has to be NULL
     * @param vote
     * @param context
     * @return
     */
    @Override
    public boolean isValid(Vote vote, ConstraintValidatorContext context) {
        return !(vote.getPost() != null && vote.getComment() != null) && (vote.getPost() != null || vote.getComment() != null);
    }
}
