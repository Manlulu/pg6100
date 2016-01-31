package no.espenschatten.exercise.hackernews.util;

import javax.validation.constraints.Pattern;

@Pattern(regexp = "/.+@.+/")
public @interface Email {

}
